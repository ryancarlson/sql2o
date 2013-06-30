package org.sql2o.parameters;

import org.sql2o.tools.NamedParameterStatement;

import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: lars
 * Date: 6/29/13
 * Time: 3:26 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ParameterSetter {

    void setParameterValue(NamedParameterStatement statement) throws SQLException;
}
