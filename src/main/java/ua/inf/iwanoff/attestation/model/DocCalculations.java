package ua.inf.iwanoff.attestation.model;

import com.lowagie.text.*;
import com.lowagie.text.Image;
import ua.inf.iwanoff.utils.MultiString;
import ua.inf.iwanoff.utils.Report;
import ua.inf.iwanoff.utils.Result;

import java.awt.*;
import java.io.IOException;

import static ua.inf.iwanoff.attestation.view.Strings.*;
import static ua.inf.iwanoff.utils.FileUtils.createTempFromResources;

/**
 *
 */
public class DocCalculations extends AbstractCalculations {
    public static String deltaSqrt = "delta_sqrt.png";
    public static String rsdUnit = "RSD_Unit.png";
    public static String deltaUnit = "DeltaUnit.png";

    private static boolean lettersLoaded = false;

    private static void loadLetters() {
        if (!lettersLoaded) {
            deltaSqrt = createTempFromResources("/" + deltaSqrt);
            rsdUnit = createTempFromResources("/" + rsdUnit);
            deltaUnit = createTempFromResources("/" + deltaUnit);
            lettersLoaded = true;
        }
    }

    static {
        loadLetters();
    }
    private Report.DocType docType;
    private Report report = null;
    private String image = null;
    private MultiString customer;
    private String title = "";
    private String protocol = "";

    public DocCalculations(WrsData data, MultiString customer) {
        super(data);
        this.customer = customer;
    }

    public Report.DocType getDocType() {
        return docType;
    }

    public void setDocType(Report.DocType docType) {
        this.docType = docType;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    @Override
    protected void prepareReport() {
        switch (flag) {
            case FLAG_ASCERTAINMENT:
                title = msPIUAZRSO.toString();
                break;
            case FLAG_ASCERTAINMENT | FLAG_HOMOGENEITY:
                title = msPIUAZPORSO.toString();
                break;
            case FLAG_HOMOGENEITY:
                title = msPIPORSO.toString();
                break;
        }
        addHeader();
        addTitle();
        addAdditionalData();
    }


    private boolean addHeader() {
        try {
            report = new Report(docType);
            report.setPageOf(msPageOf.toString());
            Cell leftCell = new Cell();
            if (image != null) {
                Image img = Image.getInstance(image);
                leftCell.addElement(new Chunk(img, 0, 0));
            }
            leftCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            leftCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            leftCell.setBorderColor(Color.white);
            Cell rightCell = new Cell(new Phrase(customer.toString(), Report.FONT_BOLD_14));
            rightCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            rightCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            rightCell.setBorderColor(Color.white);
            Cell[][] cells = {{ leftCell, rightCell }};
            report.createTable(null, null, null, 100, cells, 4, Color.white);
        }
        catch (DocumentException | IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private boolean addTitle() {
        try {
            Cell cell = new Cell(new Phrase(title, Report.FONT_BOLD_14));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorderColor(Color.white);
            Object[][] cells = {{cell}};
            report.newLine();
            report.createTable(cells, 100, 2, Color.WHITE);
        }
        catch (DocumentException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private boolean addAdditionalData() {
        try {
            report.newLine();
            Object[][] cells = {{ title , msNumber + " " + protocol }, { data.getSSName(), "" }};
            report.createTable(cells, 100, 2, Color.BLACK);
            cells = new Object[][] {
                    { msNRSO, data.getSSName() },
                    { msOperator, data.getOperator() },
                    { msStuff, data.getStuff() },
                    { msAmountOfSubstance, data.getAmount() },
                    { msOfficialReferenceSample, data.getOfficialStandardSample() },
                    { msWrsDocument, data.getDocument() },
                    { msTestDate, data.getDate() },
                    { msCertifiedValueOfRS, data.getXPSS() },
                    { msDeltaWRS, data.getDeltaWRS() }
            };
            report.newLine();
            report.createTable(cells, 100, 2, Color.BLACK);
        }
        catch (DocumentException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    protected void showSourceTable() {
        try {
            Cell cell = new Cell(new Phrase(msED.toString(), Report.FONT_BOLD_12));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorderColor(Color.white);
            Object[][] cells = {{cell}};
            report.newLine();
            report.createTable(cells, 100, 2, Color.WHITE);
            report.newLine();
            if (!data.getWeightsDilutions().isEmpty()) {
                report.text(msNO, Report.FONT_BOLD_12);
                report.newLine();
                cells = new Object[][]{{data.getWeightsDilutions()}};
                report.createTable(cells);
                report.newLine();
            }
            report.text(msAS, Report.FONT_BOLD_12);
            report.newLine();
            Object[] header = new Object[data.size() + 1];

            header[0] = msNIS;
            for (int j = 1; j <= data.size(); j++) {
                header[j] = data.getNameSorted(j - 1);
            }
            cells = new Object[data.sizeSorted(0)][data.size() + 1];
            for (int i = 0; i < data.sizeSorted(0); i++) {
                cells[i][0] = i + 1;
                for (int j = 1; j <= data.size(); j++) {
                    cells[i][j] = data.getXSorted(j - 1, i);
                }
            }
            report.createTable(header, cells);
        }
        catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void showResultsTable() {
        report.newLine();
        report.text(msRRS, Report.FONT_BOLD_12);
        report.newLine();
        Object[] header = new Object[data.size() + 1];
        header[0] = "\t";
        for (int j = 1; j <= data.size(); j++) {
            header[j] = data.getNameSorted(j - 1);
        }
        Object[][] cells = new Object[4][data.size() + 1];
        cells[0][0] = msKRS + ", (Cj)";
        cells[1][0] = msSZASJRS + ", (Aj)";
        cells[2][0] = msOSOASJRS + ", (RSDj)";
        cells[3][0] = msNZJRS + ", (Rj)";
        for (int j = 1; j <= data.size(); j++) {
            cells[0][j] = data.getConcentrationSorted(j - 1);
            cells[1][j] = xAverage[j - 1];
            cells[2][j] = xRelStdDev[j - 1];
            cells[3][j] = xAverNormalized[j - 1];
        }
        report.createTable(header, cells);
        report.newLine();
        if (pssCount == 0 && wrsCount > 0) {
            report.createTable(new Object[][]{
                    { msNSZRSOS },
                    { wrsAverNormalized }
            });
        }
        if (pssCount > 0 && wrsCount > 0) {
            report.createTable(new Object[][] {
                    { msNSZSOS, msNSZRSOS },
                    { pssAverNormalized, wrsAverNormalized}
            });
            report.newLine();
            report.createTable(new Object[][]{
                    {msCertifiedValueOfRS, msAZRSOS},
                    {Result.create(3).setValue(Double.parseDouble(data.getXPSS())), wssSV}
            });
        }
    }

    @Override
    protected void showStatisticalQualityTestHeader() {
        report.newLine();
        report.text(msSKKPR, Report.FONT_BOLD_12);
    }

    @Override
    protected void showVariancesEquality() {
        report.newLine();
        report.text(msPRD, Report.FONT_BOLD_12);
        report.newLine();
        report.createTable(new Object[][] {
                { msRKB, msTZKB },
                { calculatedBartlett, tabularBartlett }
        });
        report.newLine();
        report.text(msConclusion, Report.FONT_BOLD_12);
        report.text((uniformity ? msDRNVR : msDRZVN) + ".", Report.FONT_NORMAL_12);
    }

    @Override
    protected void showSamplesHomogeneity() {
        report.newLine();
        if (data.getMaxSampleXCount() <= 2) {
            report.text(msWarning, Report.FONT_BOLD_12);
            report.text(msInsufficientCountOfParallelMeasurements + ". " +
                    msCheckingTheHomogeneityCannotBeCalculated + ".", Report.FONT_NORMAL_12);
        }
        else {
            report.text(msPOV, Report.FONT_BOLD_12);
            report.newLine();
            report.text(msOtklonenija, Report.FONT_BOLD_12);
            report.newLine();
            Object[] header = new Object[data.size() + 1];
            header[0] = msNIS;
            for (int j = 1; j <= data.size(); j++) {
                header[j] = data.getNameSorted(j - 1);
            }
            Object[][] cells = new Object[data.sizeSorted(0)][data.size() + 1];
            for (int i = 0; i < data.sizeSorted(0); i++) {
                cells[i][0] = i + 1;
                for (int j = 1; j <= data.size(); j++) {
                    cells[i][j] = deviations[j - 1][i];
                }
            }
            report.createTable(header, cells);
            report.newLine();
            if (useQ) {
                report.createTable(new Object[][] {
                    {msOCSS + ", fp", commonDegreeOfFreedom},
                });
                report.newLine();
                header = new Object[] { "", "R", "Q1", "Q2", msKCRIT + " Q", msHomogeneity };
                cells = new Object[data.size()][6];
                for (int i = 0; i < data.size(); i++) {
                    cells[i][0] = data.getNameSorted(i);
                    cells[i][1] = valuesR[i];
                    cells[i][2] = valuesQ1[i];
                    cells[i][3] = valuesQ2[i];
                    cells[i][4] = valuesQ[i];
                    cells[i][5] = hQ[i] ? msVO : msVN;
                }
                report.createTable(header, cells);
            }
            else {
                report.createTable(new Object[][]{
                        {msOCSS + ", fp", commonDegreeOfFreedom},
                        {msTwoSidedStudent_sTTest + " (P = 99%)", twoSidedStudent99},
                        {msOOSO + ", RSDp (%)", d_msOOSO},
                        {msMDO, d_msMDO}
                });
            }
            report.newLine();
            report.text(msConclusion, Report.FONT_BOLD_12);
            report.text(homogeneous ? msONPKZVO + ".": msOPKZVN + ":", Report.FONT_NORMAL_12);
            if (!homogeneous) {
                for (int i = 0; i < data.size(); i++) {
                    if (!hQ[i]) {
                        report.text(data.getName(i), Report.FONT_NORMAL_12);
                    }
                }
            }
        }
        report.newLine();
    }

    @Override
    protected void showStatisticalQualityTestTable() {
        report.newLine();
        report.text(msPRNSZ, Report.FONT_BOLD_12);
        report.newLine();
        report.createTable(new Object[][]{
                {msOCSS + ", fp", commonDegreeOfFreedom},
                {msOKS + " (P = 95%)", oneSidedStudent95},
                {msOOSO + ", RSDp (%)", d_msOOSO6},
        });
        report.newLine();
        report.text(msNP, Report.FONT_BOLD_12);
        report.newLine();
        Object[] header = new Object[data.size()];
        Object[][] cells = new Object[1][data.size()];
        for (int j = 0; j < data.size(); j++) {
            header[j] = data.getNameSorted(j);
            cells[0][j] = data.getUncertaintySorted(j);
        }
        report.createTable(header, cells);
        report.newLine();
        if (wrsCount == 2) {
            if (statisticalInsignificancePSS && statisticalInsignificanceWRS) {
                report.createTable(new Object[] { "", msFSS, msWRS }, new Object[][] {
                        { msMDRNSZ, deltaPPSS, deltaPWRS },
                        { "2*|R1-R2|/(R1+R2)*100%", difPSS, difWRS },
                        { msSNR, msNeznzczimo, msNeznzczimo }});
            }
            else {
                report.createTable(new Object[] { "", msFSS, msWRS }, new Object[][] {
                        { msMDRNSZ, deltaPPSS, deltaPWRS},
                        { "2*|R1-R2|/(R1+R2)*100%", difPSS, difWRS},
                        { msSNR, statisticalInsignificancePSS ? msNeznzczimo : msZnaczimo,
                                statisticalInsignificanceWRS ? msNeznzczimo : msZnaczimo, },
                        { getDeltaSqrt(), practDiffPSS, practDiffWRS },
                        { msPNR, practicalInsignificancePSS ? msNeznzczimo : msZnaczimo,
                                practicalInsignificanceWRS ? msNeznzczimo : msZnaczimo }
                });
            }
            report.newLine();
            report.text(msConclusion, Report.FONT_BOLD_12);
            report.text(msNSZSOO + " " + (insignificancePSS ? msNeznzczimo : msZnaczimo) + ".", Report.FONT_NORMAL_12);
            report.text(msNSZRSOO + " " + (insignificanceWRS ? msNeznzczimo : msZnaczimo) + ".", Report.FONT_NORMAL_12);
        }
        else {
            if (statisticalInsignificancePSS) {
                report.createTable(new Object[][] {
                        { "", msFSS },
                        { msMDRNSZ, deltaPPSS },
                        { "2*|R1-R2|/(R1+R2)*100%", difPSS },
                        { msSNR, msNeznzczimo }
                });
            }
            else {
                if (pssCount > 0 && wrsCount > 0) {
                    report.createTable(new Object[][]{
                            { "", msFSS },
                            { msMDRNSZ, deltaPPSS },
                            { "2*|R1-R2|/(R1+R2)*100%", difPSS },
                            { msSNR, statisticalInsignificancePSS ? msNeznzczimo : msZnaczimo },
                            { getDeltaSqrt(), practDiffPSS},
                            { msPNR, practicalInsignificancePSS ? msNeznzczimo : msZnaczimo }
                    });
                }
            }
            report.newLine();
            report.text(msConclusion, Report.FONT_BOLD_12);
            report.text(msNSZSOO + " " + (insignificancePSS ? msNeznzczimo : msZnaczimo) + ".", Report.FONT_NORMAL_12);
        }
    }

    @Override
    protected void showDrift() {
        report.newLine();
        report.text(msPOD, Report.FONT_BOLD_12);
        report.newLine();
        Object[][] cells = new Object[data.commonXCount()][3];
        int k = 0;
        for (int i = 0; i < data.size(); i++) {
            for (int j = 0; j < data.sizeSorted(i); j++) {
                cells[k][0] = data.getNameSorted(i);
                cells[k][1] = data.getXSorted(i, j);
                cells[k][2] = data.getDateSorted(i, j);
                k++;
            }
        }
        report.createTable(new Object[] {msNameOfSolution, msAnalyticalSignal, msMeasurementRuntime}, cells);
        report.newLine();
        report.newLine();
        report.createTable(new Object[][] {
                { msOKI, commonXCount },
                { msOCSS, pooledNumberOfDegreesOfFreedom.getInt() },
                {msTwoSidedStudent_sTTest, twoSidedStudent99 }
        });
        report.newLine();
        report.newLine();
        Object[] header = new Object[data.size() + 1];
        header[0] = msName;
        for (int i = 0; i < data.size(); i++) {
            header[i + 1] = data.getNameSorted(i);
        }
        cells = new Object[10][data.size() + 1];
        cells[0][0] = "n";
        for (int i = 0; i < data.size(); i++) {
            cells[0][i + 1] = data.sizeSorted(i);
        }
        cells[1][0] = "a";
        System.arraycopy(aCoefs, 0, cells[1], 1, data.size());
        cells[2][0] = "b";
        System.arraycopy(bCoefs, 0, cells[2], 1, data.size());
        cells[3][0] = "Sb";
        System.arraycopy(sb, 0, cells[3], 1, data.size());
        cells[4][0] = "t";
        System.arraycopy(tValues, 0, cells[4], 1, data.size());
        cells[5][0] = "X" + msBegin;
        System.arraycopy(xBegin, 0, cells[5], 1, data.size());
        cells[6][0] = "X" + msEnd;
        System.arraycopy(xEnd, 0, cells[6], 1, data.size());
        cells[7][0] = msDifference;
        System.arraycopy(differences, 0, cells[7], 1, data.size());
        cells[8][0] = "Xj";
        System.arraycopy(xM, 0, cells[8], 1, data.size());
        cells[9][0] = msPD;
        for (int i = 0; i < data.size(); i++) {
            cells[9][i + 1] = significant[i] ? msZnaczimo : msNeznzczimo;
        }
        report.createTable(header, cells);
        report.newLine();
        report.text(msConclusion, Report.FONT_BOLD_12);
        if (drift) {
            report.text(msDriftIsStatisticallyNonSignificantForIndividualSamples + ".");//Дрейф для отдельных выборок статистически незначим
        }
        else
        {
            String s = "";
            report.text(msDSZD + ": ");//Дрейф статистически значим для выборок
            for (int i = 0; i < data.size(); i++) {
                if (!drifts[i]) {
                    report.text("  " + data.getName(i) + " " + s);
                }
            }
        }
        report.newLine();
    }

    @Override
    protected void showDriftError(Times driftResult) {
        report.newLine();
        report.text(msWarning, Report.FONT_BOLD_12);
        switch (driftResult) {
            case INSUFFICIENT:
                report.text(msInsufficientCountOfParallelMeasurements + ". " + msDriftCannotBeCalculated + ".",
                        Report.FONT_NORMAL_12);
                break;
            case MISSED:
                report.text(msAnalysisTimeIsMissedDriftCannotBeCalculated + ".", Report.FONT_NORMAL_12);
                break;
            case WRONG:
                report.text(msWrongTimes + ".", Report.FONT_NORMAL_12);
                break;
        }
        report.newLine();
    }

    @Override
    protected void showUncertainty() {
        report.newLine();
        if (reqDeltaWRS.getValue() > 0) {
            report.text(msUncertaintyOfCertifiedValueOfWRS, Report.FONT_BOLD_12);
            report.newLine();
                report.createTable(new Object[][]{
                        {msUncertaintyOfCertifiedValueOfWRS + ", %", wrsCount == 2 ? delta_p : delta_unit },
                        {msDeltaWRS + ", %", reqDeltaWRS }
                });
                report.text(msConclusion, Report.FONT_BOLD_12);
                report.text(msDeltaWRS + " " + (uncertainties ? msSatisfied : msNotSatisfied) + ".", Report.FONT_NORMAL_12);
        }
        else
        {
            report.text(msWarning, Report.FONT_BOLD_12);
            report.text(msRequirementToUncertaintySortedOfWRSIsMissed + ". " + msUncertaintyOfCertifiedValueOfWRS
                            + " " + msNotCalculated + ".", Report.FONT_NORMAL_12);
            report.newLine();
        }
    }

    @Override
    protected void showConclusions() {
        report.newLine();
        report.text(msConclusions, Report.FONT_BOLD_12);
        if (certifiedValue != null) {
            if (!data.getSSName().isEmpty()) {
                report.text(msAccordingToTestResultsForWRS + " \"" + data.getSSName() + "\" " +
                        msEstablishedCertifiedValue + " " + certifiedValue + " %.", Report.FONT_NORMAL_12);
            }
            else {
                report.text(msAccordingToTestResultsForWRS + " " + msEstablishedCertifiedValue + " "
                        + certifiedValue + " %.", Report.FONT_NORMAL_12);
            }
        }
        if ((flag & FLAG_ASCERTAINMENT) != 0) {
            report.text(msDeltaWRS + " " + (uncertainties ? msSatisfied : msNotSatisfied) + ".", Report.FONT_NORMAL_12);
        }
        if ((flag & FLAG_HOMOGENEITY) != 0) {
            report.text(delta_unit.getRounded() <= reqDeltaUnit.getRounded() ? msTodiesuvOp + "." : "" +
                    msTodiesunvOnp + ".", Report.FONT_NORMAL_12);
        }
        if (commonAnalyses == commonOK) {
            report.text(msRequirementsForTheResultsQualityCriteriaAreSatisfied + ".", Report.FONT_NORMAL_12);
        }
        else {
            report.text(msRequirementsForTheResultsQualityCriteriaAreNotSatisfied + ".", Report.FONT_NORMAL_12);
        }
        report.newLine();
        report.createTable(new Object[][] {
                { "", msAppointmentName, msSignature },
                { msReportWasPreparedBy, "", "" },
                { msReportWasCheckedBy, "", "" }
        });
    }

    @Override
    protected void errorMessage(DataCheck check) {
        if (check == DataCheck.NO_PSS && flag == FLAG_HOMOGENEITY) {
            return;
        }
        report.newLine();
        report.text(msWarning, Report.FONT_BOLD_12);
        switch (check) {
            case NO_WRS:
                report.text(msNoWRSData + ".", Report.FONT_NORMAL_12);
                break;
            case NO_PSS:
                report.text(msNoRSData + ".", Report.FONT_NORMAL_12);
                break;
            case WRONG_PSS_COUNT:
                report.text(msTheNumberOfRSSamplesIsGreaterThanTwo + ".", Report.FONT_NORMAL_12);
                break;
            case WRONG_WRS_COUNT:
                report.text(msTheNumberOfWRSSamplesIsGreaterThanTwo + ".", Report.FONT_NORMAL_12);
                break;
            case WRONG_X_COUNT:
                report.text(msDifferentNumberOfParallelMeasurementsUsedForDifferentSamples + "!", Report.FONT_NORMAL_12);
                break;
            case WRONG_PSS_WRS_COUNT:
                report.text(msNumberOfWrsAndRsTestsDoesNotMatchTheChosenSchema + "!", Report.FONT_NORMAL_12);
                break;
        }
    }

    @Override
    protected String getErrorMessage(DataCheck check) {
        switch (check) {
            case NO_WRS:
                return msNoWRSData.toString();
            case NO_PSS:
                return msNoRSData.toString();
            case WRONG_PSS_COUNT:
                return msTheNumberOfRSSamplesIsGreaterThanTwo.toString();
            case WRONG_WRS_COUNT:
                return msTheNumberOfWRSSamplesIsGreaterThanTwo.toString();
            case WRONG_X_COUNT:
                return msDifferentNumberOfParallelMeasurementsUsedForDifferentSamples.toString();
            case WRONG_PSS_WRS_COUNT:
                return msNumberOfWrsAndRsTestsDoesNotMatchTheChosenSchema.toString();
        }
        return null;
    }

    @Override
    protected byte[] report() {
        return report.getResult();
    }

    @Override
    protected void showWSSSamplesHomogeneity() {
        if (wrsCount > 2) {
            report.newLine();

            report.text(msCheckingTheHomogeneityOfWRS.toString(), Report.FONT_BOLD_12);
            report.newLine();
            if (report.getDocType() == Report.DocType.HTML) {
                report.createTable(new Object[][]{
                        {optionsData.getSides() == OptionsData.OneTwo.ONE ? msOKS : msTwoSidedStudent_sTTest, studentPCO},
                        {report.withSub(msTheRelativeStandardDeviationForASinglePackage +
                                " (RSD", "Unit", ")"), rsdp_unit},
                        {report.withSub(msOdiEsy + " (∆", "Unit", "), %"), delta_unit},
                        {report.withSub(msOd + " (∆", "Unit", "), %"), reqDeltaUnit}
                });
            }
            else {
                report.createTable(new Object[][]{
                        {optionsData.getSides() == OptionsData.OneTwo.ONE ? msOKS : msTwoSidedStudent_sTTest, studentPCO},
                        {getRSDUnit(), rsdp_unit},
                        {getDeltaUnit1(), delta_unit},
                        {getDeltaUnit2(), reqDeltaUnit}
                });
            }
            report.newLine();
            report.text(msConclusion, Report.FONT_BOLD_12);
            report.text(delta_unit.getRounded() <= reqDeltaUnit.getRounded() ? msTodiesuvOp + "." : "" +
                    msTodiesunvOnp + ".", Report.FONT_NORMAL_12);
        }
    }

    private Phrase getRSDUnit() {
        Phrase result = new Phrase(msTheRelativeStandardDeviationForASinglePackage + "(", Report.FONT_NORMAL_10);
        result.add(Report.image(rsdUnit));
        result.add(new Chunk("), %", Report.FONT_NORMAL_10));
        return result;
    }

    private Phrase getDeltaUnit1() {
        Phrase result = new Phrase(msOdiEsy + "(", Report.FONT_NORMAL_10);
        result.add(Report.image(deltaUnit));
        result.add(new Chunk("), %", Report.FONT_NORMAL_10));
        return result;
    }

    private Phrase getDeltaUnit2() {
        Phrase result = new Phrase(msOd + "(", Report.FONT_NORMAL_10);
        result.add(Report.image(deltaUnit));
        result.add(new Chunk("), %", Report.FONT_NORMAL_10));
        return result;
    }

    private Object getDeltaSqrt() {
        switch (docType) {
            case PDF: return report.phrase(Report.image(deltaSqrt));
            case HTML: return "∆∙√2";
        }
        return null;
    }
}
