package ua.inf.iwanoff.utils;

/**
 * Represents metadata and structural properties for a table column[cite: 6].
 */
public class ColumnData {
    private String title;
    private double width;
    private double multiplier;

    /**
     * Constructs a default ColumnData instance with empty title and zero width[cite: 6].
     */
    public ColumnData() {
        this("", 0);
    }

    /**
     * Constructs a ColumnData instance with a specified title and zero width[cite: 6].
     * 
     * @param title the column title[cite: 6]
     */
    public ColumnData(String title) {
        this(title, 0);
    }

    /**
     * Constructs a ColumnData instance with specified title and width[cite: 6].
     * 
     * @param title the column title[cite: 6]
     * @param width the column width[cite: 6]
     */
    public ColumnData(String title, double width) {
        this.title = title;
        this.width = width;
    }

    /**
     * Retrieves the column title[cite: 6].
     * 
     * @return the column title[cite: 6]
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the column title[cite: 6].
     * 
     * @param title the column title to set[cite: 6]
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Retrieves the column width[cite: 6].
     * 
     * @return the column width[cite: 6]
     */
    public double getWidth() {
        return width;
    }

    /**
     * Sets the column width[cite: 6].
     * 
     * @param width the column width to set[cite: 6]
     */
    public void setWidth(double width) {
        this.width = width;
    }
}