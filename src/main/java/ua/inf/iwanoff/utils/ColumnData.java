package ua.inf.iwanoff.utils;

public class ColumnData {
    private String title;
    private double width;
    private double multiplier;

    public ColumnData() {
        this("", 0);
    }

    public ColumnData(String title) {
        this(title, 0);
    }

    public ColumnData(String title, double width) {
        this.title = title;
        this.width = width;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }


}
