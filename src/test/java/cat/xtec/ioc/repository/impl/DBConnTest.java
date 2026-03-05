package cat.xtec.ioc.repository.impl;

import java.io.IOException;
import java.sql.Connection;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import org.junit.Test;

public class DBConnTest {

    @Test
    public void testConnectionWithDbTestProperties() throws Exception {
        DBConnection dbConnection = new DBConnection("db_test.properties");
        Connection connection = dbConnection.getConnection();

        assertNotNull(connection);
        assertFalse(connection.isClosed());

        connection.close();
    }

    @Test
    public void testConnectionWithWrongDriverProperties() {
        try {
            new DBConnection("db_wrong_driver.properties");
            fail("S'esperava ClassNotFoundException amb db_wrong_driver.properties");
        } catch (ClassNotFoundException ex) {
            assertNotNull(ex);
        } catch (IOException ex) {
            fail("No s'esperava IOException: " + ex.getMessage());
        }
    }
}