package Issue.Tracking.Tool.LoginSessionPoint.constants;

import Issue.Tracking.Tool.LoginSessionPoint.domain.Privilege;
import Issue.Tracking.Tool.LoginSessionPoint.domain.Role;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class PrivSerializer extends StdSerializer<Privilege> {

    public PrivSerializer() {
        this(null);
    }

    public PrivSerializer(Class<Privilege> t) {
        super(t);
    }



    @Override
    public void serialize(
            Privilege value, JsonGenerator jgen, SerializerProvider provider)
            throws IOException, JsonProcessingException {

        jgen.writeStartObject();
        jgen.writeObjectField("id", value.getId());
        jgen.writeObjectField("name", value.getName());
        jgen.writeEndObject();
    }
}