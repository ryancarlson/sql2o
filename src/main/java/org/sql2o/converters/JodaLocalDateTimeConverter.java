package org.sql2o.converters;

import org.joda.time.LocalDateTime;

/**
 * Created by ryancarlson on 8/13/14.
 */
public class JodaLocalDateTimeConverter implements Converter<LocalDateTime>
{
	public LocalDateTime convert(Object val) throws ConverterException {
		if (val == null){
			return null;
		}

		try{
			return new LocalDateTime(val);
		}
		catch(Throwable t){
			throw new ConverterException("Error while converting type " + val.getClass().toString() + " to joda LocalDateTime", t);
		}
	}
}
