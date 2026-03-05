package cat.xtec.ioc.repository.impl;

import java.io.IOException;
import java.sql.Connection;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import org.junit.Test;

public class DBConnTest {

    @Test
    public void testConnexioAmbPropietatsDeTest() throws Exception {
        DBConnection connexioBd = new DBConnection("db_test.properties");
        Connection connexio = connexioBd.getConnexio();

        assertNotNull(connexio);
        assertFalse(connexio.isClosed());

        connexio.close();
    }

    @Test
    public void testConnexioAmbDriverErroni() {
        try {
            new DBConnection("db_wrong_driver.properties");
            fail("S'esperava ClassNotFoundException amb db_wrong_driver.properties");
        } catch (ClassNotFoundException excepcio) {
            assertNotNull(excepcio);
        } catch (IOException excepcio) {
            fail("No s'esperava IOException: " + excepcio.getMessage());
        }
    }
}