package ua.inf.iwanoff.attestation.utils;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import static java.lang.Math.sqrt;
import static ua.inf.iwanoff.utils.MathUtils.sumOfSquares;

public class MathUtils {

    public static class BadData extends Exception { }
    public static class NoData extends Exception { }
    public static class WrongTimeFormatException extends Exception { }

    public static final double epsilon = 0.00001;

    public static double stdDev(double[] arr, int freedom) {
        double mAver = Arrays.stream(arr).average().getAsDouble();
        int len = arr.length;
        return sqrt(Arrays.stream(arr).map(v -> (v - mAver) * (v - mAver)).sum() / (len - freedom));
    }

    public static double relStdDev(double[] arr) {
        double aver = average(arr);
        return aver == 0 ? 0 : stdDev(arr, 1) / aver * 100;
    }

    public static double averNormalized(double[] arr, double c) {
        return average(arr) / c;
    }
    /**
     * Restricts the floating point value so it will be represented by the given count
     * of significant digits. If the first digit is "one", the result contains one more digit
     *
     * @param x floatong point value
     * @param digits count of significant digits
     * @return restricted value
     */
    public static String significant(double x, int digits) {
        BigDecimal bd = new BigDecimal(x);
        digits += oneAtFirst(x);
        return String.format("%."+ digits +"G", bd);
    }

    private static int oneAtFirst(double x) {
        String s = x + "";
        while((s.charAt(0) <= '0' || s.charAt(0) > '9') && s.length() > 1) {
            s = s.substring(1);
        }
        if (s.length() == 0 || s.charAt(0) != '1') {
            return 0;
        }
        return 1;
    }

    public static double SignificantDouble(double x, int digits)
    {
        int sign = (x >= 0) ? 1 : -1;
        x *= sign;
        String s = String.format("{0:E" + digits + "}", sign * x);
        if (s.charAt(0) != '1')
            s = String.format("{0:E" + (digits - 1) + "}", sign * x);
        return Double.parseDouble(s);
    }

    public static <T>void Fill(T[] arr, T value)
    {
        for (int i = 0; i < arr.length; i++)
        {
            arr[i] = value;
        }
    }

    public static double sum(double... arr) {
        return Arrays.stream(arr).sum();
    }

//    public static double sumOfSquares(double... arr) {
//        return Arrays.stream(arr).reduce(0, (x, y) -> x + y * y);
//    }

    public static double squaredSum(double... arr) {
        double sum = Arrays.stream(arr).sum();
        return sum * sum;
    }

    /**
     * Calculates integer power of floating point argument
     *
     * @param x - base of power
     * @param n - index of power
     * @return n-th power of getX
     */
    public static final double power(double x, int n) {
        if (x == 0 && n == 0)
            return 1;
        if (x == 0)
            return 0;
        return n < 0 ? 1 / power(x, -n) : (n == 0 ? 1 : x * power(x, n - 1));
    }

//    public static double Sum(double[] a)
//    {
//        double sum = 0;
//        for (double getX : a)
//        sum += getX;
//        return sum;
//    }
//
//    public static double Sum2(double[] a)
//    {
//        double sum = 0;
//        for (double getX : a)
//        sum += getX * getX;
//        return sum;
//    }

//    public static double Average(double[] a)
//    {
//        double sum = 0;
//        for (double getX : a)
//        sum += getX;
//        return sum / a.length;
//    }

    public static double average(double[] a) {
        return Arrays.stream(a).average().getAsDouble();
    }

    public static double AverageG(double[] a)
    {
        double sum = 0;
        for (double x : a)
        sum += x;
        return sum / (a.length - 1);
    }

    public static int forBartlett = 0;

    public static double StandardDeviation(double[] a)
    {
        double sum = 0;
        for (double x : a)
        sum += x * x;
        double result = sum - a.length * power(average(a), 2);
        if (result == 0 && forBartlett != 0)
        {
            result = Math.pow(10, -forBartlett) * 0.41;
            return result;
        }
        if (a.length > 1 && result > 0)
            return Math.sqrt(result / (a.length - 1));
        else
            return 0; // ????
    }

    public static double relativeStandardDeviation(double[] a)
    {
        if (average(a) != 0)
        {
            //double sd = StandardDeviation(a);
            //if (sd == 0)
            //////////////////////////////////////////////////////////////////////!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            // sd = ForcedDeviation(a);
            return (StandardDeviation(a) / average(a)) * 100;
        }
        else
            return 0; // ????
    }

    public static double ForcedDeviation(double[] a)
    {
        return 0.41;
    }

    public static double AverageNormalizedValue(double[] a, double c)
    {
        if (c != 0)
        {
            double sum = 0;
            for (int i = 0; i < a.length; i++)
                sum += a[i] / c;
            return sum / a.length;
        }
        else
            return 0;
    }

    public static double DifferenceNormalizedValue(double[] a)
    {
        return Math.abs(a[0] - a[1]) / (a[0] + a[1]) * 2 * 100;
    }

    public static double[] Deviation(double[] a)
    {
        double[] d = new double[a.length];
        double av = average(a);
        for (int i = 0; i < a.length; i++)
        {
            if (av != 0)
                d[i] = ((a[i] - av) / av) * 100;
        }
        return d;
    }

    public static int fp(int[] f)
    {
        int sum = 0;
        for (int i = 0; i < f.length; i++)
            sum += f[i] - 1;

        return sum;
    }

    public static int Fp2(int[] f)
    {
        int sum = 0;
        for (int i = 0; i < f.length; i++)
            sum += f[i] - 2;

        return sum;
    }

    public static double UnitedRelativeStandardDeviation(double[] r)
    {
        double sum = 0;
        for (int i = 0; i < r.length; i++)
            sum += r[i] * r[i];

        return Math.sqrt(sum / r.length);
    }

    public static double UnitedRelativeStandardDeviationU(double[] r, int[] f) /*throws BadData*/ {
//        if (fp(f) < 1)
//            throw new BadData();
        double sum = 0;
        for (int i = 0; i < r.length; i++)
            sum += (f[i] - 1) * r[i] * r[i];

        return Math.sqrt(sum / fp(f));
    }

    public static double DWRS(double[] x, double m)
    {

        return average(x) - m;
    }

    public static double RSD_unit(double[] x, double count)
    {
        return Math.sqrt(sumOfSquares(x) / count);
    }

    //------------------------------------------

//    public static double sum_x(double[] getX)  // расчет для таблицы результатов дрейфа суммы х
//    {
//        double sum = 0;
//        for (double v: getX)
//        sum += v;
//        return sum;
//    }
//
//    public static double sum_x2(double[] getX)  // расчет для таблицы результатов дрейфа суммы х*getX
//    {
//        double sum = 0;
//        for (double v: getX)
//        sum += v * v;
//        return sum;
//    }

//    public static double sum_time(double[] t)  // расчет для таблицы результатов дрейфа суммы time
//    {
//        double sum = 0;
//        for (double time: t)
//        {
//            //time - time из  таблицы исходных данных
//            sum += time;
//        }
//        return sum;
//    }

//    public static double sum_time2(double[] t)  // расчет для таблицы результатов дрейфа суммы time*time
//    {
//        double sum = 0;
//        for (double time: t)
//        {
//            sum += time * time;
//        }
//        return sum;
//    }

    public static double sum_x_time(double[] x, double[] t)  // расчет для таблицы результатов дрейфа суммы х*time
    {
        double sum = 0;
        for (int i = 0; i < x.length; i++)
        {
            sum += x[i] * t[i];
        }
        return sum;
    }

    public static double b(double[] x, double[] t) throws NoData // расчет для таблицы результатов дрейфа b (гориз. ось - время)
    {
        if (x.length <= 1)
            throw new NoData();
        double result = 0;
        try
        {
            double s = x.length * sum_x_time(x, t) - sum(x) * sum(t);
            double z = (x.length * sumOfSquares(t) - sum(t) * sum(t));
            // result = (nj() * sum_x_time() - sum_x() * sum_time())
            //              / (nj() * sum_time2() - sum_time() * sum_time());
            if (Math.abs(z) < epsilon)
                throw new BadData();
            result = s / z;
        }
        catch (BadData ex) { }
        return result;
    }

    public static double a(double[] x, double[] t) throws NoData // расчет для таблицы результатов дрейфа a (гориз. ось - время)
    {
        if (x.length <= 1)
            throw new NoData();
        double result = 0;
        try
        {
            double sx = sum(x);
            double b_ = b(x, t);
            double st = sum(t);
            //  result = (sum_x() - b() * sum_time()) / nj();
            result = (sx - b_ * st) / x.length;
        }
        catch (Exception bd) { }
        return result;
    }


    public static double S0(double[] x, double[] t) throws NoData // расчет для таблицы результатов дрейфа S0 (гориз. ось - время)
    {
        if (x.length <= 2)
            throw new NoData();
        double result = 0;
        try
        {

            double sx2 = sumOfSquares(x);
            double sx = sum(x);
            double a_ = a(x, t);
            double b_ = b(x, t);
            double sxt = sum_x_time(x, t);
            double sq = sx2 - a_ * sx - b_ * sxt;
            if (((sq) / (x.length - 2)) < 0)
                throw new BadData();
            result = Math.sqrt((sx2 - a_ * sx - b_ * sxt) / (x.length - 2));
        }
        catch (BadData bd) { }
        return result;
    }

    public static double Sb(double[] x, double[] t) throws NoData // расчет для таблицы результатов дрейфа Sb (гориз. ось - время)
    {
        if (x.length <= 2)
            throw new NoData();
        double result = 0;
        try
        {
            double st2 = sumOfSquares(t);
            double st = sum(t);
            double n = x.length;
            double s0 = S0(x, t);
            if (Math.abs((n * st2 - st * st)) < epsilon)
                throw new BadData();
            if (((n * s0 * s0) / (n * st2 - st * st)) < 0)
                throw new BadData();
            result = Math.sqrt((n * s0 * s0) / (n * st2 - st * st));
        }
        catch (BadData bd) { }
        return result;
    }

    public static double Sa(double[] x, double[] t) throws NoData // расчет для таблицы результатов дрейфа Sa (гориз. ось - время)
    {
        if (x.length <= 2)
            throw new NoData();
        double result = 0;
        try
        {
            double st2 = sumOfSquares(t);
            double n = x.length;
            double sb = Sb(x, t);
            if (n == 0)
                throw new BadData();
            if (((sb * sb * st2) / n) < 0)
                throw new BadData();
            result = Math.sqrt((sb * sb * st2) / n);
        }
        catch (BadData bd) { }
        return result;
    }

    public static double t(double[] x, double[] t) throws NoData // расчет для таблицы результатов дрейфа t (Student)
    {
        if (x.length <= 2)
            throw new NoData();
        double result = 0;
        try
        {
            double b_ = b(x, t);
            double sb_ = Sb(x, t);
            if (Math.abs(sb_) < epsilon)
                throw new BadData();
            result = b_ / sb_;
        }
        catch (BadData bd) { }
        return result;
    }

    public static double x_m(double[] x) // расчет для таблицы результатов дрейфа getX cp.
    {
        double result = sum(x) / x.length;
        return result;
    }

    public static double x_m_percent(double[] x) // расчет для таблицы результатов дрейфа getX cp. в %
    {
        double result = x_m(x) * 100;
        return result;
    }

    public static double t_m(double[] t) // расчет для таблицы результатов дрейфа t cp.
    {
        double result = sum(t) / t.length;
        return result;
    }

    public static double t_m_percent(double[] t) // расчет для таблицы результатов дрейфа t cp. в %
    {
        double result = t_m(t) * 100.0;
        return result;
    }

    public static double difference(double[] x, double[] t) throws NoData // расчет для таблицы результатов дрейфа getX кон. (х max)
    {
        if (x.length <= 1)
            throw new NoData();
        double result = 0;
        try
        {
            double xb = x_b(x, t);
            double xe = x_e(x, t);
            //result = 2 * 100 * (x_b() - x_e()) / (x_b() + x_e());
            if (Math.abs((xb + xe)) < epsilon)
                throw new BadData();
            result = 2.0 * 100.0 * (xe - xb) / (xb + xe);
        }
        catch (BadData bd) { }
        return result;
    }

    public static double st(double[] x, double[] t) throws NoData // расчет для таблицы результатов дрейфа t (Student)
    {
        if (x.length <= 2)
            throw new NoData();
        double result = 0;
        try
        {
            double b_ = b(x, t);
            double sb_ = Sb(x, t);
            if (Math.abs(sb_) < epsilon)
                throw new BadData();
            result = b_ / sb_;
        }
        catch (BadData bd) { }
        return result;
    }

    public static double x_b(double[] x, double[] t) throws NoData // расчет для таблицы результатов дрейфа getX нач.(соотв. tmin)
    {
        if (x.length <= 1)
            throw new NoData();
        double min = t[0]; //  time из  таблицы исходных данных
        double b_ = b(x, t);
        double a_ = a(x, t);
        return b_ * min + a_;
    }

    public static double x_e(double[] x, double[] t) throws NoData // расчет для таблицы результатов дрейфа getX кон. (соотв. tmax)
    {
        if (x.length <= 1)
            throw new NoData();
        double max = t[t.length - 1];
        double b_ = b(x, t);
        double a_ = a(x, t);
        return b_ * max + a_;
    }

    public static boolean drift_is(double[] x, double[] t, double f) throws NoData {
        if (x.length <= 2)
            throw new NoData();
        boolean d = false;
        try
        {
            double t_ = st(x, t);
            double S = Student.calcStudent(2, f, 99);
            d = (Math.abs(t_) > S); //!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        }
        catch (Exception bd) { }
        return d;
    }

    public static double t_m_unit(double[] x, double[] t) throws NoData {
        //double A, B, Sb;
        //int N;
        //if (fabs(N * sum_t2 - sum_t * sum_t) < epsilon)
        //  throw BadData();
        //B = (N * sum_x_t - sum_x * sum_t) / (N * sum_t2 - sum_t * sum_t);
        //A = (sum_x - B * sum_t) / N;
        //B = (N * sum_x_t - sum_x * sum_t) / (N * sum_t2 - sum_t * sum_t);
        //if (N <= 2)
        //  throw BadData();
        //double S0 = sqrt((sum_x2 - A * sum_x - B * sum_x_t) / (N - 2));

        //Sb = sqrt(N * S0 * S0 / (N * sum_t2 - sum_t * sum_t));
        // result = (nj() * sum_x_time() - sum_x() * sum_time())
        //              / (nj() * sum_time2() - sum_time() * sum_time());

        return b(x, t) / Sb(x, t);
    }

    public static int TimeToInt(String time) throws WrongTimeFormatException {
        if (time != null)
        {
            if (time.length() < 1)
                return 0;
            if (time.length() <= 6)
                return TimeToIntWithoutDate(time);
            String[] arr = {};// = time.split(new char[] { ' ', '_', '.' });
            if (arr.length < 5)
                throw new WrongTimeFormatException();
            try
            {
                int hour = Integer.parseInt(arr[0]);
                int min = Integer.parseInt(arr[1]);
                int day = Integer.parseInt(arr[2]);
                int month = Integer.parseInt(arr[3]);
                int year = Integer.parseInt(arr[4]);
                return min + 60 * (hour + 24 * (--day + 31 * (--month + 12 * (year - 1900))));
            }
            catch (Exception ex)
            {
                throw new WrongTimeFormatException();
            }
        }
        else
            return 0;
    }

    public static int TimeToIntWithoutDate(String time)
    {
        if (time != null)
        {
            if (time.length() < 1)
                return 0;
            String[] arr = {};// = time.split(new char[] { ' ', '_', '.' });
            if (arr.length < 2)
                return Integer.parseInt(time);
            int hour = Integer.parseInt(arr[0]);
            int min = Integer.parseInt(arr[1]);
            //return min;
            return min + 60 * hour;
        }
        else
            return 0;
    }

    public static double Qtab(int P, int n)
    {
      double[][] table = {{0.89, 0.94, 0.99},
            {0.68, 0.77, 0.89},
            {0.56, 0.64, 0.76},
            {0.48, 0.56, 0.70},
            {0.43, 0.51, 0.64},
            {0.40, 0.48, 0.58},
            {0.38, 0.46, 0.55}};
        switch (P){
            case 90: return table[n - 3][0];
            case 95: return table[n - 3][1];
            case 99: return table[n - 3][2];
            default: return 0;
        }
    }

    public static boolean Differ(List<String> dates)
    {
        SortedSet<Integer> sortedSet = new TreeSet<>();
        for (String s: dates)
        {
            try
            {
                int k = TimeToInt(s);
                if (!sortedSet.add(k))
                {
                    return false;
                }
            }
            catch (WrongTimeFormatException e) {
                return false;
            }
        }
        return true;
    }

//--------------------------------------------------


//--------------------------------------------------

public static class Student {
    static public double[][] St = new double[][]
            {
                    //side = 1     95.00    97.50   99.00   99.50     99.90    99.95
                    //side = 2     90.00    95.00   98.00   99.00     99.80    99.90
      /* 01 */{1, 6.3138, 12.7062, 31.8205, 63.6567, 318.310, 636.619},
      /* 02 */{2, 2.9200, 4.3027, 6.9646, 9.9248, 22.3271, 31.5991},
      /* 03 */{3, 2.3534, 3.1824, 4.5407, 5.8409, 10.2145, 12.9240},
      /* 04 */{4, 2.1318, 2.7764, 3.7469, 4.6041, 7.1732, 8.6103},
      /* 05 */{5, 2.0150, 2.5706, 3.3649, 4.0321, 5.8934, 6.8688},

      /* 06 */{6, 1.9432, 2.4469, 3.1427, 3.7074, 5.2076, 5.9588},
      /* 07 */{7, 1.8946, 2.3646, 2.9980, 3.4995, 4.7853, 5.4079},
      /* 08 */{8, 1.8595, 2.3060, 2.8965, 3.3554, 4.5008, 5.0413},
      /* 09 */{9, 1.8331, 2.2622, 2.8214, 3.2498, 4.2968, 4.7809},
      /* 10 */{10, 1.8125, 2.2281, 2.7638, 3.1693, 4.1437, 5.5869},

      /* 11 */{11, 1.7956, 2.2010, 2.7181, 3.1058, 4.0247, 4.4370},
      /* 12 */{12, 1.7823, 2.1788, 2.6810, 3.0545, 3.9296, 4.3178},
      /* 13 */{13, 1.7709, 2.1604, 2.6503, 3.0123, 3.8520, 4.2208},
      /* 14 */{14, 1.7613, 2.1448, 2.6245, 2.9768, 3.7874, 4.1405},
      /* 15 */{15, 1.7530, 2.1314, 2.6025, 2.9467, 3.7328, 4.0728},

      /* 16 */{16, 1.7459, 2.1199, 2.5835, 2.9208, 3.6862, 4.0150},
      /* 17 */{17, 1.7396, 2.1098, 2.5669, 2.8982, 3.6458, 3.9651},
      /* 18 */{18, 1.7341, 2.1009, 2.5524, 2.8784, 3.6105, 3.9216},
      /* 19 */{19, 1.7291, 2.0930, 2.5395, 2.8609, 3.5794, 3.8834},
      /* 20 */{20, 1.7247, 2.0860, 2.5280, 2.8453, 3.5518, 3.8495},

      /* 21 */{21, 1.7207, 2.0796, 2.5176, 2.8314, 3.5272, 3.8193},
      /* 22 */{22, 1.7171, 2.0739, 2.5083, 2.8188, 3.5050, 3.7921},
      /* 23 */{23, 1.7139, 2.0687, 2.4999, 2.8073, 3.4850, 3.7676},
      /* 24 */{24, 1.7109, 2.0639, 2.4922, 2.7969, 3.4668, 3.7454},
      /* 25 */{25, 1.7081, 2.0595, 2.4851, 2.7874, 3.4502, 3.7251},

      /* 26 */{26, 1.7056, 2.0555, 2.4786, 2.7787, 3.4350, 3.7066},
      /* 27 */{27, 1.7033, 2.0518, 2.4727, 2.7707, 3.4210, 3.6896},
      /* 28 */{28, 1.7011, 2.0484, 2.4671, 2.7633, 3.4082, 3.6739},
      /* 29 */{29, 1.6991, 2.0452, 2.4620, 2.7564, 3.3962, 3.6594},
      /* 30 */{30, 1.6973, 2.0423, 2.4573, 2.7500, 3.3852, 3.6460},

      /* 40 */{40, 1.6839, 2.0211, 2.4233, 2.7045, 3.3069, 3.5510},
      /* 50 */{50, 1.6759, 2.0086, 2.4033, 2.6778, 3.2614, 3.4960},
      /* 100 */{100, 1.6602, 1.9840, 2.3642, 2.6259, 3.1737, 3.3905},

      /* 00 */{101, 1.6479, 1.9647, 2.3338, 2.5857, 3.1066, 3.3101}};

    static private double Inter(double f, int j) {
        int k = 0;
        for (int i = 1; i < St.length; i++)
            if (St[i][0] > f) {
                k = i;
                break;
            }
        return St[k - 1][j] + (St[k][j] - St[k - 1][j]) * (f - St[k - 1][0])
                / (St[k][0] - St[k - 1][0]);
    }

    public static double calcStudent(int side, double f, double p) {

        int j;
        int p_100 = (int) (p * 100);
        if (side == 1) {
            switch (p_100) {
                case 9500:
                    j = 1;
                    break;
                case 9750:
                    j = 2;
                    break;
                case 9900:
                    j = 3;
                    break;
                case 9950:
                    j = 4;
                    break;
                case 9990:
                    j = 5;
                    break;
                case 9995:
                    j = 6;
                    break;
                default:
                    j = 1;
                    break;
            }
        }
        else {
            switch (p_100) {
                case 9000:
                    j = 1;
                    break;
                case 9500:
                    j = 2;
                    break;
                case 9800:
                    j = 3;
                    break;
                case 9900:
                    j = 4;
                    break;
                case 9980:
                    j = 5;
                    break;
                case 9990:
                    j = 6;
                    break;
                default:
                    j = 1;
                    break;
            }
        }
        if (f == 0)
            return St[St.length - 1][j];
        if (f > 100)
            return St[St.length - 1][j];
        double result = Inter(f, j);
        return result;

    }

    public static double gamma(double x) {
        double p = Math.exp(-0.57721566490153286060 * x) / x;
        for (int n = 1; n < 50000000; n++) {
            p *= Math.exp(x / n) / (1 + x / n);
        }
        return p;
    }

    private static double kn(int n) {
        double k1 = 1 / (Math.PI * Math.sqrt(n));
        double k2 = 0.5 / Math.sqrt(n);
        if (n < 2)
            return k1;
        if (n % 2 == 1) {
            for (double i = 2; i < n; i += 2)
                k1 *= i / (i - 1);
            return k1;
        }
        else {
            for (double i = 2; i < n; i += 2)
                k2 *= (i - 1) / i;
            k2 *= n - 1;
            return k2;
        }
    }

    public static double s(int n, double t) {
        if (n < 1)
            //throw new RuntimeException();
            return -1;
        double sum = 0;
        final double du = 0.01;
        double u = t - 0.5 * du;
        for (int i = 0; i < 1000000; i++, u -= du) {
            sum += du / Math.sqrt(power(1 + u * u / n, n + 1));
        }
        return sum * kn(n);
    }

    public static double calcPBasedOnStudent(int side, int f, double student) {
        return s(f, student);
    }


    public static void main(String[] args) {
      /* System.out.println("Side = 1");
       System.out.println();
       System.out.println("P = 95");
       for (int i = 1; i < 10; i++)
         System.out.println("n = " + i + " St = " + calcStudent(1, i, 95));
       System.out.println();
       System.out.println("P = 97.5");
       for (int i = 1; i < 10; i++)
         System.out.println("n = " + i + " St = " + calcStudent(1, i, 97.5));
       System.out.println();
       System.out.println("P = 99");
       for (int i = 1; i < 10; i++)
         System.out.println("n = " + i + " St = " + calcStudent(1, i, 99));
       System.out.println();
       System.out.println("P = 99.5");
       for (int i = 1; i < 10; i++)
         System.out.println("n = " + i + " St = " + calcStudent(1, i, 99.5));
       System.out.println();
       System.out.println("P = 99.9");
       for (int i = 1; i < 10; i++)
         System.out.println("n = " + i + " St = " + calcStudent(1, i, 99.9));
       System.out.println();
       System.out.println("P = 99.95");
       for (int i = 1; i < 10; i++)
         System.out.println("n = " + i + " St = " + calcStudent(1, i, 99.95));
       System.out.println();
       System.out.println("Side = 2");
       System.out.println();
       System.out.println("P = 90");
       for (int i = 1; i < 10; i++)
         System.out.println("n = " + i + " St = " + calcStudent(2, i, 90));
       System.out.println();
       System.out.println("P = 95");
       for (int i = 1; i < 10; i++)
         System.out.println("n = " + i + " St = " + calcStudent(2, i, 95));
       System.out.println();
       System.out.println("P = 98");
       for (int i = 1; i < 10; i++)
         System.out.println("n = " + i + " St = " + calcStudent(2, i, 98));
       System.out.println();
       System.out.println("P = 99");
       for (int i = 1; i < 10; i++)
         System.out.println("n = " + i + " St = " + calcStudent(2, i, 99));
       System.out.println();
       System.out.println("P = 99.8");
       for (int i = 1; i < 10; i++)
         System.out.println("n = " + i + " St = " + calcStudent(2, i, 99.8));
       System.out.println();
       System.out.println("P = 99.9");
       for (int i = 1; i < 10; i++)
         System.out.println("n = " + i + " St = " + calcStudent(2, i, 99.9));
       System.out.println("St = " + St.length);*/
        //System.out.println(s(1, 1));
        //System.out.println(s(1, 6.31));
        //System.out.println(s(2, 0.816));
        //System.out.println(s(2, 2.92));
        //System.out.println(s(120, 1.98));
        //System.out.println(s(5, 114.98));
        //System.out.println(s(4, 4.98));
        //System.out.println(s(5,-1.22));
    }
}

    public static class Bartlett {

        static final int n_elem = 18;

        enum AB {a, b};

        static class AB_pair {
            public double a, b;

            public AB_pair(double a, double b) {
                this.a = a;
                this.b = b;
            }
        }

        static double[] dc = new double[]{0, 0.5, 1, 1.5, 2, 2.5, 3, 3.5,
                4, 4.5, 5, 6, 7, 8, 9, 10, 12, 14};

        static class C {
            public static int int_c(double d) {
                for (int i = 0; i < n_elem; i++)
                    if (d == dc[i])
                        return i;
                return -1;
            }
        }

        double m(int k, AB ab, double c1) {
            AB_pair[][] m_arr = {
                    new AB_pair[]{

                            new AB_pair(5.99, 5.99),
                            new AB_pair(6.47, 6.22),
                            new AB_pair(6.89, 6.43),
                            new AB_pair(7.20, 6.64),
                            new AB_pair(7.38, 6.84),
                            new AB_pair(7.39, 7.03),
                            new AB_pair(7.22, 7.22)
                    },
                    new AB_pair[]{
                            new AB_pair(7.81, 7.81),
                            new AB_pair(8.24, 8.00),
                            new AB_pair(8.63, 8.17),
                            new AB_pair(8.96, 8.35),
                            new AB_pair(9.21, 8.52),
                            new AB_pair(9.38, 8.69),
                            new AB_pair(9.43, 8.85),
                            new AB_pair(9.37, 9.02),
                            new AB_pair(9.18, 9.18)
                    },
                    new AB_pair[]{
                            new AB_pair(9.49, 9.49),
                            new AB_pair(9.88, 9.65),
                            new AB_pair(10.24, 9.80),
                            new AB_pair(10.57, 9.96),
                            new AB_pair(10.86, 10.11),
                            new AB_pair(11.08, 10.27),
                            new AB_pair(11.24, 10.42),
                            new AB_pair(11.32, 10.57),
                            new AB_pair(11.31, 10.72),
                            new AB_pair(11.21, 10.87),
                            new AB_pair(11.02, 11.02)
                    },
                    new AB_pair[]{
                            new AB_pair(11.07, 11.07),
                            new AB_pair(11.43, 11.22),
                            new AB_pair(11.78, 11.36),
                            new AB_pair(12.11, 11.51),
                            new AB_pair(12.40, 11.65),
                            new AB_pair(12.65, 11.79),
                            new AB_pair(12.86, 11.94),
                            new AB_pair(13.01, 12.08),
                            new AB_pair(13.11, 12.22),
                            new AB_pair(13.14, 12.36),
                            new AB_pair(13.10, 12.50),
                            new AB_pair(12.78, 12.78)
                    },
                    new AB_pair[]{
                            new AB_pair(12.59, 12.59),
                            new AB_pair(12.94, 12.73),
                            new AB_pair(13.27, 12.87),
                            new AB_pair(13.59, 13.00),
                            new AB_pair(13.88, 13.14),
                            new AB_pair(14.15, 13.27),
                            new AB_pair(14.38, 13.41),
                            new AB_pair(14.58, 13.55),
                            new AB_pair(14.73, 13.68),
                            new AB_pair(14.83, 13.82),
                            new AB_pair(14.88, 13.95),
                            new AB_pair(14.81, 14.22),
                            new AB_pair(14.49, 14.49)
                    },
                    new AB_pair[]{
                            new AB_pair(14.07, 14.07),
                            new AB_pair(14.40, 14.20),
                            new AB_pair(14.72, 14.33),
                            new AB_pair(15.03, 14.46),
                            new AB_pair(15.32, 14.59),
                            new AB_pair(15.60, 14.72),
                            new AB_pair(15.84, 14.85),
                            new AB_pair(16.06, 14.98),
                            new AB_pair(16.25, 15.11),
                            new AB_pair(16.40, 15.25),
                            new AB_pair(16.51, 15.38),
                            new AB_pair(16.60, 15.64),
                            new AB_pair(16.49, 15.90),
                            new AB_pair(16.16, 16.16)
                    },
                    new AB_pair[]{
                            new AB_pair(15.51, 15.51),
                            new AB_pair(15.83, 15.63),
                            new AB_pair(16.14, 15.76),
                            new AB_pair(16.44, 15.89),
                            new AB_pair(16.73, 16.02),
                            new AB_pair(17.01, 16.14),
                            new AB_pair(17.26, 16.27),
                            new AB_pair(17.49, 16.40),
                            new AB_pair(17.70, 16.52),
                            new AB_pair(17.88, 16.65),
                            new AB_pair(18.03, 16.78),
                            new AB_pair(18.22, 17.03),
                            new AB_pair(18.26, 17.29),
                            new AB_pair(18.12, 17.54),
                            new AB_pair(17.79, 17.79)
                    },
                    new AB_pair[]{
                            new AB_pair(16.92, 16.92),
                            new AB_pair(17.23, 17.04),
                            new AB_pair(17.54, 17.17),
                            new AB_pair(17.83, 17.29),
                            new AB_pair(18.12, 17.41),
                            new AB_pair(18.39, 17.54),
                            new AB_pair(18.65, 17.66),
                            new AB_pair(18.89, 17.79),
                            new AB_pair(19.11, 17.91),
                            new AB_pair(19.31, 18.04),
                            new AB_pair(19.48, 18.16),
                            new AB_pair(19.75, 18.41),
                            new AB_pair(19.89, 18.66),
                            new AB_pair(19.89, 18.91),
                            new AB_pair(19.73, 19.16),
                            new AB_pair(19.40, 19.40)
                    },
                    new AB_pair[]{
                            new AB_pair(18.31, 18.31),
                            new AB_pair(18.61, 18.43),
                            new AB_pair(18.91, 18.55),
                            new AB_pair(19.20, 18.67),
                            new AB_pair(19.48, 18.79),
                            new AB_pair(19.76, 18.91),
                            new AB_pair(20.02, 19.04),
                            new AB_pair(20.26, 19.16),
                            new AB_pair(20.49, 19.28),
                            new AB_pair(20.70, 19.40),
                            new AB_pair(20.89, 19.52),
                            new AB_pair(21.21, 19.77),
                            new AB_pair(21.42, 20.01),
                            new AB_pair(21.52, 20.26),
                            new AB_pair(21.49, 20.50),
                            new AB_pair(21.32, 20.75)
                    },
                    new AB_pair[]{
                            new AB_pair(19.68, 19.68),
                            new AB_pair(19.97, 19.79),
                            new AB_pair(20.26, 19.91),
                            new AB_pair(20.55, 20.03),
                            new AB_pair(20.83, 20.15),
                            new AB_pair(21.10, 20.27),
                            new AB_pair(21.36, 20.39),
                            new AB_pair(21.61, 20.51),
                            new AB_pair(21.84, 20.63),
                            new AB_pair(22.06, 20.75),
                            new AB_pair(22.27, 20.87),
                            new AB_pair(22.62, 21.12),
                            new AB_pair(22.88, 21.36),
                            new AB_pair(23.06, 21.60),
                            new AB_pair(23.12, 21.84),
                            new AB_pair(23.07, 22.08),
                            new AB_pair(22.56, 22.56)
                    },
                    new AB_pair[]{
                            new AB_pair(21.03, 21.03),
                            new AB_pair(21.32, 21.14),
                            new AB_pair(21.60, 21.26),
                            new AB_pair(21.89, 21.38),
                            new AB_pair(22.16, 21.50),
                            new AB_pair(22.43, 21.62),
                            new AB_pair(22.69, 21.74),
                            new AB_pair(22.94, 21.85),
                            new AB_pair(23.18, 21.97),
                            new AB_pair(23.40, 22.09),
                            new AB_pair(23.62, 22.21),
                            new AB_pair(23.99, 22.45),
                            new AB_pair(24.30, 22.69),
                            new AB_pair(24.53, 22.92),
                            new AB_pair(24.66, 23.16),
                            new AB_pair(24.70, 23.40),
                            new AB_pair(24.44, 23.88)
                    },
                    new AB_pair[]{
                            new AB_pair(22.36, 22.36),
                            new AB_pair(22.65, 22.48),
                            new AB_pair(22.93, 22.60),
                            new AB_pair(23.21, 22.71),
                            new AB_pair(23.48, 22.83),
                            new AB_pair(23.75, 22.95),
                            new AB_pair(24.01, 23.06),
                            new AB_pair(24.26, 23.18),
                            new AB_pair(24.50, 23.30),
                            new AB_pair(24.73, 23.42),
                            new AB_pair(24.95, 23.53),
                            new AB_pair(25.34, 23.77),
                            new AB_pair(25.68, 24.00),
                            new AB_pair(25.95, 24.24),
                            new AB_pair(26.14, 24.48),
                            new AB_pair(26.25, 24.71),
                            new AB_pair(26.17, 25.19),
                            new AB_pair(25.66, 25.66)
                    },
                    new AB_pair[]{
                            new AB_pair(23.68, 23.68),
                            new AB_pair(23.97, 23.80),
                            new AB_pair(24.24, 23.92),
                            new AB_pair(24.52, 24.03),
                            new AB_pair(24.79, 24.15),
                            new AB_pair(25.05, 24.26),
                            new AB_pair(25.31, 24.38),
                            new AB_pair(25.56, 24.50),
                            new AB_pair(25.80, 24.61),
                            new AB_pair(26.04, 24.73),
                            new AB_pair(26.26, 24.85),
                            new AB_pair(26.67, 25.08),
                            new AB_pair(27.03, 25.31),
                            new AB_pair(27.33, 25.55),
                            new AB_pair(27.56, 25.78),
                            new AB_pair(27.73, 26.01),
                            new AB_pair(27.80, 26.48),
                            new AB_pair(27.50, 26.95)
                    }
            };

            if (C.int_c(c1) != -1) {
                if (ab == AB.a)
                    return m_arr[k - 3][C.int_c(c1)].a;
                else
                    return m_arr[k - 3][C.int_c(c1)].b;
            }
            else {
                int n_b = 0;
                int n_e = 0;
                for (int i = 0; i < n_elem; i++)
                    if (c1 < dc[i]) {
                        n_b = i - 1;
                        n_e = i;
                        break;
                    }
                double c_l_b = dc[n_b];
                double c_l_e = dc[n_e];
                if (ab == AB.a) {
                    double ma_b = m_arr[k - 3][n_b].a;
                    double ma_e = m_arr[k - 3][n_e].a;
                    double b = (c_l_b * ma_e - c_l_e * ma_b) / (c_l_b - c_l_e);
                    double a = (ma_b - b) / c_l_b;
                    return a * c1 + b;
                }
                else {
                    double mb_b = m_arr[k - 3][n_b].b;
                    double mb_e = m_arr[k - 3][n_e].b;
                    double b = (c_l_b * mb_e - c_l_e * mb_b) / (c_l_b - c_l_e);
                    double a = (mb_b - b) / c_l_b;
                    return a * c1 + b;
                }
            }
        }

        public static class Mm {
            double m_;
            double M;
            boolean uniformity;
            public double getCalculated() {
                return M;
            }
            public double getTabular() {
                return m_;
            }
            public boolean getUniformity() {
                return uniformity;
            }
        }

        public Mm Bartlett_(int[] nu, double[] s2, int k) throws BadData {
            Mm mm = new Mm();
            int n_el = 0;
            switch (k) {
                case 3: n_el = 7;
                    break;
                case 4: n_el = 9;
                    break;
                case 5: n_el = 11;
                    break;
                case 6: n_el = 12;
                    break;
                case 7: n_el = 13;
                    break;
                case 8: n_el = 14;
                    break;
                case 9: n_el = 15;
                    break;
                case 10: n_el = 16;
                    break;
                case 11: n_el = 16;
                    break;
                case 12: n_el = 17;
                    break;
                case 13: n_el = 17;
                    break;
                case 14: n_el = 18;
                    break;
                case 15: n_el = 18;
                    break;
                default: {
                    n_el = 0;
                    mm.M = 0;
                    mm.m_ = 0;
                    return null;
                }
            }
            mm.uniformity = true;
            int sum_nu = 0;
            double sum_nu_s2 = 0;
            double sum_nu_logs2 = 0;
            double ll;
            for (int i = 0; i < k; i++) {
                sum_nu += nu[i];
                ll = s2[i];
                ll = nu[i] * s2[i];
                sum_nu_s2 += nu[i] * s2[i];
                if (s2[i] <= 0)
                    throw new BadData();
                ll = Math.log(s2[i]);
                ll = nu[i] * Math.log(s2[i]);
                sum_nu_logs2 += nu[i] * Math.log(s2[i]);
            }
            //1:
            ll = (1.0 / sum_nu) * sum_nu_s2;
            if (ll <= 0)
                throw new BadData();
            ll = Math.log(ll);
            ll = sum_nu * ll;
            if (((1.0 / sum_nu) * sum_nu_s2) <= 0)
                throw new BadData();
            mm.M = sum_nu * Math.log((1.0 / sum_nu) * sum_nu_s2) - sum_nu_logs2;
            // double M = 8.60;
            //M = 8.30;
            double max_ma = 0;
            for (int i = 0; i < n_el; i++) {
                if (m(k, AB.a, dc[i]) > max_ma)
                    max_ma = m(k, AB.a, dc[i]);
            }
            double min_mb = max_ma;
            for (int i = 0; i < n_el; i++) {
                if ((m(k, AB.b, dc[i]) > 0) && (m(k, AB.b, dc[i]) < min_mb))
                    min_mb = m(k, AB.b, dc[i]);
            }
            mm.m_ = max_ma;
            if (max_ma <= mm.M) {
                mm.uniformity = false;
                return mm;
            }
            if (min_mb > mm.M) {
                mm.m_ = min_mb;
                return mm;
            }
            //2:
            mm.m_ = min_mb;
            if ((max_ma > mm.M) && (mm.M >= min_mb)) {
                double c1 = 0;
                for (int i = 0; i < k; i++)
                    c1 += 1.0 / nu[i];
                c1 = c1 - 1.0 / sum_nu;

                if (c1 > dc[n_el - 1]) {
                    mm.M = 0;
                    mm.m_ = 0;
                    return null;
                }

                if (m(k, AB.a, c1) <= mm.M) {
                    mm.m_ = m(k, AB.a, c1);
                    mm.uniformity = false;
                    return mm;
                }
                if (m(k, AB.b, c1) > mm.M) {
                    mm.m_ = m(k, AB.b, c1);
                    mm.uniformity = false;
                    return mm;
                }
                //3:
                if ((m(k, AB.a, c1) > mm.M) && (mm.M >= m(k, AB.b, c1))) {
                    double c3 = 0;
                    for (int i = 0; i < k; i++)
                        c3 += 1.0 / (nu[i] * nu[i] * nu[i]);
                    c3 = c3 - 1.0 / (sum_nu * sum_nu * sum_nu);
                    double C = (c1 * c1 * c1) / (k * k);
                    double dC = c1 - C;
                    double mQ = (1.0 / dC) * ((c1 - c3) * m(k, AB.a, c1) +
                            (c3 - C) * m(k, AB.b, c1));
                    mm.m_ = mQ;
                    if (mQ <= mm.M) {
                        mm.uniformity = false;
                    };
                }
            }
            return mm;
        }
    }

    public static double sqr(double x) {
        return x * x;
    }
}

