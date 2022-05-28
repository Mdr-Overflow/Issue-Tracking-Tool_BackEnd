package Issue.Tracking.Tool.LoginSessionPoint.service;

import Issue.Tracking.Tool.LoginSessionPoint.domain.Type;

import java.util.List;

public interface TypeService {

    void SaveType(Type type);
    List<Type> findALL();

    List<Type> findBy(String toSearch);

}
