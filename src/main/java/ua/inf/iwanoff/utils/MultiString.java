package ua.inf.iwanoff.utils;

/**
 * Handles multi-language strings supporting English, Ukrainian, and Russian[cite: 4].
 */
public class MultiString {
    /** Language constant for English. */
    public static final int EN = 0;
    /** Language constant for Ukrainian. */
    public static final int UA = 1;
    /** Language constant for Russian. */
    public static final int RU = 2;
    
    /** The current active language code[cite: 4]. */
    public static int lang = EN;

    /**
     * Translates and returns the string corresponding to the current language index[cite: 4].
     * 
     * @param words array of strings for different languages[cite: 4]
     * @return the string corresponding to the active language[cite: 4]
     */
    public static String translate(String ... words) {
        return words[lang];
    }

    private final String[] words;

    /**
     * Constructs a MultiString instance with localized versions of a string[cite: 4].
     * 
     * @param words array of localized text values[cite: 4]
     */
    public MultiString(String ... words) {
        this.words = words;
    }

    /**
     * Returns the string representation based on the current language setting[cite: 4].
     * 
     * @return the translated string[cite: 4]
     */
    @Override
    public String toString() {
        return translate(words);
    }

    /**
     * Retrieves the string for a specific language index[cite: 4].
     * 
     * @param language the language index (e.g., EN, UA, RU)[cite: 4]
     * @return the localized string[cite: 4]
     */
    public String get(int language) {
        return words[language];
    }
}