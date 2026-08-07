package ua.inf.iwanoff.utils;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.alignment.HorizontalAlignment;
import com.lowagie.text.html.HtmlWriter;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import com.lowagie.text.pdf.PdfWriter;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
 * Utility class for generating reports in HTML or PDF formats using OpenPDF[cite: 1].
 */
public class Report {
    private String pageOf;

    /**
     * Gets the document type.
     * @return the document type
     */
    public DocType getDocType() {
        return docType;
    }

    /**
     * Sets the document type.
     * @param docType the document type to set
     */
    public void setDocType(DocType docType) {
        this.docType = docType;
    }

    private DocType docType = DocType.HTML;

    /**
     * Supported document output types.
     */
    public enum DocType {HTML, PDF}

    private static final String times = new File(System.getenv("WINDIR"), "Fonts").getAbsolutePath() + "\\times.ttf";

    public static final Font FONT_NORMAL_10 = FontFactory.getFont(times, "Cp1251", false, 10, Font.NORMAL);
    public static final Font FONT_NORMAL_12 = FontFactory.getFont(times, "Cp1251", false, 12, Font.NORMAL);
    public static final Font FONT_NORMAL_14 = FontFactory.getFont(times, "Cp1251", false, 14, Font.NORMAL);
    public static final Font FONT_BOLD_10 = FontFactory.getFont(times, "Cp1251", false, 10, Font.BOLD);
    public static final Font FONT_BOLD_12 = FontFactory.getFont(times, "Cp1251", false, 12, Font.BOLD);
    public static final Font FONT_BOLD_14 = FontFactory.getFont(times, "Cp1251", false, 14, Font.BOLD);

    public static final String SPACE = "\240";

    /**
     * Gets the page numbering format string.
     * @return the page numbering format string
     */
    public String getPageOf() {
        return pageOf;
    }

    /**
     * Sets the page numbering format string.
     * @param pageOf the page format string to set
     */
    public void setPageOf(String pageOf) {
        this.pageOf = pageOf;
    }

    /**
     * Generates a string consisting of a specified number of non-breaking spaces.
     * @param n the number of spaces
     * @return the string of spaces
     */
    public static String spaces(int n) {
        return SPACE.repeat(Math.max(0, n));
    }

    /**
     * Creates a subscript chunk with text rise adjustment.
     * @param text the text object
     * @return the subscript Chunk
     */
    public static Chunk sub(Object text) {
        return new Chunk(text.toString()).setTextRise(-5f);
    }

    /**
     * Creates a chunk using the Greek font encoding (Cp1253).
     * @param text the text object
     * @return the Greek Chunk
     */
    public static Chunk greek(Object text) {
        return new Chunk(text.toString(), FontFactory.getFont(new File(System.getenv("WINDIR"),
                "Fonts").getAbsolutePath() + "\\times.ttf", "Cp1253", false, 12, Font.NORMAL));
    }

    /**
     * Creates a superscript chunk with text rise adjustment.
     * @param text the text object
     * @return the superscript Chunk
     */
    public static Chunk sup(Object text) {
        return new Chunk(text.toString()).setTextRise(5f);
    }

    /**
     * Loads an image from a file and scales it down by 50%.
     * @param imageFile the path to the image file
     * @return the Chunk containing the image, or null on error
     */
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

    /**
     * Appends multiple parts (chunks or text) to an existing phrase.
     * @param phrase the phrase to append to
     * @param parts objects/chunks to add
     * @return the populated phrase
     */
    public static Phrase text(Phrase phrase, Object... parts) {
        for (Object part : parts) {
            switch (part.getClass().getName()) {
                case "com.lowagie.text.Chunk":
                    phrase.add((Chunk) part);
                    break;
                default:
                    phrase.add(new Chunk(part + ""));
                    break;
            }
        }
        return phrase;
    }

    /**
     * Creates a new phrase using the current font and appends specified parts.
     * @param parts objects/chunks to add
     * @return the constructed Phrase
     */
    public Phrase phrase(Object... parts) {
        Phrase phrase = new Phrase("", font);
        return text(phrase, parts);
    }

    /**
     * Creates a phrase with text followed by a subscript index.
     * @param text base text
     * @param index subscript index text
     * @param font the font to use
     * @return the generated Phrase
     */
    public static Phrase subScriptPhrase(String text, String index, Font font) {
        Phrase phrase = new Phrase(text, font);
        phrase.add(new Chunk(index).setTextRise(-5f));
        return phrase;
    }

    /**
     * Creates a phrase with text, a subscript index, and remaining text.
     * @param text base text
     * @param index subscript index text
     * @param remaining remaining text
     * @param font the font to use
     * @return the generated Phrase
     */
    public static Phrase subScriptPhrase(String text, String index, String remaining, Font font) {
        Phrase phrase = new Phrase(text, font);
        phrase.add(new Chunk(index).setTextRise(-5f));
        phrase.add(new Chunk(remaining));
        return phrase;
    }

    /**
     * Creates a phrase with text, a Greek character, a subscript index, and remaining text.
     * @param text base text
     * @param greek Greek character text
     * @param index subscript index text
     * @param remaining remaining text
     * @param font the font to use
     * @return the generated Phrase
     */
    public static Phrase greekAndSubScriptPhrase(String text, String greek, String index, String remaining, Font font) {
        Phrase phrase = new Phrase(text, font);
        phrase.add(new Chunk(greek, FontFactory.getFont(new File(System.getenv("WINDIR"),
                "Fonts").getAbsolutePath() + "\\times.ttf", "Cp1253", false, 12, Font.NORMAL)));
        phrase.add(new Chunk(index).setTextRise(-5f));
        phrase.add(new Chunk(remaining));
        return phrase;
    }

    /**
     * Creates a phrase with a subscript using the default font.
     * @param text base text
     * @param index subscript index text
     * @return the generated Phrase
     */
    public Phrase withSub(String text, String index) {
        return subScriptPhrase(text, index, font);
    }

    /**
     * Creates a phrase with a subscript and remaining text using the default font.
     * @param text base text
     * @param index subscript index text
     * @param remaining remaining text
     * @return the generated Phrase
     */
    public Phrase withSub(String text, String index, String remaining) {
        return subScriptPhrase(text, index, remaining, font);
    }

    /**
     * Creates a phrase with Greek characters and a subscript using the default font.
     * @param text base text
     * @param greek Greek character text
     * @param index subscript index text
     * @param remaining remaining text
     * @return the generated Phrase
     */
    public Phrase withGreekAndSub(String text, String greek, String index, String remaining) {
        return greekAndSubScriptPhrase(text, greek, index, remaining, font);
    }

    /**
     * Creates a phrase with a subscript in Greek formatting.
     * @param text base text
     * @param index subscript index text
     * @return the generated Phrase
     */
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
    private final ByteArrayOutputStream out = new ByteArrayOutputStream();

    /**
     * This is deprecated constructor used by DocumentProcessor class only[cite: 1]
     * @param document previously created document[cite: 1]
     */
    public Report(Document document) {
        if (document == null) {
            throw new NullPointerException();
        }
        this.document = document;
    }

    /**
     * The main constructor that sets one of possible types (HTML / PDF)[cite: 1]
     * @param docType the document generation type (HTML or PDF)
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

    /**
     * Closes the document and returns the resulting byte array (including page numbers if applicable).
     * @return byte array of the report content
     */
    public byte[] getResult() {
        document.close();
        return withPageNumbers();
        //return this.out.toByteArray();
    }

    /**
     * Post-processes the PDF document to stamp page numbers onto each page.
     * @return byte array of the stamped PDF, or original bytes on error
     */
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

    /**
     * Sets the underlying OpenPDF Document instance.
     * @param document the Document instance
     */
    public void setDocument(Document document) {
        this.document = document;
    }

    /**
     * Gets the table cell padding.
     * @return the padding value
     */
    public int getPadding() {
        return padding;
    }

    /**
     * Sets the table cell padding.
     * @param padding the padding value to set
     */
    public void setPadding(int padding) {
        this.padding = padding;
    }

    /**
     * Gets the standard font.
     * @return the standard Font
     */
    public Font getFont() {
        return font;
    }

    /**
     * Sets the standard font.
     * @param font the Font to set
     */
    public void setFont(Font font) {
        this.font = font;
    }

    /**
     * Gets the bold font.
     * @return the bold Font
     */
    public Font getFontBold() {
        return fontBold;
    }

    /**
     * Sets the bold font.
     * @param fontBold the bold Font to set
     */
    public void setFontBold(Font fontBold) {
        this.fontBold = fontBold;
    }

    /**
     * Helper method to initialize and format a Table instance.
     */
    private Table prepareTable(String tableName, Phrase[] columnName, int width, int columnCount,
                               int padding, java.awt.Color borderColor, int... widths) throws DocumentException {
        this.padding = padding;
        Cell cell;
        if (tableName != null && !tableName.isEmpty()) {
            document.add(new Paragraph(tableName + ":", fontBold));
            document.add(new Paragraph("\n"));
        }
        Table table = null;
        try {
            table = new Table(columnCount);
        }
        catch (BadElementException e) {
            throw new TableDataException();
        }
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
        table.setHorizontalAlignment(HorizontalAlignment.LEFT);
        /*Add Header*/
        if (columnName != null) {
            for (Phrase elements : columnName) {
                cell = new Cell(elements);
                table.addCell(cell);
            }
        }
        return table;
    }

    /**
     * Creates and adds a table to the document using Phrase arrays.
     */
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

    /**
     * Creates a table with default settings from a 2D Object array.
     * @param tableData the data for the table
     */
    public void createTable(Object[][] tableData) {
        try {
            createTable(tableData, 100, 2, Color.DARK_GRAY);
        }
        catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates a table with headers and default settings from a 2D Object array.
     * @param titles header titles
     * @param tableData the data for the table
     */
    public void createTable(Object[] titles, Object[][] tableData) {
        try {
            createTable(titles, tableData, 100, 2, Color.DARK_GRAY);
        }
        catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates a table from a 2D Object array with custom sizing and border colors.
     */
    public void createTable(Object[][] tableData, int width, int padding,
                            java.awt.Color borderColor, int... widths) throws DocumentException {
        createTable(null, tableData, width, padding, borderColor, widths);
    }

    /**
     * Creates a table with headers from a 2D Object array and custom styling options.
     */
    public void createTable(Object[] header, Object[][] tableData, int width, int padding,
                            java.awt.Color borderColor, int... widths) throws DocumentException {
        int columnCount = 0;
        for (Object[] tableDatum : tableData) {
            if (columnCount < tableDatum.length) {
                columnCount = tableDatum.length;
            }
        }
        this.padding = padding;
        Cell cell;
        Table table = null;
        try {
            table = new Table(columnCount);
        }
        catch (BadElementException e) {
            throw new TableDataException();
        }
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
        table.setHorizontalAlignment(HorizontalAlignment.LEFT);
        if (header != null) {
            for (int j = 0; j < columnCount; j++) {
                String className = header[j].getClass().getName();
                cell = switch (className) {
                    case "com.lowagie.text.Phrase" -> new Cell((Phrase) header[j]);
                    case "com.lowagie.text.Cell" -> (Cell) header[j];
                    default -> new Cell(new Phrase(header[j] + "", fontBold));
                };
                table.addCell(cell);
            }
        }
        for (int i = 0; i < tableData.length; i++) {
            for (int j = 0; j < columnCount; j++) {
                if (j < tableData[i].length) {
                    String className = tableData[i][j].getClass().getName();
                    cell = switch (className) {
                        case "com.lowagie.text.Phrase" -> new Cell((Phrase) tableData[i][j]);
                        case "com.lowagie.text.Cell" -> (Cell) tableData[i][j];
                        default -> new Cell(new Phrase(tableData[i][j] + "", font));
                    };
                    if (tableData[i][j].toString().equals("1.0")) {
                        cell = new Cell(new Phrase("1.00", font));
                    }
                } else {
                    cell = new Cell("");
                }
                table.addCell(cell);
            }
        }
        document.add(table);
    }

    /**
     * Formats an array of objects into table header cells centered horizontally.
     * @param cells array of header contents
     * @return array of formatted header objects
     */
    public Object[] header(Object... cells) {
        Object[] result = new Object[cells.length];
        for (int i = 0; i < cells.length; i++) {
            Cell cell;
            String className = cells[i].getClass().getName();
            try {
                cell = switch (className) {
                    case "com.lowagie.text.Phrase" -> new Cell((Phrase) cells[i]);
                    case "com.lowagie.text.Cell" -> (Cell) cells[i];
                    default -> new Cell(new Phrase(cells[i] + "", fontBold));
                };
                cell.setHorizontalAlignment(HorizontalAlignment.CENTER);
                result[i] = cell;
            }
            catch (DocumentException e) {
                result[i] = cells[i];
            }

        }
        return result;
    }

    /**
     * Creates and adds a table using a 2D Cell array.
     */
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

    /**
     * Creates and adds a table using a 2D String array.
     */
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

    /**
     * Creates and adds a table using a 2D String array with column widths.
     */
    public void createTable(String tableName, Phrase[] columnName,
                            Phrase[] rowName, int width, String[][] tableData, int... widths)
            throws DocumentException {
        Cell cell;
        if (!Objects.equals(tableName, "")) {
            document.add(new Paragraph(tableName + ":", fontBold));
            document.add(new Paragraph("\n"));
        }
        Table table = null;
        try {
            table = new Table(columnName.length);
        }
        catch (BadElementException e) {
            throw new TableDataException();
        }
        table.setPadding(padding);
        if (width > 0) {
            table.setWidth(width);
        }
        if (widths != null && widths.length > 0) {
            table.setWidths(widths);
        }
        table.setHorizontalAlignment(HorizontalAlignment.LEFT);
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

    /**
     * Adds text to the document using the standard font.
     * @param text string to add
     * @return true if successful, false otherwise
     */
    public boolean text(String text) {
        return text(text, font);
    }

    /**
     * Adds text to the document using a specified font.
     * @param text string to add
     * @param font font to use
     * @return true if successful, false otherwise
     */
    public boolean text(String text, Font font) {
        try {
            document.add(new Paragraph(text, font));
            return true;
        }
        catch (DocumentException e) {
            return false;
        }
    }

    /**
     * Adds multi-language/multi-string text using the standard font.
     * @param text MultiString object
     * @return true if successful, false otherwise
     */
    public boolean text(MultiString text) {
        return text(text.toString());
    }

    /**
     * Adds multi-language/multi-string text using a specified font.
     * @param text MultiString object
     * @param font font to use
     * @return true if successful, false otherwise
     */
    public boolean text(MultiString text, Font font) {
        return text(text.toString(), font);
    }

    /**
     * Adds a new line/space to the document.
     * @return true if successful, false otherwise
     */
    public boolean newLine() {
        return text("\240");
    }

    static void main() {
        Object[] arr = {"1", 2, 3.0, new Cell(), new Phrase()};
        for (Object o : arr) {
            System.out.println(o.getClass().getName());
        }
    }

    /**
     * Exception thrown when table data is invalid or encounters a bad element.
     */
    public static class TableDataException extends RuntimeException {
    }
}