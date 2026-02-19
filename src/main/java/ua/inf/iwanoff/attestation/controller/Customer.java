package ua.inf.iwanoff.attestation.controller;

import ua.inf.iwanoff.utils.MultiString;

public class Customer {
    private final MultiString name;
    private final String logotypeFile;
    private String logotypeFileEn;
    private String logotypeFileRu;

    public Customer(MultiString name, String logotypeFile) {
        this.name = name;
        this.logotypeFile = logotypeFile;
    }

    public Customer(MultiString name, String logotypeFile, String logotypeFileEn, String logotypeFileRu) {
        this.name = name;
        this.logotypeFile = logotypeFile;
        this.logotypeFileEn = logotypeFileEn;
        this.logotypeFileRu = logotypeFileRu;
    }

    public MultiString getName() {
        return name;
    }

    public String getLogotypeFile() {
        return logotypeFile;
    }

    public String getLogotypeFileEn() {
        return logotypeFileEn;
    }

    public String getLogotypeFileRu() {
        return logotypeFileRu;
    }
}
