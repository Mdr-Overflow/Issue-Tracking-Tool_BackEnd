package Issue.Tracking.Tool.LoginSessionPoint.constants;

import java.util.ArrayList;
import java.util.List;

public class EndPointNames {
// Regarding Issues
// USER is able to change name, description
    //Leader can change anything excluding groups on issue
        // Admin can change anything


    public static List<String> Gen_Endp_Names() {


        return (List<String>) List.of(
                ("USER,USER_SAVE,USER_UPDATE,USER_CHANGEPASS,USERS,USER_DELETE,USER_GET,GROUPMANAGER," +
                        "GROUPMANAGER_SAVE,GROUPMANAGER_GET,GROUPMANAGER_DELETE,GROUPMANAGER_ADDUSER," +
                        "GROUPMANAGER_CHANGELEADER,GROUPMANAGER_ADDUSER,GROUPMANAGER_DELETE,ISSUEDASHBOARD," +
                        "ISSUEDASHBOARD_SAVE,ISSUEDASHBOARD_DELETE,ISSUEDASHBOARD_ADMIN_UPDATE,ISSUEDASHBOARD_USER_UPDATE" +
                        ",ISSUEDASHBOARD_LEADER_UPDATE,ISSUEDASHBOARD_GET,ISSUEDASHBOARD_GETSOL,EXTRAS_GET,EXTRAS_UPDATE,EXTRAS_SAVE,EXTRAS_DELETE"
                ).split(",")
        );
    }
}
