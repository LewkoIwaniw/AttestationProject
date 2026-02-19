package ua.inf.iwanoff.attestation.utils;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import static ua.inf.iwanoff.attestation.utils.MultiString.*;

public class StringUtils {

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
    
    public static String significant(double x, int digits) {
        BigDecimal bd = new BigDecimal(x);
        digits += oneAtFirst(x);
        return String.format("%."+ digits +"G", bd);
    }

    public static double value(double x, int digits) {
        double result = Double.parseDouble(significant(x, digits));
        return result;
    }

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

    public static double valueOrDefault(String text, double def) {
        try {
            return Double.parseDouble(text);
        }
        catch (NumberFormatException e) {
            return def;
        }
    }

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

    public static String str(double value) {
        return significant(value, 4);
    }

    public static String str(double value, int significant) {
        return significant(value, significant);
    }

    public static String str(double value, int significant, int afterPoint) {
        return format("%" + significant + "." + afterPoint + "f", value);
    }

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
     * @param fullName
     * @return
     */
    public static String dashAndName(String fullName) {
        int index = fullName.lastIndexOf("\\");
        return " - " + fullName.substring(index + 1);
    }

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

}
