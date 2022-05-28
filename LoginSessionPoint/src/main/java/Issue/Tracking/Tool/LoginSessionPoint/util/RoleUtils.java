package Issue.Tracking.Tool.LoginSessionPoint.util;

import Issue.Tracking.Tool.LoginSessionPoint.domain.APIUser;
import Issue.Tracking.Tool.LoginSessionPoint.domain.Role;
import Issue.Tracking.Tool.LoginSessionPoint.repo.RoleRepo;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@NoArgsConstructor
public class RoleUtils {
    private static RoleRepo roleRepo;
    public static void giveRole(String roleName, APIUser user){

        Role role = new Role(null,roleName,null,null);
        user.getRoles().add(role);

    }

    public static <T> Stream<T> convertListToStream(List<T> list)
    {
        return list.stream();
    }
    public static Boolean StringToBoolean(String s){
        return s.equals("true");
    }
}
