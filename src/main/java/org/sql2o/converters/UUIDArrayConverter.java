package org.sql2o.converters;

import org.postgresql.jdbc4.Jdbc4Array;

import java.sql.ResultSet;
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

				UUID[] uuids = new UUID[rowCount(resultSet)];

				// get uuid from last column in each row
				int arrayIndices = 0;
				while(resultSet.next())
				{
					String result = resultSet.getString(resultSet.getMetaData().getColumnCount());
					uuids[arrayIndices++] = UUID.fromString(result);
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

	private int rowCount(ResultSet resultSet) throws SQLException
	{
		resultSet.last();

		int rowCount = resultSet.getRow();

		resultSet.beforeFirst();

		return rowCount;
	}
}
