package ua.inf.iwanoff.attestation;

import ua.inf.iwanoff.attestation.model.AttestationProcessor;
import ua.inf.iwanoff.attestation.model.OptionsData;
import ua.inf.iwanoff.utils.MultiString;

import jakarta.xml.bind.JAXBException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

public class ConsoleTest {
    AttestationProcessor processor = AttestationProcessor.getInstance();

    private String setup() {
        OptionsData optionsData = new OptionsData();
        optionsData.setVariancesEquality(true);
        optionsData.setSamplesHomogeneity(true);
        optionsData.setDrift(true);
        optionsData.setSides(OptionsData.OneTwo.ONE);
        MultiString.lang = MultiString.EN;
        processor.setOptionsData(optionsData);
        String date = new Date().toString();
        date = date.replace(':', '_');
        date = date.replace(' ', '_');
        return date;
    }

    private void test() {
        String date = setup();
        test("../AttestationTests/валидация_инсталяции.xml", "../AttestationTests/A_" + date + ".html");
        test("../AttestationTests/7_выборок.xml", "../AttestationTests/B_" + date + ".html");
        test("../AttestationTests/5_выборок.xml", "../AttestationTests/C_" + date + ".html");
        test("../AttestationTests/Bisoprolol.xml", "../AttestationTests/Bisoprolol_" + date + ".html");
        test("../AttestationTests/20 Пентоксифілін.xml", "../AttestationTests/Pentoxifylline_" + date + ".html");
    }

    private void test(String dirName) {
        String date = setup();
        File dir = new File(dirName);
        String[] list = dir.list();
        for(String name : list) {
            if (name.endsWith("xml")) {
                System.out.println(name);
                test(dirName + "/" + name, dirName + "/" + name + date + ".html");
            }
        }
    }

    private boolean test(String sourceFileName, String reportFileName) {
        try {
            processor.readFromXML(sourceFileName);
            char schema = switch (processor.getSampleCount()) {
                case 4 -> 'A';
                case 5 -> 'C';
                case 7 -> 'B';
                default -> 'X';
            };
            byte[] result = processor.calcABC(schema, null, schema + "");
            new FileOutputStream(reportFileName).write(result, 0, result.length);
        }
        catch (JAXBException | IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    static void main() {
        new ConsoleTest().test("../AttestationTests");
        //new ConsoleTest().test();
    }
}
