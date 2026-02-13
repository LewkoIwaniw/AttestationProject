package ua.inf.iwanoff.attestation.model;

import ua.inf.iwanoff.utils.MathUtils;
import ua.inf.iwanoff.utils.Report;
import ua.inf.iwanoff.utils.Result;

import java.util.Arrays;

import static java.lang.Math.abs;
import static ua.inf.iwanoff.utils.MathUtils.*;
import static ua.inf.iwanoff.utils.StringUtils.*;

/**
 * Created by Leo on 18.11.18.
 */
public abstract class AbstractCalculations {
    protected int flag;
    private double[] rsd;
    private double[] aWRS;
    private double[] aPSS;
    private double xpss;
    protected Result reqDeltaWRS;
    protected double delta_pPSS;
    protected double delta_pWRS;
    protected Result practDiffPSS;
    protected Result practDiffWRS;

    public enum DataCheck {
        OK,
        NO_XPSS,
        NO_DELTA_WRS,
        WRONG_PSS_WRS_COUNT,
        NO_PSS,
        NO_WRS,
        WRONG_PSS_COUNT,
        WRONG_WRS_COUNT,
        WRONG_X_COUNT
    }

    public enum Times { OK, MISSED, WRONG, INSUFFICIENT }

    public static final boolean SIGNIFICANT = false;
    public static final boolean NOT_SIGNIFICANT = true;

    public static final int FLAG_ASCERTAINMENT = 1;
    public static final int FLAG_HOMOGENEITY = 2;

    protected abstract void prepareReport();
    protected abstract void showSourceTable();
    protected abstract void showResultsTable();
    protected abstract void showSamplesHomogeneity();
    protected abstract void showStatisticalQualityTestTable();
    protected abstract void showDrift();
    protected abstract void showUncertainty();
    protected abstract void showConclusions();
    protected abstract void errorMessage(DataCheck check);
    protected abstract String getErrorMessage(DataCheck check);
    protected abstract byte[] report();

    protected WrsData data;
    protected int wrsCount = 0;
    protected int pssCount = 0;
    protected int commonXCount = 0;

    protected Result[] xAverage;
    protected Result[] xRelStdDev;
    protected Result[] xAverNormalized;
    protected Result wrsAverNormalized;
    protected Result pssAverNormalized;
    protected Result wssSV;
    protected Result calculatedBartlett;
    protected Result tabularBartlett;
    protected Result[][] deviations;
    protected int commonDegreeOfFreedom;
    protected Result twoSidedStudent99;
    protected boolean homogeneous = false;
    protected boolean uniformity = false;
    protected boolean uncertainties = false;
    protected Result d_msOOSO;
    protected Result d_msMDO;
    protected Result oneSidedStudent95;
    protected Result d_msOOSO6;
    protected Result deltaPPSS;
    protected Result deltaPWRS;
    protected Result difPSS;
    protected Result difWRS;
    protected boolean statisticalInsignificancePSS;
    protected boolean statisticalInsignificanceWRS;
    protected boolean practicalInsignificancePSS;
    protected boolean practicalInsignificanceWRS;
    protected boolean insignificancePSS;
    protected boolean insignificanceWRS;
    protected Result delta_p;
    protected Result pooledNumberOfDegreesOfFreedom;
    protected Result[] aCoefs;
    protected Result[] bCoefs;
    protected Result[] sb;
    protected Result[] tValues;
    protected Result[] xBegin;
    protected Result[] xEnd;
    protected Result[] differences;
    protected Result[] xM;
    protected boolean[] significant;
    protected boolean drift;
    protected boolean[] drifts;

    private int[] f;
    protected OptionsData optionsData;

    protected int commonAnalyses = 0;
    protected int commonOK = 0;

    protected Result rsdp_unit;
    protected Result studentPCO;
    protected Result delta_unit;
    protected Result reqDeltaUnit;
    protected boolean useQ;
    protected Result[] valuesR;
    protected Result[] valuesQ1;
    protected Result[] valuesQ2;
    protected Result[] valuesQ;
    protected boolean[] hQ;
    protected Result certifiedValue;

    public AbstractCalculations(WrsData data) {
        this.data = data;
    }

    /**
     * Main calculation method used in all three schemata of attestation
     *
     * @param flag
     * @return array of bytes that represents final report
     */
    protected byte[] calc(int flag, OptionsData optionsData) {
        this.flag = flag;
        commonAnalyses = 0;
        commonOK = 0;
        this.optionsData = optionsData;
        prepareReport();
        DataCheck check = checkData();
        if (check != DataCheck.OK) {
            throw new DataException.ReportException(getErrorMessage(check));
        }
        if (check.ordinal() >= DataCheck.NO_WRS.ordinal()) {
            errorMessage(check);
            return report();
        }
        if (check.ordinal() > DataCheck.OK.ordinal()) {
            errorMessage(check);
        }
        try {
            showSourceTable();
        }
        catch (Report.TableDataException e) {
            throw new DataException.NoXException();
        }
        calcAscertainment();
        return report();
    }

    private void calcAscertainment() {
        recalcSourceData();
        try {
            showResultsTable();
            calcWSSSamplesHomogeneity();
            if ((flag & FLAG_HOMOGENEITY) > 0) {
                showWSSSamplesHomogeneity();
            }
            showStatisticalQualityTestHeader();
            calcVariancesEquality();
            if (optionsData.isVariancesEquality()) {
                showVariancesEquality();
            }
            calcSamplesHomogeneity();
            if (optionsData.isSamplesHomogeneity()) {
                showSamplesHomogeneity();
            }
            statisticalQualityTest();
            if ((flag & FLAG_ASCERTAINMENT) > 0) {
                showStatisticalQualityTestTable();
            }
            if (optionsData.isDrift()) {
                Times driftResult = calcDrift();
                if (driftResult == Times.OK) {
                    showDrift();
                }
                else {
                    showDriftError(driftResult);
                }
            }
            if ((flag & FLAG_ASCERTAINMENT) > 0) {
                showUncertainty();
                calcCertifiedValue();
            }
            showConclusions();
        }
        catch (NumberFormatException e) {
            throw new DataException.InsufficientDataException();
        }
    }

    private void calcCertifiedValue() {
        certifiedValue = Result.create(3).setValue(
                MathUtils.Round(MathUtils.average(aWRS) * xpss / MathUtils.average(aPSS), 1));
    }

    protected abstract void showWSSSamplesHomogeneity();

    private void calcWSSSamplesHomogeneity() {
        double[] dWRS = new double[wrsCount];
        //Нормализованное среднее значение РСО
        aWRS = new double[wrsCount];
        int k = 0;
        for (int i = 0; i < data.size(); i++)
        {
            if (data.flagSorted(i) == WrsData.DataType.WRS)
            {
                double[] a_ = new double[data.sizeSorted(i)];
                for (int j = 0; j < data.sizeSorted(i); j++)
                {
                    a_[j] = data.xSorted(i, j);
                }
                aWRS[k] = MathUtils.AverageNormalizedValue(a_,  data.concentrationSorted(i));
                k++;
            }
        }
        k = 0;
        for (int i = 0; i < data.size(); i++)
        {
            if (data.flagSorted(i) == WrsData.DataType.WRS)
            {
                double[] x_WRS = new double[data.sizeSorted(i)];
                for (int j = 0; j < data.sizeSorted(i); j++)
                {
                    x_WRS[j] = data.xSorted(i, j);
                }

                dWRS[k] = (MathUtils.AverageNormalizedValue(x_WRS, data.concentrationSorted(i)) -
                        MathUtils.average(aWRS)) / (MathUtils.average(aWRS)) * 100;
                k++;
            }
        }
        rsdp_unit = Result.create(2).setValue(MathUtils.RSD_unit(dWRS, wrsCount - 1));
        studentPCO = Result.create(7, 4).setValue(Student.calcStudent(
                optionsData.getSides() == OptionsData.OneTwo.ONE ? 1 : 2, wrsCount - 1, 95.0));
        delta_unit = Result.create(2).setValue(rsdp_unit.getValue() * studentPCO.getValue());
        // Требования к неопределенности аттестованного значения РСО -
        reqDeltaWRS = Result.create(2).setValue(valueOrDefault(data.getDeltaWRS(), -1));
        // фактически всегда критерий 0,5%.
        // Требования к относительному доверительному интервалу
        reqDeltaUnit = Result.create(2).setValue(valueOrDefault(data.getDeltaWRS(), -1)); // getDeltaUnit ?
        // для единичного содержания упаковки (data.DeltaUnit=data.DeltaWRS)
        //Аттестованное значение ФСО
        xpss = valueOrDefault(data.getXPSS(), -1);
        if (reqDeltaUnit.getRounded() <= 0) {
            reqDeltaUnit.setValue(reqDeltaWRS.getValue());
        }
    }

    protected abstract void showDriftError(Times times);

    private Times calcDrift() {
        Times result = checkTimes();
        if (result == Times.OK) {
            commonXCount = 0;
            int[] f = new int[data.size()];
            for (int i = 0; i < data.size(); i++) {
                f[i] = data.sizeSorted(i);
                commonXCount += data.sizeSorted(i);
            }
            double degreesOfFreedom = MathUtils.Fp2(f);
            pooledNumberOfDegreesOfFreedom = Result.create(6).setValue(degreesOfFreedom);
            twoSidedStudent99.setValue(Student.calcStudent(2, degreesOfFreedom, 99));
            double[][] r_a = new double[data.size()][];
            for (int i = 0; i < data.size(); i++) {
                r_a[i] = new double[data.sizeSorted(i)];
                for (int j = 0; j < data.sizeSorted(i); j++)
                {
                    r_a[i][j] = data.xSorted(i, j) / data.concentrationSorted(i);
                }


            }
            aCoefs = new Result[data.size()];
            bCoefs = new Result[data.size()];
            sb = new Result[data.size()];
            tValues = new Result[data.size()];
            xBegin = new Result[data.size()];
            xEnd = new Result[data.size()];
            xM = new Result[data.size()];
            differences = new Result[data.size()];
            significant = new boolean[data.size()];
            double[] t_= new double[data.size()];
            for (int i = 0; i < data.size(); i++) {
                double[] t = data.getTSorted(i);
                aCoefs[i] = Result.create(6).setValue(a(r_a[i], t));
                bCoefs[i] = Result.create(6).setValue(b(r_a[i], t));
                sb[i] = Result.create(6).setValue(Sb(r_a[i], t));
                tValues[i] = Result.create(6).setValue(t(r_a[i], t));
                xBegin[i] = Result.create(6).setValue(x_b(r_a[i], t));
                xEnd[i] = Result.create(6).setValue(x_e(r_a[i], t));
                differences[i] = Result.create(6).setValue(difference(r_a[i], t));
                xM[i] = Result.create(6).setValue(x_m(r_a[i]));
                t_[i] = t(r_a[i], t);
                significant[i] = abs(t_[i]) > Student.calcStudent(2, MathUtils.Fp2(f), 99);
                //commonXCount += data.sizeSorted(i);
            }
            drift = true;
            drifts = new boolean[data.size()];
            for (int i = 0; i < data.size(); i++)
            {
                // if (WrsMath.drift_is(data.Sample[i].XArray(), data.Sample[i].DateArray(), WrsMath.Fp2(f)))
                if (abs(t_[i]) > Student.calcStudent(2, MathUtils.Fp2(f), 99)) {
                    drift = false;
                    drifts[i] = false;
                }
                else {
                    drifts[i] = true;
                }
            }
        }
        return result;
    }

    private Times checkTimes() {
        if (data.getMaxSampleXCount() <= 2) {
            return Times.INSUFFICIENT;
        }
        int zeros = 0;
        int commonTimes = 0;
        int wrongSamples = 0;
        for (int i = 0; i < data.size(); i++) {
            for (int j = 0; j < data.sizeSorted(i); j++) {
                if (data.getDateSorted(i, j).equals("0")) {
                    zeros++;
                }
                commonTimes++;
            }
            if (!allDiffer(stringsToDates(data.getDatesSorted(i)))) {
                wrongSamples++;
            }
        }
        if (zeros == commonTimes) {
            return Times.MISSED;
        }
        if (wrongSamples == 0) {
            return Times.OK;
        }
        return Times.WRONG;
    }

    private void calcSamplesHomogeneity() {
        f = new int[data.size()];
        for (int i = 0; i < data.size(); i++) {
            f[i] = data.sizeSorted(i);
        }
        commonDegreeOfFreedom = MathUtils.fp(f);
        twoSidedStudent99 = Result.create(9, 4).setValue(MathUtils.Student.calcStudent(2, commonDegreeOfFreedom, 99));
        rsd = new double[data.size()];
        for (int i = 0; i < data.size(); i++) {
            rsd[i] = MathUtils.relativeStandardDeviation(data.getXSorted(i));
        }
        d_msOOSO = Result.create(2).setValue(MathUtils.UnitedRelativeStandardDeviationU(rsd, f));
        d_msMDO = Result.create(6).setValue(d_msOOSO.getValue() * twoSidedStudent99.getValue());
        oneSidedStudent95 = Result.create(9, 4).setValue(MathUtils.Student.calcStudent(1, commonDegreeOfFreedom, 95));
        d_msOOSO6 = Result.create(6).setValue(MathUtils.UnitedRelativeStandardDeviationU(rsd, f));
        hQ = new boolean[data.size()];
        if (commonDegreeOfFreedom >= 9) {
            useQ = false;
            double[][] a = new double[data.size()][data.getMaxSampleXCount()];
            for (int i = 0; i < data.size(); i++) {
                for (int j = 0; j < data.sizeSorted(i); j++) {
                    a[i][j] = data.xSorted(i, j);
                }
                //стр. 2 ячейки 1...sampleCount  Среднее значение аналитического сигнала для j-того р-ра
            }
            double[][] d = new double[data.size()][data.getMaxSampleXCount()];
            for (int i = 0; i < data.size(); i++) {
                d[i] = MathUtils.deviations(a[i]);
            }
            for (int i = 0; i < data.size(); i++) {
                hQ[i] = true;
                for (int j = 0; j < data.sizeSorted(i); j++) {
                    if (abs(d[i][j]) > d_msMDO.getRounded()) {
                        homogeneous = false;
                        hQ[i] = false;
                        break;
                    }
                }
            }
        }
        else {
            useQ = true;
            valuesR = new Result[data.size()];
            valuesQ1 = new Result[data.size()];
            valuesQ2 = new Result[data.size()];
            valuesQ = new Result[data.size()];
            for (int i = 0; i < data.size(); i++) {
                double[] sortSample = new double[data.sizeSorted(i)];
                for (int k_ = 0; k_ < sortSample.length; k_++) {
                    sortSample[k_] = data.xSorted(i, k_);
                }
                Arrays.sort(sortSample);
                double R = 0, Q1 = 0, Q2 = 0;
                if (sortSample.length < 8) {
                    R = abs(sortSample[0] - sortSample[sortSample.length - 1]);
                }
                else {
                    R = abs(sortSample[0] - sortSample[sortSample.length - 2]);
                }
                valuesR[i] = Result.create(2).setValue(R);
                Q1 = abs(sortSample[0] - sortSample[1]) / R;
                Q2 = abs(sortSample[sortSample.length - 1] - sortSample[sortSample.length - 2]) / R;
                valuesQ1[i] = Result.create(2).setValue(R == 0 ? 0 : Q1);
                valuesQ2[i] = Result.create(2).setValue(R == 0 ? 0 : Q2);
                if (sortSample.length > 2) {
                    double qt = MathUtils.Qtab(99, data.sizeSorted(i));
                    valuesQ[i] = Result.create(2).setValue(qt);
                    double qtRounded = Result.create(2).setValue(qt).getRounded();
                    if (R == 0) {
                        hQ[i] = true;
                    }
                    else {
                        hQ[i] = valuesQ1[i].getRounded() < qtRounded && valuesQ2[i].getRounded() < qtRounded;
                    }
                }
                else {

                }
            }
        }
        homogeneous = allTrue(hQ);
    }

    public static boolean allTrue(boolean... arr) {
        for (boolean b : arr) {
            if (!b) {
                return false;
            }
        }
        return true;
    }

    private void calcVariancesEquality() {
        commonAnalyses++;
        try {
            Bartlett.Mm mm = calcBartlett(data.size());
            if (mm != null) {
                calculatedBartlett = Result.create(6, 2).setValue(mm.getCalculated());
                tabularBartlett = Result.create(6, 2).setValue(mm.getTabular());
                uniformity = mm.getUniformity();
                if (uniformity) {
                    commonOK++;
                }
            }
            deviations = new Result[data.size()][];
            for (int i = 0; i < deviations.length; i++) {
                deviations[i] = Result.createArray(MathUtils.deviations(data.getXSorted(i)), 6);
            }
        }
        catch (BadData badData) {
            badData.printStackTrace();
        }
    }

    protected abstract void showVariancesEquality();

    protected abstract void showStatisticalQualityTestHeader();

    private void recalcSourceData() {
        xAverage = new Result[data.size()];
        xRelStdDev = new Result[data.size()];
        xAverNormalized = new Result[data.size()];
        double rsSum = 0;
        double wrsSum = 0;
        for (int i = 0; i < data.size(); i++) {
            xAverage[i] = Result.create(6).setValue(Arrays.stream(data.getXSorted(i)).average().getAsDouble());
            xRelStdDev[i] = Result.create(2).setValue(relStdDev(data.getXSorted(i)));
            double normalized = averNormalized(data.getXSorted(i), data.concentrationSorted(i));
            if (data.flagSorted(i) == WrsData.DataType.PRS) {
                rsSum += normalized;
            }
            else {
                wrsSum += normalized;
            }
            xAverNormalized[i] = Result.create(6).setValue(normalized);
        }
        pssAverNormalized = Result.create(6).setValue(rsSum / pssCount);
        wrsAverNormalized = Result.create(6).setValue(wrsSum / wrsCount);
        double xpss = 0;
        try {
            xpss = Double.parseDouble(data.getXPSS());
        }
        catch (NumberFormatException e) {
            throw new DataException.WrongXPSS();
        }
        //wssSV = Result.create(3).setValue(xpss * wrsAverNormalized.getValue() / pssAverNormalized.getValue()); // Corrected 2020_08_11!!!!
        try {
            wssSV = Result.create(3).setValue(xpss * wrsAverNormalized.getRounded() / pssAverNormalized.getRounded()); // Corrected 2020_08_11!!!!
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void statisticalQualityTest() {
        double urs = MathUtils.UnitedRelativeStandardDeviation(rsd);
        int[] countWRS2 = new int[wrsCount];
        double[] aWRS2 = new double[wrsCount];  //Нормализованное среднее значение РСО
        double[] dWRS = new double[wrsCount];

        //Нормализованное среднее значение СО
        aPSS = new double[pssCount];
        aWRS = new double[wrsCount];  //Нормализованное среднее значение РСО

        if (pssCount == 2 || wrsCount == 2) {
            int[] countPSS2 = new int[pssCount];
            // Нормализованное среднее значение СО
            double[] aPSS2 = new double[pssCount];
            int k = 0;
            for (int i = 0; i < data.size(); i++) {
                if (data.flagSorted(i) == WrsData.DataType.PRS) {
                    countPSS2[k] = data.getXSorted(i).length;
                    aPSS2[k] = MathUtils.AverageNormalizedValue(data.getXSorted(i), data.concentrationSorted(i));
                    k++;
                }
            }

            k = 0;
            for (int i = 0; i < data.size(); i++)
            {
                if (data.flagSorted(i) == WrsData.DataType.PRS)
                {
                    double[] a_ = new double[data.sizeSorted(i)];
                    for (int j = 0; j < data.sizeSorted(i); j++)
                    {
                        a_[j] = data.xSorted(i, j);
                    }
                    aPSS[k] = MathUtils.AverageNormalizedValue(a_, data.concentrationSorted(i));
                    k++;
                }
            }
            k = 0;
            for (int i = 0; i < data.size(); i++)
            {
                if (data.flagSorted(i) == WrsData.DataType.WRS)
                {
                    double[] a_ = new double[data.sizeSorted(i)];
                    for (int j = 0; j < data.sizeSorted(i); j++)
                    {
                        a_[j] = data.xSorted(i, j);
                    }
                    aWRS[k] = MathUtils.AverageNormalizedValue(a_, data.concentrationSorted(i));
                    k++;
                }
            }
            k = 0;
            for (int i = 0; i < data.size(); i++) {
                if (data.flagSorted(i) == WrsData.DataType.WRS) {
                    countWRS2[k] = data.getXSorted(i).length;
                    aWRS2[k] = MathUtils.AverageNormalizedValue(data.getXSorted(i), data.concentrationSorted(i));
                    double[] x_WRS = new double[data.sizeSorted(i)];
                    for (int j = 0; j < data.sizeSorted(i); j++)
                    {
                        x_WRS[j] = data.xSorted(i, j);
                    }
                    dWRS[k] = (MathUtils.AverageNormalizedValue(x_WRS, data.concentrationSorted(i)) -
                            MathUtils.average(aWRS)) / (MathUtils.average(aWRS)) * 100;
                    k++;
                }
            }

            double delta_dop_pPSS1 = 0;
            double delta_dop_pPSS2 = 0;
            int i_delta_dop_pPSS1 = 0;
            int i_delta_dop_pPSS2 = 0;
            boolean b__delta_dop_pPSS1 = true;
            for (int i = 0; i < data.size(); i++) {
                if (data.flagSorted(i) == WrsData.DataType.PRS) {
                    if (b__delta_dop_pPSS1) {
                        for (int j = 0; j < data.sizeSorted(i); j++) {
                            delta_dop_pPSS1 += MathUtils.sqr(data.uncertaintySorted(i));
                            i_delta_dop_pPSS1++;
                            b__delta_dop_pPSS1 = false;
                        }
                    }
                    else {
                        for (int j = 0; j < data.sizeSorted(i); j++) {
                            delta_dop_pPSS2 += MathUtils.sqr(data.uncertaintySorted(i));
                            i_delta_dop_pPSS2++;
                        }
                    }
                }
            }

            delta_dop_pPSS1 /= i_delta_dop_pPSS1;
            delta_dop_pPSS2 /= i_delta_dop_pPSS2;

            double delta_dop_pWRS1 = 0;
            double delta_dop_pWRS2 = 0;
            int i_delta_dop_pWRS1 = 0;
            int i_delta_dop_pWRS2 = 0;
            boolean b__delta_dop_pWRS1 = true;
            for (int i = 0; i < data.size(); i++) {
                if (data.flagSorted(i) == WrsData.DataType.WRS) {
                    if (b__delta_dop_pWRS1) {
                        for (int j = 0; j < data.size(); j++) {
                            delta_dop_pWRS1 += MathUtils.sqr(data.uncertaintySorted(i));
                            i_delta_dop_pWRS1++;
                            b__delta_dop_pWRS1 = false;
                        }
                    }
                    else {
                        for (int j = 0; j < data.size(); j++) {
                            delta_dop_pWRS2 += MathUtils.sqr(data.uncertaintySorted(i));
                            i_delta_dop_pWRS2++;
                        }
                    }
                }
            }
            delta_dop_pWRS1 /= i_delta_dop_pWRS1;
            delta_dop_pWRS2 /= i_delta_dop_pWRS2;

            delta_pPSS = 0;
            double united = MathUtils.UnitedRelativeStandardDeviation(rsd);
            double student95 = Student.calcStudent(1, commonDegreeOfFreedom, 95);
            if (countPSS2.length > 1) {
                delta_pPSS = Math.sqrt(MathUtils.sqr(united * student95) / countPSS2[0]
                        + MathUtils.sqr(united * student95) / countPSS2[1] + delta_dop_pPSS1 + delta_dop_pPSS2);
                deltaPPSS = Result.create(2).setValue(delta_pPSS);
                // ФСО Максимально допустимое различие приведенных средних значений
                //(относительный односторонний доверительный интервал для вероятности 95%)
            }
            delta_pWRS = 0;
            if (countWRS2.length > 1) {
                delta_pWRS = Math.sqrt(MathUtils.sqr(united * student95) / countPSS2[0]
                        + MathUtils.sqr(united * student95) / countPSS2[1] + delta_dop_pWRS1 + delta_dop_pWRS2);
                deltaPWRS = Result.create(2).setValue(delta_pWRS);
                // РСО Максимально допустимое различие приведенных средних значений
                // (относительный односторонний доверительный интервал для вероятности 95%)
            }

            if (countPSS2.length > 1) {
                difPSS = Result.create(2).setValue(MathUtils.DifferenceNormalizedValue(aPSS2));
                statisticalInsignificancePSS = difPSS.getRounded() <= deltaPPSS.getRounded();
                // practDiffPSS = Result.create(2).setValue(delta_pPSS * Math.sqrt(2)); // Corrected 2020_08_11!!!!
                practDiffPSS = Result.create(2).setValue(Double.parseDouble(data.getDeltaWRS()) * Math.sqrt(2)); // Corrected 2020_08_11!!!!
                practicalInsignificancePSS = (difPSS.getRounded() <= practDiffPSS.getRounded());
            }
            if (countWRS2.length > 1) {
                difWRS = Result.create(2).setValue(MathUtils.DifferenceNormalizedValue(aWRS2));
                statisticalInsignificanceWRS = difWRS.getRounded() <= deltaPWRS.getRounded();
                //practDiffWRS = Result.create(2).setValue(delta_pWRS * Math.sqrt(2)); // Corrected 2020_08_11!!!!
                practDiffWRS = Result.create(2).setValue(Double.parseDouble(data.getDeltaWRS()) * Math.sqrt(2)); // Corrected 2020_08_11!!!!
                practicalInsignificanceWRS = (difWRS.getRounded() <= practDiffWRS.getRounded());
            }
            insignificancePSS = statisticalInsignificancePSS || practicalInsignificancePSS;
            insignificanceWRS = statisticalInsignificanceWRS || practicalInsignificanceWRS;

            if (insignificancePSS && insignificanceWRS) {
                commonOK++;
            }
            commonAnalyses++;

            double delta_dop = 0;
            for (int i = 0; i < data.size(); i++)
            {
                delta_dop += MathUtils.sqr(data.uncertaintySorted(i));
            }
            if (reqDeltaWRS.getRounded() > 0)
            {
                if (pssCount == 2 && wrsCount == 2)//если кол-во выборок = 2
                {

                    double stud = Student.calcStudent(1, MathUtils.fp(f), 95);
                    double urs_stud = urs * stud;
                    double z = 0.5 * MathUtils.sqr(urs_stud);
                    delta_p = Result.create(2).setValue(Math.sqrt(z / nRSCount() + z / nWRSCount() + 0.25 * delta_dop));
                    uncertainties = delta_p.getRounded() < reqDeltaWRS.getRounded();
                }
                if (wrsCount > 2) {
                    double RSDP_unit = MathUtils.RSD_unit(dWRS, wrsCount - 1);
                    delta_unit = Result.create(2).setValue(RSDP_unit * Student.calcStudent(1, wrsCount - 1, 95.0));
                    uncertainties = delta_unit.getRounded() <= reqDeltaWRS.getRounded();
                }
            }
            if (uncertainties) {
                commonOK++;
            }
            commonAnalyses++;
        }
    }

    private DataCheck checkData() {
        calcWrsPss();
        if (!isDouble(data.getDeltaWRS())) {
            return DataCheck.NO_DELTA_WRS;
        }
        if (!isDouble(data.getXPSS())) {
            return DataCheck.NO_XPSS;
        }
        if (pssCount == 0 && (flag & FLAG_ASCERTAINMENT) != 0) {
            return DataCheck.NO_PSS;
        }
        if (wrsCount == 0) {
            return DataCheck.NO_WRS;
        }
        if (pssCount > 2) {
            return DataCheck.WRONG_PSS_COUNT;
        }
        if (wrsCount < 2) {
            return DataCheck.WRONG_WRS_COUNT;
        }
        if (!xCountEqual()) {
            return DataCheck.WRONG_X_COUNT;
        }
        if ((flag & FLAG_ASCERTAINMENT) != 0 && (flag & FLAG_HOMOGENEITY) == 0 && (pssCount != 2 || wrsCount != 2)) {
            return DataCheck.WRONG_PSS_WRS_COUNT;
        }
        if ((flag & FLAG_ASCERTAINMENT) != 0 && (flag & FLAG_HOMOGENEITY) != 0  && wrsCount == 2) {
            return DataCheck.WRONG_PSS_WRS_COUNT;
        }
        if ((flag & FLAG_ASCERTAINMENT) == 0 && (flag & FLAG_HOMOGENEITY) != 0 && pssCount != 0) {
            return DataCheck.WRONG_PSS_WRS_COUNT;
        }
        return DataCheck.OK;
    }

    private void calcWrsPss() {
        pssCount = 0;
        wrsCount = 0;
        for (int i = 0; i < data.size(); i++) {
            if (data.flagSorted(i) == WrsData.DataType.PRS) {
                pssCount++;
            }
            else {
                wrsCount++;
            }
        }
    }

    private boolean xCountEqual() {
        int xCount = data.sizeSorted(0);
        for (int i = 1; i < data.size(); i++) {
            if (xCount != data.sizeSorted(i)) {
                return false;
            }
        }
        return true;
    }

    private MathUtils.Bartlett.Mm calcBartlett(int k) throws MathUtils.BadData {
        int[] nu = new int[data.size()];
        double[] s2 = new double[data.size()];
        for (int i = 0; i < data.size(); i++) {
            nu[i] = data.sizeSorted(i) - 1;
            MathUtils.forBartlett = digitsIfEquals(i);
            double dd = MathUtils.relativeStandardDeviation(data.getSampleXArray(i));
            s2[i] = dd * dd;
            MathUtils.forBartlett = 0;
        }
        return new MathUtils.Bartlett().Bartlett_(nu, s2, k);
    }

    //TODO Implement correctly
    private int digitsIfEquals(int i) {
        boolean differ = false;
        double x = data.xSorted(i, 0);
        for (int j = 1; j < data.sizeSorted(i); j++) {
            if (data.xSorted(i, j) != data.xSorted(i, 0)) {
                differ = true;
                break;
            }
        }
        if (!differ) {
            String s = data.getXSorted(i, 0);
            return s.substring(s.lastIndexOf('.') + 1).length();
        }
        return 0;
    }

    private int nWRSCount() {
        for (int i = 0; i < data.size(); i++) {
            if (data.flagSorted(i) == WrsData.DataType.WRS) {
                return data.sizeSorted(i);
            }
        }
        return 0;
    }

    private int nRSCount() {
        for (int i = 0; i < data.size(); i++) {
            if (data.flagSorted(i) == WrsData.DataType.PRS) {
                return data.sizeSorted(i);
            }
        }
        return 0;
    }
}

