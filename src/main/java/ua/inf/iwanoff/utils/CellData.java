package ua.inf.iwanoff.utils;

import java.util.function.Predicate;

public class CellData {
    private String value;
    private Predicate<String> valueTypeCheck;
    private boolean editable = true;

    public CellData(String value) {
        this.value = value;
        valueTypeCheck = x -> true;
    }

    public CellData(String value, Predicate<String> valueTypeCheck) {
        this.value = value;
        this.valueTypeCheck = valueTypeCheck;
    }

    public String getValue() {
        return value;
    }

    public Predicate<String> getValueTypeCheck() {
        return valueTypeCheck;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setValueTypeCheck(Predicate<String> valueTypeCheck) {
        this.valueTypeCheck = valueTypeCheck;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    @Override
    public String toString() {
        return value;
    }
}
