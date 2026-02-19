package ua.inf.iwanoff.utils;

public class MultiString {
    public static final int EN = 0, UA = 1, RU = 2;
    public static int lang = EN;

    public static String translate(String ... words) {
        return words[lang];
    }

    private final String[] words;

    public MultiString(String ... words) {
        this.words = words;
    }

    @Override
    public String toString() {
        return translate(words);
    }

    public String get(int language) {
        return words[language];
    }
}
