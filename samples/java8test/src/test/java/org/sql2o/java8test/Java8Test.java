package org.sql2o.java8test;

import org.junit.*;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.StatementRunnable;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
/**
 * Created with IntelliJ IDEA.
 * User: lars
 * Date: 6/28/13
 * Time: 11:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class Java8Test {


    @Test
    public void testSomething() {
        assertThat(true, is(true));

        Sql2o sql2o = new Sql2o("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1","sa", "");


        sql2o.runInTransaction((Connection connection, Object argument) -> {
            String sql = "select 1 from (values(0))";

            int val = connection.createQuery(sql).executeScalar(Integer.class);

            assertThat(val, is(equalTo(1)));
        });
    }
}
