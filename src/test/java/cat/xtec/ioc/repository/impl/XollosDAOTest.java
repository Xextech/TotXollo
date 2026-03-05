package cat.xtec.ioc.repository.impl;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;

import cat.xtec.ioc.domain.Xollo;

public class XollosDAOTest {

    private XolloDAO xolloDAO;

    @Before
    public void setUp() throws Exception {
        xolloDAO = new XolloDAO("db_test.properties");
        resetDatabase();
    }

    @Test
    public void testGetXolloByCodi() {
        Xollo xollo = xolloDAO.getXolloByCodi("1");

        assertNotNull(xollo);
        assertEquals("1", xollo.getCodi());
        assertEquals("Xollo1", xollo.getTitol());
    }

    @Test
    public void testGetAllXollos() {
        List<Xollo> xollos = xolloDAO.getAllXollos();

        assertNotNull(xollos);
        assertEquals(6, xollos.size());
    }

    @Test
    public void testAddXollo() {
        Xollo newXollo = new Xollo("99", 15, 0, "Xollo99", "Descripcio99");

        xolloDAO.addXollo(newXollo);
        Xollo created = xolloDAO.getXolloByCodi("99");

        assertNotNull(created);
        assertEquals("99", created.getCodi());
        assertEquals(Integer.valueOf(15), created.getNumeroUnitats());
    }

    @Test
    public void testUpdateXollo() {
        Xollo xollo = xolloDAO.getXolloByCodi("2");
        xollo.setNumeroReserves(3);
        xollo.setTitol("Xollo2Actualitzat");

        xolloDAO.updateXollo(xollo);
        Xollo updated = xolloDAO.getXolloByCodi("2");

        assertEquals(Integer.valueOf(3), updated.getNumeroReserves());
        assertEquals("Xollo2Actualitzat", updated.getTitol());
    }

    private void resetDatabase() throws Exception {
        DBConnection dbConnection = new DBConnection("db_test.properties");

        try (Connection connection = dbConnection.getConnection();
                Statement statement = connection.createStatement()) {
            statement.execute("DROP ALL OBJECTS");
            statement.execute("RUNSCRIPT FROM 'classpath:init.sql'");
        }
    }
}