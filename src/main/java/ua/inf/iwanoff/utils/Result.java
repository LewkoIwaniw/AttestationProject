package ua.inf.iwanoff.utils;

/**
 * Represents a result value with formatting and comparison capabilities[cite: 7].
 */
public class Result {

    /**
     * Compares two Result objects to check if the first is less than or equal to the second[cite: 7].
     * 
     * @param r1 the first result[cite: 7]
     * @param r2 the second result[cite: 7]
     * @return true if r1 is less than or equal to r2, false otherwise[cite: 7]
     */
    public static boolean lessOrEquals(Result r1, Result r2) {
        double x1 = Double.parseDouble(r1.toString());
        double x2 = Double.parseDouble(r2.toString());
        return x1 <= x2;
    }

    /**
     * Compares the absolute value of the first Result object to the second Result object[cite: 7].
     * 
     * @param r1 the first result[cite: 7]
     * @param r2 the second result[cite: 7]
     * @return true if the absolute value of r1 is less than or equal to r2, false otherwise[cite: 7]
     */
    public static boolean lessOrEqualsAbs(Result r1, Result r2) {
        double x1 = Double.parseDouble(r1.toString());
        double x2 = Double.parseDouble(r2.toString());
        return Math.abs(x1) <= x2;
    }

    /**
     * Compares two Result objects to check if the first is greater than or equal to the second[cite: 7].
     * 
     * @param r1 the first result[cite: 7]
     * @param r2 the second result[cite: 7]
     * @return true if r1 is greater than or equal to r2, false otherwise[cite: 7]
     */
    public static boolean greaterOrEquals(Result r1, Result r2) {
        double x1 = Double.parseDouble(r1.toString());
        double x2 = Double.parseDouble(r2.toString());
        return x1 >= x2;
    }

    private Double value;
    private Integer digits, common, afterPoint;

    /**
     * Constructs a Result object with specified digit precision[cite: 7].
     * 
     * @param digits the number of digits[cite: 7]
     */
    public Result(int digits) {
        this.digits = digits;
    }

    /**
     * Constructs a Result object with specified digit precision and value[cite: 7].
     * 
     * @param digits the number of digits[cite: 7]
     * @param value the result value[cite: 7]
     */
    public Result(int digits, Double value) {
        this.digits = digits;
        this.value = value;
    }

    /**
     * Constructs a Result object with common and after-point formatting parameters[cite: 7].
     * 
     * @param common the common part count[cite: 7]
     * @param afterPoint the digits after point count[cite: 7]
     */
    public Result(int common, int afterPoint) {
        this.common = common;
        this.afterPoint = afterPoint;
    }

    /**
     * Factory method to create a Result instance with specified digits[cite: 7].
     * 
     * @param digits the number of digits[cite: 7]
     * @return a new Result instance[cite: 7]
     */
    public static Result create(int digits) {
        return new Result(digits);
    }

    /**
     * Factory method to create a Result instance with common and after-point parameters[cite: 7].
     * 
     * @param common the common part count[cite: 7]
     * @param afterPoint the digits after point count[cite: 7]
     * @return a new Result instance[cite: 7]
     */
    public static Result create(int common, int afterPoint) {
        return new Result(common, afterPoint);
    }

    /**
     * Creates an array of Result objects from an array of doubles and digit precision[cite: 7].
     * 
     * @param arr the array of double values[cite: 7]
     * @param digits the number of digits[cite: 7]
     * @return an array of Result objects[cite: 7]
     */
    public static Result[] createArray(double[] arr, int digits) {
        Result[] results = new Result[arr.length];
        for (int i = 0; i < results.length; i++) {
            results[i] = create(digits).setValue(arr[i]);
        }
        return results;
    }

    /**
     * Retrieves the underlying value[cite: 7].
     * 
     * @return the value[cite: 7]
     */
    public Double getValue() {
        return value;
    }

    /**
     * Retrieves the value as an integer[cite: 7].
     * 
     * @return the integer value[cite: 7]
     */
    public int getInt() {
        return value.intValue();
    }

    /**
     * Retrieves the rounded value based on the formatting rules[cite: 7].
     * 
     * @return the rounded double value[cite: 7]
     */
    public Double getRounded() {
        return Double.parseDouble(toString());
    }

    /**
     * Retrieves the rounded value with specified decimal precision[cite: 7].
     * 
     * @param d the precision parameter[cite: 7]
     * @return the rounded double value[cite: 7]
     */
    public Double getRounded(int d) {
        String s = StringUtils.str(value, d);
        return Double.parseDouble(s);
    }

    /**
     * Constructs a Result object with specified value and digit precision[cite: 7].
     * 
     * @param value the result value[cite: 7]
     * @param digits the number of digits[cite: 7]
     */
    public Result(double value, int digits) {
        this.value = value;
        this.digits = digits;
    }

    /**
     * Constructs a Result object with specified value, common, and after-point parameters[cite: 7].
     * 
     * @param value the result value[cite: 7]
     * @param common the common part count[cite: 7]
     * @param afterPoint the digits after point count[cite: 7]
     */
    public Result(double value, int common, int afterPoint) {
        this.value = value;
        this.common = common;
        this.afterPoint = afterPoint;
    }

    /**
     * Sets the value for this Result instance[cite: 7].
     * 
     * @param value the value to set[cite: 7]
     * @return this Result instance[cite: 7]
     */
    public Result setValue(Double value) {
        this.value = value;
        return this;
    }

    /**
     * Returns a string representation of the formatted result[cite: 7].
     * 
     * @return the string representation[cite: 7]
     */
    @Override
    public String toString() {
        return digits != null ? StringUtils.str(value, digits) :
               (common != null && afterPoint != null ? StringUtils.str(value, common, afterPoint) : null);
    }
}