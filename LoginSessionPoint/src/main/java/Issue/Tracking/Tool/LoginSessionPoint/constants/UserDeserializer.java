package Issue.Tracking.Tool.LoginSessionPoint.constants;

import Issue.Tracking.Tool.LoginSessionPoint.domain.APIUser;
import Issue.Tracking.Tool.LoginSessionPoint.domain.Role;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.IntNode;
import com.fasterxml.jackson.databind.node.LongNode;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.Date;

public class UserDeserializer extends StdDeserializer<APIUser> {


    private final DateTimeFormatter dateFormatter = new DateTimeFormatterBuilder()
            .appendOptional(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss"))
            .toFormatter();

    public UserDeserializer() {
        this(null);
    }

    public UserDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public APIUser deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        JsonNode node = jp.getCodec().readTree(jp);
        Long id = (Long) ((LongNode) node.get("id")).numberValue();
        String username = node.get("username").asText();
        String email = node.get("email").asText();
        String name = node.get("name").asText();

        String createdAt = node.get("createdAt").asText();
        LocalDateTime dateToConvert = LocalDateTime.parse(createdAt, dateFormatter);
        Date in = new Date();
        Date out = Date.from(dateToConvert.atZone(ZoneId.systemDefault()).toInstant());

        String updatedAt = node.get("updatedAt").asText();
        LocalDateTime dateToConvert2 = LocalDateTime.parse(updatedAt , dateFormatter);
        Date in2 = new Date();
        Date out2 = Date.from(dateToConvert.atZone(ZoneId.systemDefault()).toInstant());

        return new APIUser(id, username,null,email,name,new ArrayList<>(),new ArrayList<>(),out,out2);
    }
}