package ua.inf.iwanoff.utils;

import java.util.function.Predicate;

/**
 * Represents data associated with a cell, including its value, validation predicate, and editability state[cite: 2].
 */
public class CellData {
    private String value;
    private Predicate<String> valueTypeCheck;
    private boolean editable = true;

    /**
     * Constructs a new CellData object with the specified value and no type validation restrictions[cite: 2].
     * 
     * @param value the string value of the cell[cite: 2]
     */
    public CellData(String value) {
        this.value = value;
        valueTypeCheck = _ -> true;
    }

    /**
     * Constructs a new CellData object with the specified value and a validation predicate[cite: 2].
     * 
     * @param value the string value of the cell[cite: 2]
     * @param valueTypeCheck the predicate used to validate the cell value[cite: 2]
     */
    public CellData(String value, Predicate<String> valueTypeCheck) {
        this.value = value;
        this.valueTypeCheck = valueTypeCheck;
    }

    /**
     * Gets the string value of the cell[cite: 2].
     * 
     * @return the cell value[cite: 2]
     */
    public String getValue() {
        return value;
    }

    /**
     * Gets the value validation predicate[cite: 2].
     * 
     * @return the validation predicate[cite: 2]
     */
    public Predicate<String> getValueTypeCheck() {
        return valueTypeCheck;
    }

    /**
     * Sets the string value of the cell[cite: 2].
     * 
     * @param value the value to set[cite: 2]
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Sets the value validation predicate[cite: 2].
     * 
     * @param valueTypeCheck the validation predicate to set[cite: 2]
     */
    public void setValueTypeCheck(Predicate<String> valueTypeCheck) {
        this.valueTypeCheck = valueTypeCheck;
    }

    /**
     * Checks whether the cell is editable[cite: 2].
     * 
     * @return true if the cell is editable, false otherwise[cite: 2]
     */
    public boolean isEditable() {
        return editable;
    }

    /**
     * Sets the editability state of the cell[cite: 2].
     * 
     * @param editable true to make the cell editable, false otherwise[cite: 2]
     */
    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    /**
     * Returns the string representation of the cell, which is its value[cite: 2].
     * 
     * @return the cell value as a string[cite: 2]
     */
    @Override
    public String toString() {
        return value;
    }
}