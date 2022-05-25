package Issue.Tracking.Tool.LoginSessionPoint.exception;

public class PasswordMissingException extends RuntimeException{

    public PasswordMissingException() {

        super("Must provide a password");
    }

}
