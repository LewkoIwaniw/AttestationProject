package ua.inf.iwanoff.attestation.model;

import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

import static ua.inf.iwanoff.utils.StringUtils.stringsToDates;

/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Sample" maxOccurs="unbounded">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="X" maxOccurs="unbounded">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;attribute name="Value" use="required" type="{http://www.w3.org/2001/XMLSchema}double" />
 *                           &lt;attribute name="Date" use="required" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *                 &lt;attribute name="Name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="Flag" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="Concentration" use="required" type="{http://www.w3.org/2001/XMLSchema}double" />
 *                 &lt;attribute name="Uncertainty" use="required" type="{http://www.w3.org/2001/XMLSchema}double" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="WeightsDilutions" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *       &lt;attribute name="SSName" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="Operator" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="Stuff" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="Amount" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="OfficialStandardSample" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="Document" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="Date" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="Uncertainty" type="{http://www.w3.org/2001/XMLSchema}double" />
 *       &lt;attribute name="XPSS" type="{http://www.w3.org/2001/XMLSchema}double" />
 *       &lt;attribute name="DeltaWRS" type="{http://www.w3.org/2001/XMLSchema}double" />
 *       &lt;attribute name="DeltaUnit" type="{http://www.w3.org/2001/XMLSchema}double" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "sample",
    "weightsDilutions"
})
@XmlRootElement(name = "WrsData")
public class WrsData {

    public enum DataType {
        PRS, WRS
    }

    public static DataType getDataType(String flag) {
        switch (flag) {
            case "ФСО": case "ФСЗ": case "PRS":
                return DataType.PRS;
        }
        return DataType.WRS;
    }

    public static String getDataTypeString(DataType flag) {
        switch (flag) {
            case PRS: return "PRS";
            case WRS: return "RS";
        }
        return null;
    }

    @XmlElement(name = "Sample", required = true)
    private List<WrsData.Sample> sample;
    @XmlElement(name = "WeightsDilutions", required = true)
    private String weightsDilutions = "";
    @XmlAttribute(name = "SSName")
    private String ssName = "";
    @XmlAttribute(name = "Operator")
    private String operator = "";
    @XmlAttribute(name = "Stuff")
    private String stuff = "";
    @XmlAttribute(name = "Amount")
    private String amount = "";
    @XmlAttribute(name = "OfficialStandardSample")
    private String officialStandardSample = "";
    @XmlAttribute(name = "Document")
    private String document = "";
    @XmlAttribute(name = "Date")
    private String date = "";
    @XmlAttribute(name = "Uncertainty")
    private String uncertainty = "0";
    @XmlAttribute(name = "XPSS")
    private String xpss = "100";
    @XmlAttribute(name = "DeltaWRS")
    private String deltaWRS = "0.5";
    @XmlAttribute(name = "DeltaUnit")
    private String deltaUnit = "0.5";

    /**
     * Gets the value of the sample property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the sample property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSample().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed inf the list
     * {@link WrsData.Sample }
     * 
     * 
     */
    public List<WrsData.Sample> getSample() {
        if (sample == null) {
            sample = new ArrayList<WrsData.Sample>();
            sample.add(new Sample());
        }
        return this.sample;
    }

    /**
     * Gets the value of the weightsDilutions property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWeightsDilutions() {
        return weightsDilutions;
    }

    /**
     * Sets the value of the weightsDilutions property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWeightsDilutions(String value) {
        this.weightsDilutions = value;
    }

    /**
     * Gets the value of the ssName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSSName() {
        return ssName;
    }

    /**
     * Sets the value of the ssName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSSName(String value) {
        this.ssName = value;
    }

    /**
     * Gets the value of the operator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOperator() {
        return operator;
    }

    /**
     * Sets the value of the operator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOperator(String value) {
        this.operator = value;
    }

    /**
     * Gets the value of the stuff property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStuff() {
        return stuff;
    }

    /**
     * Sets the value of the stuff property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStuff(String value) {
        this.stuff = value;
    }

    /**
     * Gets the value of the amount property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAmount() {
        return amount;
    }

    /**
     * Sets the value of the amount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAmount(String value) {
        this.amount = value;
    }

    /**
     * Gets the value of the officialStandardSample property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOfficialStandardSample() {
        return officialStandardSample;
    }

    /**
     * Sets the value of the officialStandardSample property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOfficialStandardSample(String value) {
        this.officialStandardSample = value;
    }

    /**
     * Gets the value of the document property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDocument() {
        return document;
    }

    /**
     * Sets the value of the document property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDocument(String value) {
        this.document = value;
    }

    /**
     * Gets the value of the date property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDate() {
        return date;
    }

    /**
     * Sets the value of the date property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDate(String value) {
        this.date = value;
    }

    /**
     * Gets the value of the uncertainty property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUncertainty() {
        return uncertainty;
    }

    /**
     * Sets the value of the uncertainty property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUncertainty(String value) {
        this.uncertainty = value;
    }

    /**
     * Gets the value of the xpss property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getXPSS() {
        return xpss;
    }

    /**
     * Sets the value of the xpss property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setXPSS(String value) {
        this.xpss = value;
    }

    /**
     * Gets the value of the deltaWRS property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeltaWRS() {
        return deltaWRS;
    }

    /**
     * Sets the value of the deltaWRS property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeltaWRS(String value) {
        this.deltaWRS = value;
    }

    /**
     * Gets the value of the deltaUnit property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeltaUnit() {
        return deltaUnit;
    }

    /**
     * Sets the value of the deltaUnit property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeltaUnit(String value) {
        this.deltaUnit = value;
    }

    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="X" maxOccurs="unbounded">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;attribute name="Value" use="required" type="{http://www.w3.org/2001/XMLSchema}double" />
     *                 &lt;attribute name="Date" use="required" type="{http://www.w3.org/2001/XMLSchema}integer" />
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *       &lt;attribute name="Name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="Flag" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="Concentration" use="required" type="{http://www.w3.org/2001/XMLSchema}double" />
     *       &lt;attribute name="Uncertainty" use="required" type="{http://www.w3.org/2001/XMLSchema}double" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "x"
    })
    public static class Sample {

        @XmlElement(name = "X", required = true)
        private List<WrsData.Sample.X> x = new ArrayList<>();
        @XmlAttribute(name = "Name", required = true)
        private String name = "";
        @XmlAttribute(name = "Flag", required = true)
        private String flag = "WRS";
        @XmlAttribute(name = "Concentration", required = true)
        private String concentration = "";
        @XmlAttribute(name = "Uncertainty", required = true)
        private String uncertainty = "";

        /**
         * Gets the value of the getX property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the getX property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getX().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed inf the list
         * {@link WrsData.Sample.X }
         * 
         * 
         */
        public List<WrsData.Sample.X> getX() {
            if (x == null) {
                x = new ArrayList<WrsData.Sample.X>();
            }
            return this.x;
        }

        /**
         * Gets the value of the name property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getName() {
            return name;
        }

        /**
         * Sets the value of the name property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setName(String value) {
            this.name = value;
        }

        /**
         * Gets the value of the flag property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getFlag() {
            return flag;
        }

        /**
         * Sets the value of the flag property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setFlag(String value) {
            this.flag = value;
        }

        /**
         * Gets the value of the concentration property.
         * 
         */
        public String getConcentration() {
            return concentration;
        }

        /**
         * Sets the value of the concentration property.
         * 
         */
        public void setConcentration(String value) {
            this.concentration = value;
        }

        /**
         * Gets the value of the uncertainty property.
         * 
         */
        public String getUncertainty() {
            return uncertainty;
        }

        /**
         * Sets the value of the uncertainty property.
         * 
         */
        public void setUncertainty(String value) {
            this.uncertainty = value;
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;attribute name="Value" use="required" type="{http://www.w3.org/2001/XMLSchema}String" />
         *       &lt;attribute name="Date" use="required" type="{http://www.w3.org/2001/XMLSchema}integer" />
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "")
        public static class X {

            public X() {
                value = "";
                date = "";
            }

            public X(String value, String date) {
                this.value = value;
                this.date = date;
            }

            @XmlAttribute(name = "Value", required = true)
            private String value;
            @XmlAttribute(name = "Date", required = true)
            private String date;

            /**
             * Gets the value of the value property.
             * 
             */
            public String getValue() {
                return value;
            }

            /**
             * Sets the value of the value property.
             * 
             */
            public void setValue(String value) {
                this.value = value;
            }

            /**
             * Gets the value of the date property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDate() {
                return date;
            }

            /**
             * Sets the value of the date property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDate(String value) {
                this.date = value;
            }

        }

    }

    public double[] getSampleXArray(int i) {
        double[] result = new double[sizeSorted(i)];
        for (int j = 0; j < sizeSorted(i); j++) {
            result[j] = xSorted(i, j);
        }
        return result;
    }

    public int getMaxSampleXCount() {
        int result = 0;
        for (WrsData.Sample sample : getSample()) {
            if (sample.getX().size() > result) {
                result = sample.getX().size();
            }
        }
        return result;
    }


    public int size() {
        return getSample().size();
    }

    public int size(int i) {
        return getSample().get(i).getX().size();
    }

    public String getName(int i) {
        return getSample().get(i).getName();
    }

    public String getFlag(int i) {
        return getSample().get(i).getFlag();
    }

    public String getConcentration(int i) {
        return getSample().get(i).getConcentration();
    }

    public String getUncertainty(int i) {
        return getSample().get(i).getUncertainty();
    }

    public String getX(int i, int j) {
        return getSample().get(i).getX().get(j).getValue();
    }

    public String getDate(int i, int j) {
        return getSample().get(i).getX().get(j).getDate();
    }

    public void setName(int i, String name) {
        getSample().get(i).setName(name);
    }

    public void setConcentration(int i, String concentration) {
        getSample().get(i).setConcentration(concentration);
    }

    public void setUncertainty(int i, String uncertainty) {
        getSample().get(i).setUncertainty(uncertainty);
    }

    public void setFlag(int i, String flag) {
        getSample().get(i).setFlag(flag);
    }

    public void setX(int i, int j, String x) {
        getSample().get(i).getX().get(j).setValue(x);
    }

    public void setDate(int i, int j, String date) {
        getSample().get(i).getX().get(j).setDate(date);
    }

    public int sizeSorted(int i) {
        return getSamples().get(i).getX().size();
    }

    public double xSorted(int i, int j) {
        try {
            return Double.parseDouble(getSamples().get(i).getX().get(j).getValue());
        }
        catch (NumberFormatException e) {
            throw new DataException.WrongX();
        }
    }

    public double dateSorted(int i, int j) {
        return (Integer) stringsToDates(getDatesSorted(i))[j];
    }

    public DataType flagSorted(int i) {
        return getFlagSorted(i).equals("PRS") ? DataType.PRS : DataType.WRS;
    }

    public double[] getXSorted(int i) {
        double[] result = new double[sizeSorted(i)];
        for (int j = 0; j < sizeSorted(i); j++) {
            try {
                result[j] = xSorted(i, j);
            }
            catch (NumberFormatException e) {
                return null;
            }
        }
        return result;
    }

    public double[] getTSorted(int i) {
        double[] result = new double[sizeSorted(i)];
        for (int j = 0; j < sizeSorted(i); j++) {
            try {
                result[j] = dateSorted(i, j);
            }
            catch (NumberFormatException e) {
                return null;
            }
        }
        return result;
    }

    public double concentrationSorted(int i) {
        try {
            return Double.parseDouble(getConcentrationSorted(i));
        }
        catch (NumberFormatException e) {
            throw new DataException.WrongConcentration();
        }
    }

    public double uncertaintySorted(int i) {
        try {
            return Double.parseDouble(getUncertaintySorted(i));
        }
        catch (NumberFormatException e) {
            throw new DataException.WrongUncertainty();
        }
    }

    public boolean samplesPresent() {
        return sample != null;
    }

    public String getNameSorted(int i) {
        return getSamples().get(i).getName();
    }

    public String getConcentrationSorted(int i) {
        return getSamples().get(i).getConcentration();
    }

    public String getUncertaintySorted(int i) {
        return getSamples().get(i).getUncertainty();
    }

    public String getFlagSorted(int i) {
        return getSamples().get(i).getFlag();
    }

    public String getXSorted(int i, int j) {
        return getSamples().get(i).getX().get(j).getValue();
    }

    public String getDateSorted(int i, int j) {
        return getSamples().get(i).getX().get(j).getDate();
    }

    public String[] getDatesSorted(int i) {
        String[] result = new String[sizeSorted(i)];
        for (int j = 0; j < result.length; j++) {
            result[j] = getDateSorted(i, j);
        }
        return result;
    }

    public void setNameSorted(int i, String name) {
        getSamples().get(i).setName(name);
    }

    public void setConcentrationSorted(int i, String concentration) {
        getSamples().get(i).setConcentration(concentration);
    }

    public void setUncertaintySorted(int i, String uncertainty) {
        getSamples().get(i).setUncertainty(uncertainty);
    }

    public void setFlagSorted(int i, String flag) {
        getSamples().get(i).setFlag(flag);
    }

    public void setXSorted(int i, int j, String x) {
        getSamples().get(i).getX().get(j).setValue(x);
    }

    public void setDateSorted(int i, int j, String date) {
        getSamples().get(i).getX().get(j).setDate(date);
    }

    public int commonXCount() {
        int result = 0;
        for (int i = 0; i < size(); i++) {
            result += sizeSorted(i);
        }
        return result;
    }

    public List<String> getDatesSorted() {
        List<String> result = new ArrayList<>();
        for (Sample sample : getSamples()) {
            for (Sample.X x : sample.getX()) {
                result.add(x.getDate());
            }
        }
        return result;
    }

    public List<Sample> getSamples() {
        List<Sample> referenceSamples = new ArrayList<>();
        List<Sample> workingSamples = new ArrayList<>();
        for (Sample sample : getSample()) {
            if (sample.getFlag().equals("PRS")) {
                referenceSamples.add(sample);
            }
            else {
                workingSamples.add(sample);
            }
        }
        referenceSamples.addAll(workingSamples);
        return referenceSamples;
    }

    public int maxLength() {
        int maxLen = 0;
        for (int i = 0; i < size(); i++) {
            if (getNameSorted(i).length() > maxLen) {
                maxLen = getNameSorted(i).length();
            }
            if (getFlagSorted(i).length() > maxLen) {
                maxLen = getFlagSorted(i).length();
            }
            if (getConcentrationSorted(i).length() > maxLen) {
                maxLen = getConcentrationSorted(i).length();
            }
            if (getUncertainty(i).length() > maxLen) {
                maxLen = getUncertaintySorted(i).length();
            }
            for (int j = 0; j < sizeSorted(i); j++) {
                if (getXSorted(i, j).length() > maxLen) {
                    maxLen = getXSorted(i, j).length();
                }
                if (getDateSorted(i, j).length() > maxLen) {
                    maxLen = getDateSorted(i, j).length();
                }
            }
        }
        return maxLen;
    }

    public void addX(int index) {
        for (int i = 0; i < size(); i++) {
            sample.get(i).getX().add(index, new Sample.X());
        }
    }

    public void addSample(int index) {
        Sample s = new Sample();
        for (int i = 0; i < size(0); i++) {
            s.getX().add(new Sample.X());
        }
        sample.add(index, s);
    }

    public void removeSample(int index) {
        sample.remove(index);
    }


    public int commonXLength(int index) {
        int result = 0;
        for (int i = 0; i < size(); i++) {
            result += getX(i, index).length();
            result += getDate(i, index).length();
        }
        return result;
    }

    public void removeX(int index) {
        for (int i = 0; i < size(); i++) {
            sample.get(i).getX().remove(index);
        }
    }
}
