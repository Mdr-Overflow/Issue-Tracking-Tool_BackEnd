package Issue.Tracking.Tool.LoginSessionPoint.constants;

import Issue.Tracking.Tool.LoginSessionPoint.domain.APIUser;
import Issue.Tracking.Tool.LoginSessionPoint.domain.Privilege;
import Issue.Tracking.Tool.LoginSessionPoint.domain.Role;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.util.List;

public class RoleSerializer extends StdSerializer<Role> {

    public RoleSerializer() {
        this(null);
    }

    public RoleSerializer(Class<Role> t) {
        super(t);
    }



    @Override
    public void serialize(
            Role value, JsonGenerator jgen, SerializerProvider provider)
            throws IOException, JsonProcessingException {

        jgen.writeStartObject();
        jgen.writeObjectField("id", value.getId());
        jgen.writeObjectField("name", value.getName());
        jgen.writeFieldName("privileges");
        jgen.writeStartArray();

        List<Privilege> privs = (List<Privilege>) value.getPrivileges();


        for (Privilege p : privs) {
            jgen.writeStartObject();
            jgen.writeObjectField("id", p.getId());
            jgen.writeObjectField("name", p.getName());
            jgen.writeEndObject();
        }

        jgen.writeEndArray();

       jgen.writeStringField("createdAt", value.getCreatedAt().toString());
       jgen.writeStringField("lastUpdated", value.getLastUpdated().toString());
        jgen.writeEndObject();
    }
}