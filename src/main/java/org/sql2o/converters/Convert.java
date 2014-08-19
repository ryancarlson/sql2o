package org.sql2o.converters;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Static class used to register new converters. Also used internally by sql2o to lookup a converter.
 */
public class Convert {

    private static final Logger logger = LoggerFactory.getLogger(Convert.class);
    
    private static Map<Class<?>, Converter<?>> registeredConverters = new HashMap<Class<?>, Converter<?>>();

    static{
        registerConverter(Integer.class, new IntegerConverter(false));
        registerConverter(int.class, new IntegerConverter(true));

        registerConverter(Double.class, new DoubleConverter(false));
        registerConverter(double.class, new DoubleConverter(true));

        registerConverter(Float.class, new FloatConverter(false));
        registerConverter(float.class, new FloatConverter(true));

        registerConverter(Long.class, new LongConverter(false));
        registerConverter(long.class, new LongConverter(true));

        registerConverter(Short.class, new ShortConverter(false));
        registerConverter(short.class, new ShortConverter(true));

        registerConverter(Byte.class, new ByteConverter(false));
        registerConverter(byte.class, new ByteConverter(true));

        registerConverter(BigDecimal.class, new BigDecimalConverter());
        
        registerConverter(String.class, new StringConverter());
        
        Converter<Date> utilDateConverter = new DateConverter();
        registerConverter(java.util.Date.class, utilDateConverter);
        registerConverter(java.sql.Date.class, utilDateConverter);
        registerConverter(java.sql.Time.class, utilDateConverter);
        registerConverter(java.sql.Timestamp.class, utilDateConverter);

        BooleanConverter booleanConverter = new BooleanConverter();
        registerConverter(Boolean.class, booleanConverter);
        registerConverter(boolean.class, booleanConverter);

        try {
            Class<?> jodaTimeClass = Class.forName("org.joda.time.DateTime");
            registerConverter(jodaTimeClass, new JodaDateTimeConverter());
        } catch (ClassNotFoundException e) {
            logger.warn("Failed to initialize Joda DateTime. DateTime converter not registered");
        }

		try {
			Class<?> jodaLocalDateTimeClass = Class.forName("org.joda.time.LocalDateTime");
			registerConverter(jodaLocalDateTimeClass, new JodaLocalDateTimeConverter());
		} catch (ClassNotFoundException e) {
			logger.warn("Failed to initialize Joda LocalDateTime. LocalDateTime converter not registered");
		}

		try {
			Class<?> jodaDateTimeZoneClass = Class.forName("org.joda.time.DateTimeZone");
			registerConverter(jodaDateTimeZoneClass, new JodaDateTimeZoneConverter());
		} catch (ClassNotFoundException e) {
			logger.warn("Failed to initialize Joda DateTimeZone. DateTimeZone converter not registered");
		}

        ByteArrayConverter byteArrayConverter = new ByteArrayConverter();
        registerConverter(Byte[].class, byteArrayConverter);
        registerConverter(byte[].class, byteArrayConverter);
`
        InputStreamConverter inputStreamConverter = new InputStreamConverter();
        registerConverter(InputStream.class, inputStreamConverter);
        registerConverter(ByteArrayInputStream.class, inputStreamConverter);

        registerConverter(UUID.class, new UUIDConverter());
        registerConverter(UUID[].class, new UUIDArrayConverter());

        try {
        	Class<?> jsonNodeClass = Class.forName("com.fasterxml.jackson.databind.JsonNode");
        	registerConverter(jsonNodeClass, new JsonNodeConverter());
        }
        catch(ClassNotFoundException e) {
        	logger.warn("Failed to initialize JsonNode. JsonNode converter not register");
        }

        try {
            Class<?> xmlDocumentClass = Class.forName("org.w3c.dom.Document");
            registerConverter(xmlDocumentClass, new XmlConverter());
        }
        catch(ClassNotFoundException e) {
            logger.warn("Failed to initialize Xml Document. Document converter not register");
        }

    }
    
    public static Converter<?> getConverter(Class<?> clazz) throws ConverterException {
        if (registeredConverters.containsKey(clazz)){
            return registeredConverters.get(clazz);
        } else if (clazz.isEnum()) {
            return new EnumConverter(clazz);
        } else{
            throw new ConverterException("No converter registered for class: " + clazz.getName());
        }

    }
    
    public static void registerConverter(Class<?> clazz, Converter<?> converter){
        registeredConverters.put(clazz, converter);
    }
}
