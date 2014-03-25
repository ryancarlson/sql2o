package org.sql2o.converters;

import org.postgresql.jdbc4.Jdbc4SQLXML;
import org.postgresql.util.PGobject;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;

/**
 * Created by ryancarlson on 3/20/14.
 */
public class XmlConverter implements Converter<Document>
{
    public Document convert(Object val) throws ConverterException
    {
        if(val == null) return null;

        if(Document.class.isAssignableFrom(val.getClass()))
        {
            return (Document)val;
        }

        String xmlString = null;

        if(PGobject.class.equals(val.getClass()))
        {
             xmlString = ((PGobject)val).getValue();
        }
        else if(Jdbc4SQLXML.class.equals(val.getClass()))
        {
            try {
            xmlString = ((Jdbc4SQLXML)val).getString();
            }catch(Exception e) {
                throw new RuntimeException(e);
            }
        }
        else if(String.class.equals(val.getClass()))
        {
            xmlString = (String)val;
        }

        if(xmlString != null)
        {
            try
            {
                DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

                return builder.parse(new ByteArrayInputStream(xmlString.getBytes()));
            }
            catch(Exception e)
            {
                throw new RuntimeException(e);
            }
        }

        throw new ConverterException("Unable to convert: " + val.getClass().getCanonicalName() + " to Xml Document");
    }
}
