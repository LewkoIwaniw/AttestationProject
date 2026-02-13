package ua.inf.iwanoff.attestation.model;

public class OptionsData {

    public enum OneTwo { ZERO, ONE, TWO };

    private boolean variancesEquality;
    private boolean samplesHomogeneity;
    private boolean drift;
    private OneTwo sides;

    public OptionsData() {
        variancesEquality = samplesHomogeneity = drift = true;
        sides = OneTwo.ONE;
    }

    public OptionsData(boolean variancesEquality, boolean samplesHomogeneity, boolean drift, OneTwo sides) {
        this.variancesEquality = variancesEquality;
        this.samplesHomogeneity = samplesHomogeneity;
        this.drift = drift;
        this.sides = sides;
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
}
