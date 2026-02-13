package ua.inf.iwanoff.attestation.model;

public class DataRow {
    private String name = "";
    private String c1 = "";
    private String c2 = "";
    private String c3 = "";
    private String c4 = "";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getC1() {
        return c1;
    }

    public void setC1(String c1) {
        this.c1 = c1;
    }

    public String getC2() {
        return c2;
    }

    public void setC2(String c2) {
        this.c2 = c2;
    }

    public String getC3() {
        return c3;
    }

    public void setC3(String c3) {
        this.c3 = c3;
    }

    public String getC4() {
        return c4;
    }

    public void setC4(String c4) {
        this.c4 = c4;
    }

    public void setC(int i, String c) {
        switch (i) {
            case 0:
                c1 = c;
                return;
            case 1:
                c2 = c;
                return;
            case 2:
                c3 = c;
                return;
            case 3:
                c4 = c;
                return;
        }
    }

    @Override public String toString() {
        return name + " " + c1 + " " + c2 + " " + c3 + " " + c4 + " ";
    }
}
