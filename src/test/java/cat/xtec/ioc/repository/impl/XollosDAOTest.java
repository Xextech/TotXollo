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

    private XolloDAO daoXollo;

    @Before
    public void preparaEntorn() throws Exception {
        daoXollo = new XolloDAO("db_test.properties");
        reiniciaBaseDades();
    }

    @Test
    public void testObteXolloPerCodi() {
        Xollo xollo = daoXollo.getXolloByCodi("1");

        assertNotNull(xollo);
        assertEquals("1", xollo.getCodi());
        assertEquals("Xollo1", xollo.getTitol());
    }

    @Test
    public void testObteTotsElsXollos() {
        List<Xollo> llistaXollos = daoXollo.getAllXollos();

        assertNotNull(llistaXollos);
        assertEquals(6, llistaXollos.size());
    }

    @Test
    public void testAfegeixXollo() {
        Xollo nouXollo = new Xollo("99", 15, 0, "Xollo99", "Descripcio99");

        daoXollo.addXollo(nouXollo);
        Xollo xolloCreat = daoXollo.getXolloByCodi("99");

        assertNotNull(xolloCreat);
        assertEquals("99", xolloCreat.getCodi());
        assertEquals(Integer.valueOf(15), xolloCreat.getNumeroUnitats());
    }

    @Test
    public void testActualitzaXollo() {
        Xollo xollo = daoXollo.getXolloByCodi("2");
        xollo.setNumeroReserves(3);
        xollo.setTitol("Xollo2Actualitzat");

        daoXollo.updateXollo(xollo);
        Xollo xolloActualitzat = daoXollo.getXolloByCodi("2");

        assertEquals(Integer.valueOf(3), xolloActualitzat.getNumeroReserves());
        assertEquals("Xollo2Actualitzat", xolloActualitzat.getTitol());
    }

    private void reiniciaBaseDades() throws Exception {
        DBConnection connexioBd = new DBConnection("db_test.properties");

        try (Connection connexio = connexioBd.getConnexio();
                Statement sentencia = connexio.createStatement()) {
            sentencia.execute("DROP ALL OBJECTS");
            sentencia.execute("RUNSCRIPT FROM 'classpath:init.sql'");
        }
    }
}