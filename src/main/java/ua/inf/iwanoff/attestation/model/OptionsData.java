package ua.inf.iwanoff.attestation.model;

public class OptionsData {

    public enum OneTwo { ZERO, ONE, TWO }
    public enum Units { MG_ML, PERCENTS }

    private boolean variancesEquality;
    private boolean samplesHomogeneity;
    private boolean drift;
    private OneTwo sides;
    private Units units;

    public OptionsData() {
        variancesEquality = samplesHomogeneity = drift = true;
        sides = OneTwo.ONE;
    }

    public OptionsData(boolean variancesEquality, boolean samplesHomogeneity, boolean drift, OneTwo sides, Units units) {
        this.variancesEquality = variancesEquality;
        this.samplesHomogeneity = samplesHomogeneity;
        this.drift = drift;
        this.sides = sides;
        this.units = units;
    }

    public boolean isVariancesEquality() {
        return variancesEquality;
    }

    public void setVariancesEquality(boolean variancesEquality) {
        this.variancesEquality = variancesEquality;
    }

    public boolean isSamplesHomogeneity() {
        return samplesHomogeneity;
    }

    public void setSamplesHomogeneity(boolean samplesHomogeneity) {
        this.samplesHomogeneity = samplesHomogeneity;
    }

    public boolean isDrift() {
        return drift;
    }

    public void setDrift(boolean drift) {
        this.drift = drift;
    }

    public OneTwo getSides() {
        return sides;
    }

    public void setSides(OneTwo sides) {
        this.sides = sides;
    }

    public Units getUnits() {
        return units;
    }

    public void setUnits(Units units) {
        this.units = units;
    }
}
