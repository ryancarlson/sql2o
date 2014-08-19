package org.sql2o.converters;

import org.joda.time.DateTimeZone;

/**
 * Created by ryancarlson on 8/19/14.
 */
public class JodaDateTimeZoneConverter implements Converter<DateTimeZone>
{
	public DateTimeZone convert(Object val) throws ConverterException
	{
		if(val == null) return null;

		try
		{
			return DateTimeZone.forID(val.toString());
		}
		catch(Throwable t)
		{
			throw new ConverterException("Error while converting type " + val.getClass().toString() + " to joda DateTimeZone", t);
		}
	}
}
