package org.ran.jsonschema.suppliers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsonschema.JsonSchema;
import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.jupiter.api.Test;
import org.ran.jsonschema.SchemaDataSupplier;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

class EnumDataSupplierTest {

    @Test
    void generateFromSchemaWithEnum() {
        try (InputStream inputStream = getClass().getResourceAsStream("schemaWithEnum.json")) {
            JSONObject rawSchema = new JSONObject(new JSONTokener(inputStream));
            Schema schema = SchemaLoader.load(rawSchema);
            Object generatedData = new SchemaDataSupplier().get(schema);
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(generatedData);
            assertTrue(json.matches("\\{\"six\":(2|true|null|3\\.5|\\[\"almost empty aray\"\\]|\\{\"one-item\":\"object\"})}"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}