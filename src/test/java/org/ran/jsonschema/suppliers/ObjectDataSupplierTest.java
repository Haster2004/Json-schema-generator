package org.ran.jsonschema.suppliers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.ran.jsonschema.SchemaDataSupplier;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ObjectDataSupplierTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void generateFromSchema() {
        try (InputStream inputStream = getClass().getResourceAsStream("schema.json")) {
            JSONObject rawSchema = new JSONObject(new JSONTokener(inputStream));
            Schema schema = SchemaLoader.load(rawSchema);
            System.out.println(new SchemaDataSupplier().get(schema));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void generateFromSchemaWithInnerFields() {
        try (InputStream inputStream = getClass().getResourceAsStream("schemaWithInnerFields.json")) {
            JSONObject rawSchema = new JSONObject(new JSONTokener(inputStream));
            Schema schema = SchemaLoader.load(rawSchema);
            Object generatedData = new SchemaDataSupplier().get(schema);
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(generatedData);
            assertTrue(json.matches("\\{\"a\":\"[A-z]*\",\"field1\":\\{\"field12\":\\{\"field123\":" +
                    "\\{\"field123int\":[0-9]*,\"field123str\":\"[A-z]*\"}}},\"c\":[0-9]*}"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}