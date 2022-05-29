package Issue.Tracking.Tool.LoginSessionPoint.exception;

public class InvalidMailException extends RuntimeException {

    public InvalidMailException() {

        super("String entered is not a valid mail format");
    }
}
