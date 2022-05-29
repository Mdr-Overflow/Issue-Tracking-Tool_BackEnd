package Issue.Tracking.Tool.LoginSessionPoint.constants;

import java.util.UUID;

public class IdGenerator {
    public static long createId() {
        UUID uuid = UUID.randomUUID();
        return uuid.getLeastSignificantBits();
    }
}
