package ua.inf.iwanoff.utils;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

import static ua.inf.iwanoff.utils.MultiString.*;

/**
 * Utility class providing string manipulation, parsing, formatting, and mathematical rounding functions[cite: 3].
 */
public class StringUtils {

    /**
     * Counts the significant digits of a string representation.
     * 
     * @param s the string representation of a number
     * @return the count of significant digits
     */
    private static int digitsCount(String s) {
        s = s.replaceAll("[.,]", "");
        while (s.startsWith("0") || s.startsWith("-")) {
            s = s.substring(1);
        }
        while (s.endsWith("0")) {
            s = s.substring(0, s.length() - 1);
        }
        int len = s.length();
        if (s.charAt(0) == '1') {
            len--;
        }
        s = s.toLowerCase();
        if (s.contains("e")) {
            s = s.substring(s.indexOf("e"));
            len -= s.length();
        }
        return len;
    }
 
    /**
     * Checks if the first non-zero/non-sign digit of a double value is '1'.
     * 
     * @param x the double value to check
     * @return 1 if the first significant digit is '1', otherwise 0
     */
    private static int oneAtFirst(double x) {
        String s = x + "";
        while((s.charAt(0) <= '0' || s.charAt(0) > '9') && s.length() > 1) {
            s = s.substring(1);
        }
        if (s.isEmpty() || s.charAt(0) != '1') {
            return 0;
        }
        return 1;
    }

    /**
     * Restricts the floating point value so it will be represented by the given count
     * of significant digits. If the first digit is "one", the result contains one more digit[cite: 3]
     *
     * @param x floating point value[cite: 3]
     * @param digits count of significant digits[cite: 3]
     * @return restricted value[cite: 3]
     */
    public static String significant(double x, int digits) {
        BigDecimal bd = new BigDecimal(x);
        digits += oneAtFirst(x);
        return String.format("%."+ digits +"G", bd);
    }

    /**
     * Parses a double value restricted to a given count of significant digits.
     * 
     * @param x floating point value
     * @param digits count of significant digits
     * @return parsed double value
     */
    public static double value(double x, int digits) {
        double result = Double.parseDouble(significant(x, digits));
        return result;
    }

    /**
     * Rounds the last significant digit of a double value.
     * 
     * @param x floating point value
     * @return rounded value
     */
    public static double roundLast(double x) {
        String s = String.format("%e", x);
        String sub = s.substring(s.indexOf('.') + 1, s.indexOf('e'));
        while (sub.startsWith("0") && sub.length() > 1) {
            sub = sub.substring(1);
        }
        while (sub.endsWith("0") && sub.length() > 2) {
            sub = sub.substring(0, sub.length() - 1);
        }
        String sub1 = Math.round(Integer.parseInt(sub) / 10.0) + "";
        s = s.replace(sub, sub1);
        x = Double.parseDouble(s);
        return x;
    }

    /**
     * Parses a string into a double value, returning a default value if parsing fails.
     * 
     * @param text the text to parse
     * @param def the default fallback value
     * @return the parsed double or default value
     */
    public static double valueOrDefault(String text, double def) {
        try {
            return Double.parseDouble(text);
        }
        catch (NumberFormatException e) {
            return def;
        }
    }

    /**
     * Formats a string with arguments and trims trailing zeros for floating point formats.
     * 
     * @param format the format string
     * @param args formatting arguments
     * @return formatted string
     */
    public static String format(String format, Object... args) {
        String s = String.format(format, args);
        if (format.contains("%f")) {
            while (s.endsWith("0")) {
                s = s.substring(0, s.length() - 1);
            }
            if (s.endsWith(".")) {
                s = s.substring(0, s.length() - 1);
            }
        }
        return s;
    }

    /**
     * Converts a double value to a string with 4 significant digits.
     * 
     * @param value the double value
     * @return string representation
     */
    public static String str(double value) {
        return significant(value, 4);
    }

    /**
     * Converts a double value to a string with a specified number of significant digits.
     * 
     * @param value the double value
     * @param significant count of significant digits
     * @return string representation
     */
    public static String str(double value, int significant) {
        return significant(value, significant);
    }

    /**
     * Converts a double value to a string with specified significant digits and decimal places.
     * 
     * @param value the double value
     * @param significant width/significant constraint format
     * @param afterPoint digits after the decimal point
     * @return string representation
     */
    public static String str(double value, int significant, int afterPoint) {
        return format("%" + significant + "." + afterPoint + "f", value);
    }

    /**
     * Returns today's date formatted according to the current language setting.
     * 
     * @return formatted current date string
     */
    public static String today() {
        String locale = switch (MultiString.lang) {
            case EN -> "EN";
            case UA -> "UA";
            case RU -> "RU";
            default -> "";
        };
        return DateFormat.getDateInstance(DateFormat.FULL, new Locale(locale)).format(new Date());
    }

    /**
     * Extracts the file name or last path component from a full path string.
     *
     * @param fullName the full path string[cite: 3]
     * @return the dash and name (file name) component[cite: 3]
     */
    public static String dashAndName(String fullName) {
        int index = fullName.lastIndexOf("\\");
        return fullName.substring(index + 1);
    }

    /**
     * Extracts the directory path portion from a full file path string.
     *
     * @param fullName the full path string[cite: 3]
     * @return the directory path[cite: 3]
     */
    public static String getPath(String fullName) {
        int index = fullName.lastIndexOf("\\");
        return fullName.substring(0, index);
    }

    /**
     * Checks if a string can be parsed into a valid double.
     * 
     * @param num the string to check
     * @return true if valid double, false otherwise
     */
    public static boolean isDouble(String num) {
        if (num == null) {
            return false;
        }
        try {
            Double.parseDouble(num);
            return true;
        }
        catch (NumberFormatException ex) {
            return false;
        }
    }

    /**
     * Converts a time string without date components into total minutes integer.
     * 
     * @param time the time string
     * @return total minutes integer
     */
    public static int TimeToIntWithoutDate(String time)
    {
        if (time != null)
        {
            if (time.isEmpty())
                return 0;
            String[] arr = {};
            if (arr.length < 2)
                return Integer.parseInt(time);
            int hour = Integer.parseInt(arr[0]);
            int min = Integer.parseInt(arr[1]);
            return min + 60 * hour;
        }
        else
            return 0;
    }

    /**
     * Converts a full date-time string into an integer representation.
     * 
     * @param time the date-time string
     * @return integer representation of time/date, or null on failure
     */
    public static Integer timeToInt(String time) {
        if (time != null)
        {
            if (time.isEmpty())
                return 0;
            if (time.length() <= 6)
                return TimeToIntWithoutDate(time);
            String[] arr = {};
            if (arr.length < 5)
                return null;
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
                return null;
            }
        }
        else
            return 0;
    }

    /**
     * Converts an array of time/date strings into an array of integer representations using streams.
     * 
     * @param arr array of string values
     * @return array of converted objects
     */
    public static Object[] stringsToDates(String[] arr) {
        return Arrays.stream(arr).map(value -> timeToInt(value)).toArray();
    }

    /**
     * Pads a string with spaces to a specified length.
     * 
     * @param s the string to pad
     * @param len target length
     * @return padded string
     */
    public static String addSpaces(String s, int len) {
        return String.format("%-" + len + "s", s);
    }

    /**
     * Main execution method for testing utilities.
     * 
     * @param args command-line arguments
     */
    static void main(String[] args) {
        String s = "hhd";
        System.out.println("|" + addSpaces(s, 10) + "|");
    }
}