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

public class UserSerializer extends StdSerializer<APIUser> {

    public UserSerializer() {
        this(null);
    }

    public UserSerializer(Class<APIUser> t) {
        super(t);
    }



    @Override
    public void serialize(
            APIUser value, JsonGenerator jgen, SerializerProvider provider)
            throws IOException, JsonProcessingException {

        jgen.writeStartObject();
        jgen.writeNumberField("id", value.getId());
        jgen.writeStringField("username", value.getUsername());
        jgen.writeStringField("name", value.getName());
        jgen.writeStringField("email", value.getEmail());

        jgen.writeFieldName("roles");
        jgen.writeStartArray();

        List<Role> roles = (List<Role>) value.getRoles();


        for (Role p: roles) {
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