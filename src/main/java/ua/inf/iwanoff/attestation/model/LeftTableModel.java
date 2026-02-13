package ua.inf.iwanoff.attestation.model;

import javax.swing.table.AbstractTableModel;

import static ua.inf.iwanoff.attestation.Controller.FIXED_ROWS;
import static ua.inf.iwanoff.attestation.view.Strings.*;

public class LeftTableModel extends AbstractTableModel {
    private AttestationProcessor processor;
    private boolean showTimes = false;

    public LeftTableModel(AttestationProcessor processor) {
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
        return FIXED_ROWS + (isShowTimes() ? processor.getMaxSampleXCount() * 2 : processor.getMaxSampleXCount());
    }

    @Override public int getColumnCount() {
        return 1;
    }

    @Override public Object getValueAt(int rowIndex, int columnIndex) {
        if (columnIndex > 0) {
            return null;
        }
        if (rowIndex < FIXED_ROWS) {
            switch (rowIndex) {
                case 0: return msSampleTitle.toString();
                case 1: return msFlag.toString();
                case 2: return msConcentration.toString();
                case 3: return msUncertainty.toString();
            }
        }
        if (isShowTimes()) {
            if (rowIndex % 2 == 0) {
                return "x" + ((rowIndex - FIXED_ROWS) / 2 + 1);
            }
            return msTime.toString() + ((rowIndex - FIXED_ROWS) / 2 + 1);
        }
        return "x" + (rowIndex - FIXED_ROWS + 1);
    }

}
