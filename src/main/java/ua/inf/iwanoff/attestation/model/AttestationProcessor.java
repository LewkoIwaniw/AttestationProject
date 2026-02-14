package ua.inf.iwanoff.attestation.model;

import ua.inf.iwanoff.attestation.controller.Customer;
import ua.inf.iwanoff.attestation.model.WrsData.DataType;
import ua.inf.iwanoff.utils.FileUtils;
import ua.inf.iwanoff.utils.GraphUtils;
import ua.inf.iwanoff.utils.MultiString;
import ua.inf.iwanoff.utils.Report;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import java.io.*;

import static ua.inf.iwanoff.attestation.model.AbstractCalculations.FLAG_ASCERTAINMENT;
import static ua.inf.iwanoff.attestation.model.AbstractCalculations.FLAG_HOMOGENEITY;
import static ua.inf.iwanoff.attestation.model.WrsData.getDataType;
import static ua.inf.iwanoff.attestation.model.WrsData.getDataTypeString;

/**
 *
 * Class implements Singleton pattern.
 */
public class AttestationProcessor {

    public static final byte STATE_LOADED = 1;
    public static final byte STATE_CALCULATED = 2;
    public static final byte STATE_SAVED = 4;
    public static final byte STATE_REPORT_SAVED = 8;

    private static AttestationProcessor instance;
    private WrsData data;
    private Runnable reactFunc = null;
    private byte state = STATE_SAVED | STATE_REPORT_SAVED;
    private OptionsData optionsData = new OptionsData();
    private char lastSchema = ' ';

    public OptionsData getOptionsData() {
        return optionsData;
    }

    public void setOptionsData(OptionsData optionsData) {
        this.optionsData = optionsData;
    }

    public boolean isLoaded() {
        return (state & STATE_LOADED) != 0;
    }

    public boolean isCalculated() {
        return (state & STATE_CALCULATED) != 0;
    }

    public boolean isDataSaved() {
        return (state & STATE_SAVED) != 0;
    }

    public boolean isReportSaved() {
        return (state & STATE_REPORT_SAVED) != 0;
    }

    private DocCalculations calculations;

    public void setReactFunc(Runnable reactFunc) {
        this.reactFunc = reactFunc;
    }

    public boolean attestationWasDone() {
        return lastSchema != ' ';
    }

    private AttestationProcessor() {
    }

    public WrsData getData() {
        if (data == null) {
            data = new WrsData();
        }
        return data;
    }

    public void addState(byte flag) {
        state |= flag;
        if (reactFunc != null) {
            reactFunc.run();
        }
    }

    public void removeState(byte flag) {
        state &= ~flag;
        if (reactFunc != null) {
            reactFunc.run();
        }
    }

    private void resetState() {
        state = STATE_SAVED | STATE_REPORT_SAVED;
        if (reactFunc != null) {
            reactFunc.run();
        }
    }

    public byte getState() {
        return state;
    }

    public static AttestationProcessor getInstance() {
        if (instance == null) {
            instance = new AttestationProcessor();
        }
        return instance;
    }

    public void readFromXML(String fileName) throws FileNotFoundException, JAXBException {
        doNew();
        JAXBContext jaxbContext = JAXBContext.newInstance("ua.inf.iwanoff.attestation.model");
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        data = (WrsData) unmarshaller.unmarshal(new FileInputStream(fileName));
        addState(STATE_LOADED);
    }

    public void doNew() {
        data = null;
        resetState();
    }

    public void importFromTxt(String fileName) throws IOException {
        WrsData newData = new WrsData();
        newData.getSample().clear();
        try (FileInputStream fis = new FileInputStream(fileName);
             InputStreamReader isr = new InputStreamReader(fis, "CP1251");
             BufferedReader sr = new BufferedReader(isr)) {
            String line;
            while ((line = sr.readLine()) != null) {
                String[] arr = line.split("\\t");
                String name = arr[0];
                String c = arr[2];
                String uncertainty = arr[3];
                DataType flag = getDataType(arr[5].substring(0, 3));
                boolean added = false;
                for (WrsData.Sample sample : newData.getSample()) {
                    if (name.equals(sample.getName()) && c.equals(sample.getConcentration()) &&
                            uncertainty.equals(sample.getUncertainty()) && flag == getDataType(sample.getFlag())) {
                        sample.getX().add(new WrsData.Sample.X(arr[1], arr[4]));
                        added = true;
                    }
                }
                if (!added) {
                    WrsData.Sample newSample = new WrsData.Sample();
                    newSample.setName(name);
                    newSample.setConcentration(c);
                    newSample.setUncertainty(uncertainty);
                    newSample.getX().clear();
                    newSample.setFlag(getDataTypeString(flag));
                    newSample.getX().add(new WrsData.Sample.X(arr[1], arr[4]));
                    newData.getSample().add(newSample);
                }
            }
            data = newData;
            removeState(STATE_SAVED);
        }
        catch (ArrayIndexOutOfBoundsException ex) {
            throw new DataException.WrongFileFormat();
        }
    }

    public int getSampleCount() {
        if (data == null || !data.samplesPresent()) {
            return 0;
        }
        return data.size();
    }

    public boolean setXPSS(String xpss) {
        if (xpss.length() == 0) {
            return true;
        }
        try {
            Double.parseDouble(xpss);
            getData().setXPSS(xpss);
            return true;
        }
        catch (NumberFormatException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean setDeltaWRS(String deltaWRS) {
        if (deltaWRS.length() == 0) {
            return true;
        }
        try {
            Double.parseDouble(deltaWRS);
            getData().setDeltaWRS(deltaWRS);
            return true;
        }
        catch (NumberFormatException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getLogoFileName() {
        String logoFileName = null;
        byte[] buffer = FileUtils.getBytes("/" + customer.getLogotypeFile());
        File tempFile = null;
        try {
            tempFile = File.createTempFile(customer.getLogotypeFile(), "");
            tempFile.deleteOnExit();
            logoFileName = tempFile.getAbsolutePath();
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        try (OutputStream output = new FileOutputStream(tempFile)) {
            output.write(buffer, 0, buffer.length);
        }
        catch (IOException | NullPointerException e) {
            e.printStackTrace();
            return null;
        }
        return logoFileName;
    }

    public byte[] calcABC(char schema, String fileName, String protocol) {
        lastSchema = schema;
        calculations = new DocCalculations(getData(), customer.getName());
        calculations.setImage(getLogoFileName());
        if (fileName == null) {
            calculations.setDocType(Report.DocType.HTML);
        }
        else {
            calculations.setDocType(Report.DocType.PDF);
        }
        calculations.setProtocol(protocol);
        int flag;
        switch (schema) {
            case 'A': flag = FLAG_ASCERTAINMENT; break;
            case 'B': flag = FLAG_ASCERTAINMENT | FLAG_HOMOGENEITY; break;
            case 'C': flag = FLAG_HOMOGENEITY; break;
            default : return null;
        }
        byte[] result = calculations.calc(flag, optionsData);
        addState(STATE_CALCULATED);
        removeState(STATE_REPORT_SAVED);
        return result;
    }

    public int getMaxSampleXCount() {
        return data == null ? 0 : data.getMaxSampleXCount();
    }

    public void saveToXML(String fileName) throws FileNotFoundException, JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance("ua.inf.iwanoff.attestation.model");
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.marshal(getData(), new FileOutputStream(fileName));
        addState(STATE_SAVED);
    }

    public void removeDataRow(int index) {
        getData().removeX(index);
    }

    public void addDataRow(int index) {
        getData().addX(index);
    }

    public void addSample(int index) {
        getData().addSample(index);
    }

    public void removeSample(int index) {
        getData().removeSample(index);
    }

    public boolean saveReport(String fileName, String protocol) {
        byte[] buffer = calcABC(lastSchema, fileName, protocol);
        try (FileOutputStream fileOutputStream = new FileOutputStream(fileName)) {
            fileOutputStream.write(buffer, 0, buffer.length);
        }
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        addState(STATE_REPORT_SAVED);
        return true;
    }

    public Customer getCustomer() {
        return customer;
    }

//    private Customer customer = new Customer(
//            new MultiString("PJSC SIC \"Borshchahivskiy CPP\"",
//                    "ПАТ НВЦ \"Борщагівський хіміко-фармацевтичний завод\"",
//                    "ПАО НПЦ \"Борщаговский химико-фармацевтический завод\""),
//            "borscanovsky_chphz.png");

//    private Customer customer = new Customer(
//            new MultiString("AT \"Astrapharm\" Ltd.",
//                "ТОВ \"Астрафарм\"",
//                "ООО \"Астрафарм\""),
//            "Astrapharm.png");
//
//    private Customer customer = new Customer(
//            new MultiString("S.C. Balkan Pharmaceuticals S.R.L.",
//                    "S.C. Balkan Pharmaceuticals S.R.L.",
//                    "S.C. Balkan Pharmaceuticals S.R.L."),
//            "balkan.jpg");
//
//    private Customer customer = new Customer(
//            new MultiString("Joint-stock company \"Galychpharm\"",
//                    "АТ \"Галичфарм\"",
//                    "АО \"Галичфарм\""),
//            "Arterium.png");
//    private Customer customer = new Customer(
//            new MultiString("Kharkov Pharmaceutical Enterprise \"Zdorovya Narodu\" Ltd.",
//                    "ТОВ Харківське фармацевтичне підприємство \"Здоров'я народу\"",
//                    "ООО Харьковское фармацевтическое предприятие \"Здоровье народу\""),
//            "ZdorovyaNarodu.png");
    private Customer customer = new Customer(
            new MultiString("Corporation \"Zdorovya\" Ltd.",
                    "Товариство з обмеженою відповідальністю Корпорація\"Здоров'я\"",
                    "ООО Харьковское фармацевтическое предприятие \"Здоровье народу\""),
            "ZdorovyaNarodu.png");
//    private Customer customer = new Customer(
//            new MultiString("\"Biopharma LLC\"",
//                    "ТОВ ФЗ\"БІОФАРМА\"",
//                    "ООО ФЗ\"БИОФАРМА\""),
//            "biopharma.jpg");
//    private Customer customer = new Customer(
//            new MultiString("PJSC \"Chervona zirka\" Chemical & Pharmaceutical Plant",
//                    "Публічне акціонерне товариство «Хімфармзавод «Червона зірка»",
//                    "Публичное акционерное общество «Химфармзавод «Червона зірка»"),
//            "zirka.png");
//    private Customer customer = new Customer(
//            new MultiString("JSC \"Lubnypharm\"",
//                    "АТ \"Лубнифарм\"",
//                    "АО \"Лубнифарм\""),
//            "lubny.png");
//    private Customer customer = new Customer(
//            new MultiString("PJSC \"Monfarm\"",
//                    "ПАТ \"Монфарм\"",
//                    "ОАО \"Монфарм\""),
//            "Monfarm.jpg");
//    private Customer customer = new Customer(
//            new MultiString("Novofarm-Biosynthes Ltd.",
//                    "ТОВ фірма «Новофарм-Біосинтез»\n",
//                    "ООО фирма «Новофарм-Биосинтез»\n"),
//            "Novofarm_en.png", "Novofarm_ua.png", "Novofarm_ru.png");
//    private Customer customer = new Customer(
//            new MultiString("PJSC \"Lekhim-Kharkiv\"",
//                    "ПрАТ «Лекхім-Харків»\n",
//                    "ЧАО «Лекхим-Харьков»\n"),
//            "Lekchim.jpg");
    public static final String VERSION = "2.2";

}