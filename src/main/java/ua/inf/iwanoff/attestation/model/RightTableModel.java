package ua.inf.iwanoff.attestation.model;

import ua.inf.iwanoff.utils.CellData;

import javax.swing.table.AbstractTableModel;

import static ua.inf.iwanoff.attestation.controller.Controller.FIXED_ROWS;
import static ua.inf.iwanoff.attestation.view.Strings.msRS;
import static ua.inf.iwanoff.attestation.view.Strings.msWRS;
import static ua.inf.iwanoff.utils.StringUtils.addSpaces;

public class RightTableModel extends AbstractTableModel {
    private AttestationProcessor processor;
    private boolean showTimes = false;
    int maxlen;

    public RightTableModel(AttestationProcessor processor) {
        this.processor = processor;
    }

    public AttestationProcessor getProcessor() {
        return processor;
    }

    public void setProcessor(AttestationProcessor processor) {
        this.processor = processor;
    }

    public boolean isShowTimes() {
        return showTimes;
    }

    public void setShowTimes(boolean showTimes) {
        this.showTimes = showTimes;
    }

    @Override public int getRowCount() {
        return FIXED_ROWS + (showTimes ? processor.getMaxSampleXCount() * 2 : processor.getMaxSampleXCount());
    }

    @Override public int getColumnCount() {
        return processor.getSampleCount() == 0 ? 1 : processor.getSampleCount();
    }

    @Override public Object getValueAt(int rowIndex, int columnIndex) {
        if (processor.getData() == null || columnIndex > processor.getData().size()) {
            return new CellData("");
        }
        if (rowIndex < FIXED_ROWS) {
            switch (rowIndex) {
                case 0:
                    maxlen = processor.getData().maxLength();
                    if (maxlen == 0) {
                        maxlen++;
                    }
                    return new CellData(addSpaces(processor.getData().getName(columnIndex), (int)(maxlen * 1.8)));
                case 1: return new CellData(getFlag(columnIndex));
                case 2: return new CellData(processor.getData().getConcentration(columnIndex));
                case 3: return new CellData(processor.getData().getUncertainty(columnIndex));
            }
        }
        if (showTimes) {
            if (rowIndex % 2 == 0) {
                return new CellData(processor.getData().getX(columnIndex, (rowIndex - FIXED_ROWS) / 2));
            }
            String time = processor.getData().getDate(columnIndex, (rowIndex - FIXED_ROWS) / 2);
            if (time.equals("0")) {
                time = "";
            }
            return new CellData(time);
        }
        return new CellData(processor.getData().getX(columnIndex, rowIndex - FIXED_ROWS));
    }

    @Override public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        String prev = getValueAt(rowIndex, columnIndex).toString();
        String s = aValue.toString();
        if (rowIndex < FIXED_ROWS) {
            switch (rowIndex) {
                case 0: processor.getData().setName(columnIndex, s); return;
                case 1: setFlag(columnIndex, prev, s); return;
                case 2: processor.getData().setConcentration(columnIndex, s); return;
                case 3: processor.getData().setUncertainty(columnIndex, s); return;
            }
        }
        if (showTimes) {
            if (rowIndex % 2 == 0) {
                processor.getData().setX(columnIndex, (rowIndex - FIXED_ROWS) / 2, s);
            }
            else {
                if (s.equals("")) {
                    s = "0";
                }
                processor.getData().setDate(columnIndex, (rowIndex - FIXED_ROWS) / 2 , s);
            }
        }
        else {
            processor.getData().setX(columnIndex, rowIndex - FIXED_ROWS, s);
        }
    }

    private String getFlag(int columnIndex) {
        String result = processor.getData().getFlag(columnIndex);
        return switch (result.toUpperCase()) {
            case "РСО", "РСЗ", "WRS", "WSS" -> msWRS.toString();
            case "СО", "СЗ", "ФСО", "ФСЗ", "RS", "SS", "PRS", "PSS" -> msRS.toString();
            default -> "";
        };
    }

    private void setFlag(int columnIndex, String prev, String s) {
        String result = switch (s.toUpperCase()) {
            case "РСО", "РСЗ", "WRS", "WSS" -> "WRS";
            case "СО", "СЗ", "ФСО", "ФСЗ", "RS", "SS", "PRS", "PSS" -> "PRS";
            default -> prev;
        };
        processor.getData().setFlag(columnIndex, result);
    }
// processor.getData().getFlag(columnIndex)
}
