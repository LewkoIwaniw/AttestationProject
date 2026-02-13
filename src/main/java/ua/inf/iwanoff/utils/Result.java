package ua.inf.iwanoff.utils;

/**
 * Created by Leo on 10.05.18.
 */
public class Result {
    public static boolean lessOrEquals(Result r1, Result r2) {
        double x1 = Double.parseDouble(r1.toString());
        double x2 = Double.parseDouble(r2.toString());
        return x1 <= x2;
    }

    public static boolean lessOrEqualsAbs(Result r1, Result r2) {
        double x1 = Double.parseDouble(r1.toString());
        double x2 = Double.parseDouble(r2.toString());
        return Math.abs(x1) <= x2;
    }

    public static boolean greaterOrEquals(Result r1, Result r2) {
        double x1 = Double.parseDouble(r1.toString());
        double x2 = Double.parseDouble(r2.toString());
        return x1 >= x2;
    }

    private Double value;
    private Integer digits, common, afterPoint;

    public Result(int digits) {
        this.digits = digits;
    }

    public Result(int digits, Double value) {
        this.digits = digits;
        this.value = value;
    }

    public Result(int common, int afterPoint) {
        this.common = common;
        this.afterPoint = afterPoint;
    }

    public static Result create(int digits) {
        return new Result(digits);
    }

    public static Result create(int common, int afterPoint) {
        return new Result(common, afterPoint);
    }

    public static Result[] createArray(double[] arr, int digits) {
        Result[] results = new Result[arr.length];
        for (int i = 0; i < results.length; i++) {
            results[i] = create(digits).setValue(arr[i]);
        }
        return results;
    }

    public Double getValue() {
        return value;
    }

    public int getInt() {
        return value.intValue();
    }

    public Double getRounded() {
        return Double.parseDouble(toString());
    }

    public Double getRounded(int d) {
        String s = StringUtils.str(value, d);
        return Double.parseDouble(s);
    }


    public Result(double value, int digits) {
        this.value = value;
        this.digits = digits;
    }

    public Result(double value, int common, int afterPoint) {
        this.value = value;
        this.common = common;
        this.afterPoint = afterPoint;
    }

    public Result setValue(Double value) {
        this.value = value;
        return this;
    }

    @Override
    public String toString() {
        return digits != null ? StringUtils.str(value, digits) :
               (common != null && afterPoint != null ? StringUtils.str(value, common, afterPoint) : null);
    }
}
