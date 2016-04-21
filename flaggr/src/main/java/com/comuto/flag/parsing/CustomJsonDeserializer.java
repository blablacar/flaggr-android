package com.comuto.flag.parsing;

import com.comuto.flag.operators.InsetOperator;
import com.comuto.flag.operators.ModuloOperator;
import com.comuto.flag.operators.Operator;
import com.comuto.flag.operators.PercentageOperator;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * Custom deserializer to indicate to the parser
 * how he can parse the abstract class Operator
 * with the multiple subclasses
 *
 * @see com.comuto.flag.operators.Operator
 */
public class CustomJsonDeserializer implements JsonDeserializer<Operator> {

    private static final String INSET_KEY = "in-set";
    private static final String INSET_OPERATOR_PACKAGE = InsetOperator.class.getCanonicalName();
    private static final String PERCENTAGE_KEY = "percentage";
    private static final String PERCENTAGE_OPERATOR_PACKAGE = PercentageOperator.class.getCanonicalName();
    private static final String MODULO_KEY = "modulo";
    private static final String MODULO_OPERATOR_PACKAGE = ModuloOperator.class.getCanonicalName();
    private static final String JSON_NAME_ATTR = "name";

    @Override
    public Operator deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        final JsonObject jsonObject = json.getAsJsonObject();
        final JsonElement name = jsonObject.get(JSON_NAME_ATTR);
        if (null != name) {
            String type = name.getAsString();

            try {
                if (type.equals(INSET_KEY)) {
                    return context.deserialize(jsonObject, Class.forName(INSET_OPERATOR_PACKAGE));
                } else if (type.equals(PERCENTAGE_KEY)) {
                    return context.deserialize(jsonObject, Class.forName(PERCENTAGE_OPERATOR_PACKAGE));
                } else if (type.equals(MODULO_KEY)) {
                    return context.deserialize(jsonObject, Class.forName(MODULO_OPERATOR_PACKAGE));
                }
                throw new JsonParseException("Unknown Operator : " + type);

            } catch (ClassNotFoundException cnfe) {
                throw new JsonParseException("Unknown element type: " + type, cnfe);
            }
        }
        throw new JsonParseException("Operator without name");
    }
}
