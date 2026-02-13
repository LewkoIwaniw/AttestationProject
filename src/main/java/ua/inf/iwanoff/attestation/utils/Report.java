package ua.inf.iwanoff.attestation.utils;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.html.HtmlWriter;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import com.lowagie.text.pdf.PdfWriter;

import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

//import static ua.in.iwanoff.validation.view.Strings.msPageOf;

public class Report {
    private String pageOf;
    private DocType docType = DocType.HTML;

    public enum DocType {HTML, PDF}

    private static final String times = new File(System.getenv("WINDIR"), "Fonts").getAbsolutePath() + "\\times.ttf";

    public static final Font FONT_NORMAL_10 = FontFactory.getFont(times, "Cp1251", false, 10, Font.NORMAL);
    public static final Font FONT_NORMAL_12 = FontFactory.getFont(times, "Cp1251", false, 12, Font.NORMAL);
    public static final Font FONT_NORMAL_14 = FontFactory.getFont(times, "Cp1251", false, 14, Font.NORMAL);
    public static final Font FONT_BOLD_10 = FontFactory.getFont(times, "Cp1251", false, 10, Font.BOLD);
    public static final Font FONT_BOLD_12 = FontFactory.getFont(times, "Cp1251", false, 12, Font.BOLD);
    public static final Font FONT_BOLD_14 = FontFactory.getFont(times, "Cp1251", false, 14, Font.BOLD);

    public static final String SPACE = "\240";

    public String getPageOf() {
        return pageOf;
    }

    public void setPageOf(String pageOf) {
        this.pageOf = pageOf;
    }

    public static String spaces(int n) {
        StringBuilder sb = new StringBuilder("");
        for (int i = 0; i < n; i++) {
            sb.append(SPACE);
        }
        return sb.toString();
    }

    public static Chunk sub(Object text) {
        return new Chunk(text.toString()).setTextRise(-5f);
    }

    public static Chunk greek(Object text) {
        return new Chunk(text.toString(), FontFactory.getFont(new File(System.getenv("WINDIR"),
                "Fonts").getAbsolutePath() + "\\times.ttf", "Cp1253", false, 12, Font.NORMAL));
    }

    public static Chunk sup(Object text) {
        return new Chunk(text.toString()).setTextRise(5f);
    }

    public static Chunk image(String imageFile) {
        try {
            Image img = Image.getInstance(imageFile);
            img.scalePercent(50, 50);
            return new Chunk(img, 0, -3);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Phrase text(Phrase phrase, Object... parts) {
        for (Object part : parts) {
            switch (part.getClass().getName()) {
                case "com.lowagie.text.Chunk":
                    phrase.add(part);
                    break;
                default:
                    phrase.add(new Chunk(part + ""));
                    break;
            }
        }
        return phrase;
    }

    public Phrase phrase(Object... parts) {
        Phrase phrase = new Phrase("", font);
        return text(phrase, parts);
    }

    public static Phrase subScriptPhrase(String text, String index, Font font) {
        Phrase phrase = new Phrase(text, font);
        phrase.add(new Chunk(index).setTextRise(-5f));
        return phrase;
    }

    public Phrase withSub(String text, String index) {
        return subScriptPhrase(text, index, font);
    }

    public Phrase withSubGreek(String text, String index) {
        Phrase phrase = new Phrase(text, FontFactory.getFont(new File(System.getenv("WINDIR"),
                "Fonts").getAbsolutePath() + "\\times.ttf", "Cp1253", false, 12, Font.NORMAL));
        phrase.add(new Chunk(index, font).setTextRise(-5f));
        return phrase;
    }

    private int padding = 4;
    private Font font = FONT_NORMAL_10;
    private Font fontBold = FONT_BOLD_10;
    private Document document;
    private ByteArrayOutputStream out = new ByteArrayOutputStream();

//    public Report() {
//    }

    /**
     * This is deprecated constructor used by DocumentProcessor class only
     * @param document previously created document
     */
    public Report(Document document) {
        if (document == null) {
            throw new NullPointerException();
        }
        this.document = document;
    }

    /**
     * The main constructor that sets one of possible types (html / pdf)
     * @param docType
     */
    public Report(DocType docType) {
        document = new Document();
        switch (this.docType = docType) {
            case HTML:
                HtmlWriter.getInstance(document, out);
                break;
            case PDF:
                try {
                    PdfWriter.getInstance(document, out);
                }
                catch (DocumentException e) {
                    throw new RuntimeException("Document Exception");
                }
                break;
            default:
        }
        document.open();
    }

    public byte[] getResult() {
        document.close();
        return withPageNumbers();
    }

    private byte[] withPageNumbers() {
        byte[] bytes = this.out.toByteArray();
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        try {
            PdfReader reader = new PdfReader(in);
            int n = reader.getNumberOfPages();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            PdfStamper stamp = new PdfStamper(reader, out);
            PdfContentByte over;
            for (int i = 1; i <= n; i++) {
                over = stamp.getOverContent(i);
                over.beginText();
                over.setFontAndSize(font.getBaseFont(), 10);
                over.setTextMatrix(20, 20);
                over.showText(String.format(pageOf, i, n));
                over.endText();
            }
            stamp.close();
            return out.toByteArray();
        }
        catch (IOException | DocumentException e) {
            return this.out.toByteArray();
        }
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public int getPadding() {
        return padding;
    }

    public void setPadding(int padding) {
        this.padding = padding;
    }

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public Font getFontBold() {
        return fontBold;
    }

    public void setFontBold(Font fontBold) {
        this.fontBold = fontBold;
    }

    private Table prepareTable(String tableName, Phrase[] columnName, int width, int columnCount,
                               int padding, java.awt.Color borderColor, int... widths) throws DocumentException {
        this.padding = padding;
        Cell cell;
        if (tableName != null && tableName != "") {
            document.add(new Paragraph(tableName + ":", fontBold));
            document.add(new Paragraph("\n"));
        }
        Table table = new Table(columnCount);
        if (width > 0) {
            table.setWidth(width);
        }
        table.setPadding(this.padding = padding);
        if (borderColor != null) {
            table.setBorderColor(borderColor);
        }
        if (widths != null && widths.length > 0) {
            table.setWidths(widths);
        }
        table.setAlignment(Element.ALIGN_LEFT);
        /*Add Header*/
        if (columnName != null) {
            for (int i = 0; i < columnName.length; i++) {
                cell = new Cell(columnName[i]);
                table.addCell(cell);

            }
        }
        return table;
    }

    public void createTable(String tableName, Phrase[] columnName, Phrase[] rowName, int width,
                            Phrase[][] tableData, int padding, java.awt.Color borderColor, int... widths)
            throws DocumentException {
        Table table = prepareTable(tableName, columnName, width, tableData[0].length, padding, borderColor, widths);
        Cell cell;
        if (tableData != null) {
            for (int i = 0; i < tableData.length; i++) {

                if (rowName != null && tableData.length == rowName.length) {
                    cell = new Cell(rowName[i]);
                    table.addCell(cell);
                }

                for (int j = 0; j < tableData[i].length; j++) {
                    if (tableData[i][j] != null)
                        cell = new Cell(tableData[i][j]);
                    else
                        cell = new Cell(new Phrase(""));
                    table.addCell(cell);

                }
            }
        }
        document.add(table);
    }

    public void createTable(Object[][] tableData) {
        try {
            createTable(tableData, 100, 2, Color.DARK_GRAY);
        }
        catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    public void createTable(Object[][] tableData, int width, int padding,
                            java.awt.Color borderColor, int... widths) throws DocumentException {
        int columnCount = 0;
        for (int i = 0; i < tableData.length; i++) {
            if (columnCount < tableData[i].length) {
                columnCount = tableData[i].length;
            }
        }
        this.padding = padding;
        Cell cell;
        Table table = new Table(columnCount);
        if (width > 0) {
            table.setWidth(width);
        }
        table.setPadding(this.padding = padding);
        if (borderColor != null) {
            table.setBorderColor(borderColor);
        }
        if (widths != null && widths.length > 0) {
            table.setWidths(widths);
        }
        table.setAlignment(Element.ALIGN_LEFT);
        for (int i = 0; i < tableData.length; i++) {
            for (int j = 0; j < columnCount; j++) {
                if (j < tableData[i].length) {
                    String className = tableData[i][j].getClass().getName();
                    switch (className) {
                        case "com.lowagie.text.Phrase":
                            cell = new Cell((Phrase) tableData[i][j]);
                            break;
                        case "com.lowagie.text.Cell":
                            cell = (Cell) tableData[i][j];
                            break;
                        default:
                            cell = new Cell(new Phrase(tableData[i][j] + "", font));
                    }
                } else {
                    cell = new Cell("");
                }
                table.addCell(cell);
            }
        }
        document.add(table);
    }

    public Object[] header(Object... cells) {
        Object[] result = new Object[cells.length];
        for (int i = 0; i < cells.length; i++) {
            Cell cell;
            String className = cells[i].getClass().getName();
            try {
                switch (className) {
                    case "com.lowagie.text.Phrase":
                        cell = new Cell((Phrase) cells[i]);
                        break;
                    case "com.lowagie.text.Cell":
                        cell = (Cell) cells[i];
                        break;
                    default:
                        cell = new Cell(new Phrase(cells[i] + "", fontBold));
                }
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                result[i] = cell;
            }
            catch (DocumentException e) {
                result[i] = cells[i];
            }

        }
        return result;
    }

    public void createTable(String tableName, Phrase[] columnName, Phrase[] rowName, int width, Cell[][] tableData,
                            int padding, java.awt.Color borderColor, int... widths)
            throws DocumentException {
        Table table = prepareTable(tableName, columnName, width, tableData[0].length, padding, borderColor, widths);
        Cell cell;
        if (tableData != null) {
            for (int i = 0; i < tableData.length; i++) {

                if (rowName != null && tableData.length == rowName.length) {
                    cell = new Cell(rowName[i]);
                    table.addCell(cell);
                }

                for (int j = 0; j < tableData[i].length; j++) {
                    if (tableData[i][j] != null)
                        cell = tableData[i][j];
                    else
                        cell = new Cell(new Phrase(""));
                    table.addCell(cell);

                }
            }
        }
        document.add(table);
    }

    public void createTable(String tableName, Phrase[] columnName, Phrase[] rowName, int width, String[][] tableData,
                            int padding, java.awt.Color borderColor, int... widths)
            throws DocumentException {
        Table table = prepareTable(tableName, columnName, width, tableData[0].length, padding, borderColor, widths);
        Cell cell;
        if (tableData != null) {
            for (int i = 0; i < tableData.length; i++) {

                if (rowName != null && tableData.length == rowName.length) {
                    cell = new Cell(rowName[i]);
                    table.addCell(cell);
                }

                for (int j = 0; j < tableData[i].length; j++) {
                    cell = tableData[i][j] != null ?
                            new Cell(new Phrase(tableData[i][j] + "", font)) :
                            new Cell(new Phrase(""));
                    table.addCell(cell);

                }
            }
        }
        document.add(table);
        table.setWidths(widths);
    }

    public void createTable(String tableName, Phrase[] columnName,
                            Phrase[] rowName, int width, String[][] tableData, int... widths)
            throws DocumentException {
        Cell cell;
        if (tableName != "") {
            document.add(new Paragraph(tableName + ":", fontBold));
            document.add(new Paragraph("\n"));
        }
        Table table = new Table(columnName.length);
        table.setPadding(padding);
        if (width > 0) {
            table.setWidth(width);
        }
        if (widths != null && widths.length > 0) {
            table.setWidths(widths);
        }
        table.setAlignment(Element.ALIGN_CENTER);
        /*Add Header*/
        if (columnName != null) {
            for (int i = 0; i < columnName.length; i++) {
                cell = new Cell(columnName[i]);
                table.addCell(cell);

            }
        }

        if (tableData != null) {
            for (int i = 0; i < tableData.length; i++) {
                if (rowName != null && tableData[i].length == rowName.length) {
                    cell = new Cell(rowName[i]);
                    table.addCell(cell);
                }

                for (int j = 0; j < tableData[i].length; j++) {
                    cell = tableData[i][j] != null ?
                            new Cell(new Phrase(tableData[i][j] + "", font)) :
                            new Cell(new Phrase(""));
                    table.addCell(cell);
                }
            }
        }
        document.add(table);
    }

    public boolean text(String text) {
        return text(text, font);
    }

    public boolean text(String text, Font font) {
        try {
            document.add(new Paragraph(text, font));
            return true;
        }
        catch (DocumentException e) {
            return false;
        }
    }

    public boolean text(MultiString text) {
        return text(text.toString());
    }

    public boolean text(MultiString text, Font font) {
        return text(text.toString(), font);
    }

    public boolean newLine() {
        return text("\240");
    }

    public static void main(String[] args) {
        Object[] arr = {"1", 2, 3.0, new Cell(), new Phrase()};
        for (Object o : arr) {
            System.out.println(o.getClass().getName());
        }
    }
}