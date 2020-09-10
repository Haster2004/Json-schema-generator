package org.ran.jsonschema.suppliers;


import org.everit.json.schema.ObjectSchema;
import org.everit.json.schema.Schema;
import org.ran.jsonschema.SupplyResolver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class ObjectDataSupplier extends AbstractDataSupplier<ObjectSchema, Map<String, Object>> {


    @Override
    public Map<String, Object> generateDataFromSchema(ObjectSchema schema) {
        Map<String, Object> mapData = new HashMap<>();

        // todo add support for min and max properties
        Set<String> properties = schema.getPropertySchemas().keySet();
        properties.forEach(stream -> {
            Schema propertySchema = schema.getPropertySchemas().get(stream);
            Object generatedExample = SupplyResolver.resolve(propertySchema).forSchema(propertySchema);
            mapData.put(stream, generatedExample);
        });
        return mapData;
    }


}
