package Issue.Tracking.Tool.LoginSessionPoint.constants;

import Issue.Tracking.Tool.LoginSessionPoint.domain.APIUser;
import Issue.Tracking.Tool.LoginSessionPoint.domain.Privilege;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.LongNode;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.Date;

public class PrivDeserializer extends StdDeserializer<Privilege> {


    private final DateTimeFormatter dateFormatter = new DateTimeFormatterBuilder()
            .appendOptional(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss"))
            .toFormatter();

    public PrivDeserializer() {
        this(null);
    }

    public PrivDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Privilege deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        JsonNode node = jp.getCodec().readTree(jp);
        Long id = (Long) ((LongNode) node.get("id")).numberValue();
        String username = node.get("name").asText();


        return new Privilege(id, username,new ArrayList<>());
    }
}