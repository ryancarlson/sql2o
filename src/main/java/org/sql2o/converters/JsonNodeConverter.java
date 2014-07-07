package org.sql2o.converters;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.postgresql.util.PGobject;

public class JsonNodeConverter implements Converter<JsonNode> {

	public JsonNode convert(Object val) throws ConverterException {
		if(val == null) return null;

		if(JsonNode.class.isAssignableFrom(val.getClass())) {
			return (JsonNode)val;
		}

		if(PGobject.class.equals(val.getClass())) {
			String jsonString = ((PGobject)val).getValue();
			ObjectMapper mapper = new ObjectMapper();

			try {
				return mapper.readTree(jsonString);
			}
			catch(Exception e) {
				throw new ConverterException("Error parsing JSON", e);
			}
		}
		
		if(String.class.equals(val.getClass())) {
			String jsonString = (String)val;
			ObjectMapper mapper = new ObjectMapper();

			try {
				return mapper.readTree(jsonString);
			}
			catch(Exception e) {
				throw new ConverterException("Error parsing JSON", e);
			}
		}

		throw new ConverterException("Unable to convert: " + val.getClass().getCanonicalName() + " to JsonNode");
	}
}
