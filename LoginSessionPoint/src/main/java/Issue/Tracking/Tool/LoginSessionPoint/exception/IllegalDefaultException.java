package Issue.Tracking.Tool.LoginSessionPoint.exception;

public class IllegalDefaultException extends RuntimeException{

    public IllegalDefaultException() {

        super("illegal change to default");
    }
}
