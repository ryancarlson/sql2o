package org.sql2o.converters;

import org.postgresql.jdbc4.Jdbc4Array;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.UUID;

/**
 * Used by sql2o to convert a value from the database into a {@link java.util.UUID}.
 */
public class UUIDArrayConverter implements Converter<UUID[]>
{
	public UUID[] convert(Object val) throws ConverterException
	{
		if (val == null)
		{
			return null;
		}

		if (Jdbc4Array.class.isAssignableFrom(val.getClass()))
		{
			Jdbc4Array jdbc4Array = (Jdbc4Array) val;

			try
			{
				ResultSet resultSet = jdbc4Array.getResultSet();

				// get result set row count
				resultSet.last();
				UUID[] uuids = new UUID[resultSet.getRow()];
				resultSet.beforeFirst();

				// get uuid from last column in each row
				for (int i = 0; resultSet.next(); i++)
				{
					ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
					String result = resultSet.getString(resultSetMetaData.getColumnCount());
					uuids[i] = UUID.fromString(result);
				}

				return uuids;
			}
			catch (SQLException e)
			{
				throw new ConverterException("Cannot convert type " + val.getClass().toString() + " to java.util.UUID[] due to sql exception", e);
			}
		}

		throw new ConverterException("Cannot convert type " + val.getClass().toString() + " to java.util.UUID[]");
	}
}

