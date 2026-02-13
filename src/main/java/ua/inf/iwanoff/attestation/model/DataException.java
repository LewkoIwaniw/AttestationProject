package ua.inf.iwanoff.attestation.model;

public class DataException extends RuntimeException {
    public static class WrongXPSS extends DataException {
    }

    public static class WrongFileFormat extends DataException {
    }

    public static class ReportException extends DataException {
        public ReportException(String message) {
            super(message);
        }
    }

    public static class WrongConcentration extends DataException {
    }

    public static class WrongUncertainty extends DataException {
    }

    public static class WrongX extends DataException {
    }

    public DataException() {
    }

    public DataException(String message) {
        super(message);
    }

    public static class InsufficientDataException extends DataException {
    }

    public static class NoXException extends DataException {
    }
}
