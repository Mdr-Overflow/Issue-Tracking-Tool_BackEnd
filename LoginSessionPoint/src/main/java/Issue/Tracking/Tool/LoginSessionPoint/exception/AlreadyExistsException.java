package Issue.Tracking.Tool.LoginSessionPoint.exception;

import Issue.Tracking.Tool.LoginSessionPoint.domain.APIUser;

public class AlreadyExistsException extends  RuntimeException{

    public AlreadyExistsException(Class<?> Class){
        super(Class.getName() + " Already Exists.");
    }
}
