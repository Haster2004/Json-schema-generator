package org.ran.jsonschema.suppliers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.jupiter.api.Test;
import org.ran.jsonschema.SchemaDataSupplier;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CombinedDataSupplierTest {
    @Test
    public void oneOfSchemaGenerateCorrectly() throws Exception {
        String schema = "" +
                "{\n" +
                "  \"oneOf\": [\n" +
                "    { \"type\": \"integer\"},\n" +
                "    { \"type\": \"string\"}\n" +
                "  ]\n" +
                "}";

        Object generatedData = new SchemaDataSupplier().get(schema);
        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(generatedData);

        System.out.println(json);
        //schema.validate(new JSONObject(new JSONTokener(json)));
    }

    @Test
    public void SchemaWithNestedOneOfGenerateCorrectly() {
        try (InputStream inputStream = getClass().getResourceAsStream("schemaWithNestedOneOf.json")) {
            JSONObject rawSchema = new JSONObject(new JSONTokener(inputStream));
            Schema schema = SchemaLoader.load(rawSchema);
            Object generatedData = new SchemaDataSupplier().get(schema);
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(generatedData);
            schema.validate(new JSONObject(json));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void allOfSchemaGenerateCorrectly() throws Exception {
        String schema = "" +
                "{\n" +
                "  \"allOf\": [\n" +
                "    { \"type\": \"string\" },\n" +
                "    { \"maxLength\": 5 }\n" +
                "  ]\n" +
                "}";

        Object generatedData = new SchemaDataSupplier().get(schema);
        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(generatedData);

        System.out.println(json);
        //schema.validate(new JSONObject(new JSONTokener(json)));
    }

    @Test
    public void allOfObjectsSchemaGenerateCorrectly() throws Exception {
        String schema = "" +
                "{\n" +
                "  \"allOf\": [\n" +
                "    {\n" +
                "      \"type\": \"object\",\n" +
                "      \"properties\": {\n" +
                "          \"b\": {\n" +
                "            \"type\":\"string\"\n" +
                "          }\n" +
                "      },\n" +
                "      \"required\": [\"b\"],\n" +
                "        \"additionalProperties\":true\n" +
                "    },\n" +
                "    {\n" +
                "      \"type\": \"object\",\n" +
                "      \"properties\": {\n" +
                "          \"a\": {\n" +
                "            \"type\":\"string\"\n" +
                "          }\n" +
                "       },\n" +
                "      \"required\": [\"a\"],\n" +
                "        \"additionalProperties\":true\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        Object generatedData = new SchemaDataSupplier().get(schema);
        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(generatedData);

        System.out.println(json);
        //schema.validate(new JSONObject(new JSONTokener(json)));
    }

    private ObjectMapper mapper = new ObjectMapper();
}
