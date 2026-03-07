package cat.xtec.ioc.repository.impl;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import cat.xtec.ioc.domain.Xollo;
import cat.xtec.ioc.repository.XolloRepository;

/**
 * Implementació JDBC del repositori de xollos.
 *
 * Fa operacions CRUD sobre la taula "xollos" utilitzant sentències SQL
 * preparades.
 */
@Repository
@Primary
public class XolloDAO implements XolloRepository {

    private final DBConnection connexioBd;

    public XolloDAO() {
        this("db.properties");
    }
    /** Constructor que permet especificar un fitxer de connexió diferent, útil per a proves. */
    public XolloDAO(String fitxerConnexio) {
        try {
            this.connexioBd = new DBConnection(fitxerConnexio);
        } catch (ClassNotFoundException ex) {
            throw new IllegalStateException("No s'ha pogut carregar el driver JDBC.", ex);
        } catch (IOException ex) {
            throw new IllegalStateException("No s'ha pogut llegir el fitxer de configuració.", ex);
        }
    }

    /**
     * Insereix un nou xollo a la base de dades.
     */
    @Override
    public void addXollo(Xollo xollo) {
        String sentenciaSql = "INSERT INTO xollos (codi, numeroUnitats, numeroReserves, titol, descripcio) VALUES (?, ?, ?, ?, ?)";

        /** Utilitza try-with-resources per assegurar el tancament de connexions i sentències, i gestiona les excepcions SQL. */
        try (Connection connexio = connexioBd.getConnexio();//
                PreparedStatement sentencia = connexio.prepareStatement(sentenciaSql)) {

            Integer numeroReserves = xollo.getNumeroReserves(); 
            if (numeroReserves == null) {
                numeroReserves = 0;
            }

            sentencia.setString(1, xollo.getCodi());
            sentencia.setInt(2, xollo.getNumeroUnitats());
            sentencia.setInt(3, numeroReserves);
            sentencia.setString(4, xollo.getTitol());
            sentencia.setString(5, xollo.getDescripcio());
            sentencia.executeUpdate();

        } catch (SQLException ex) {
            throw new IllegalArgumentException("No s'ha pogut afegir el xollo amb codi: " + xollo.getCodi(), ex);
        }
    }

    /**
     * Recupera un xollo pel seu codi.
     */
    @Override
    public Xollo getXolloByCodi(String codi) {
        String sentenciaSql = "SELECT codi, numeroUnitats, numeroReserves, titol, descripcio FROM xollos WHERE codi = ?";

        try (Connection connexio = connexioBd.getConnexio();
                PreparedStatement sentencia = connexio.prepareStatement(sentenciaSql)) {

            sentencia.setString(1, codi);

            try (ResultSet resultat = sentencia.executeQuery()) {
                if (resultat.next()) {
                    return mapaResultatAXollo(resultat);
                }
            }

        } catch (SQLException ex) {
            throw new IllegalStateException("Error en consultar el xollo amb codi: " + codi, ex);
        }

        throw new IllegalArgumentException("No s'ha trobat el xollo amb el codi: " + codi);
    }

    /**
     * Actualitza un xollo existent per codi.
     */
    @Override
    public void updateXollo(Xollo xollo) {
        String sentenciaSql = "UPDATE xollos SET numeroUnitats = ?, numeroReserves = ?, titol = ?, descripcio = ? WHERE codi = ?";

        try (Connection connexio = connexioBd.getConnexio();
                PreparedStatement sentencia = connexio.prepareStatement(sentenciaSql)) {

            sentencia.setInt(1, xollo.getNumeroUnitats());
            sentencia.setInt(2, xollo.getNumeroReserves());
            sentencia.setString(3, xollo.getTitol());
            sentencia.setString(4, xollo.getDescripcio());
            sentencia.setString(5, xollo.getCodi());

            int filesAfectades = sentencia.executeUpdate();
            if (filesAfectades == 0) {
                throw new IllegalArgumentException("No s'ha trobat el xollo amb el codi: " + xollo.getCodi());
            }

        } catch (SQLException ex) {
            throw new IllegalStateException("No s'ha pogut actualitzar el xollo amb codi: " + xollo.getCodi(), ex);
        }
    }

    /**
     * Retorna tots els xollos ordenats per codi.
     */
    @Override
    public List<Xollo> getAllXollos() {
        String sentenciaSql = "SELECT codi, numeroUnitats, numeroReserves, titol, descripcio FROM xollos ORDER BY codi";
        List<Xollo> xollos = new ArrayList<Xollo>();

        try (Connection connexio = connexioBd.getConnexio();
                PreparedStatement sentencia = connexio.prepareStatement(sentenciaSql);
                ResultSet resultat = sentencia.executeQuery()) {

            while (resultat.next()) {
                xollos.add(mapaResultatAXollo(resultat));
            }

            return xollos;
        } catch (SQLException ex) {
            throw new IllegalStateException("No s'han pogut recuperar els xollos.", ex);
        }
    }

    /**
     * Converteix una fila del ResultSet a objecte de domini Xollo.
     */
    private Xollo mapaResultatAXollo(ResultSet resultat) throws SQLException {
        Xollo xollo = new Xollo();
        xollo.setCodi(resultat.getString("codi"));
        xollo.setNumeroUnitats(resultat.getInt("numeroUnitats"));
        xollo.setNumeroReserves(resultat.getInt("numeroReserves"));
        xollo.setTitol(resultat.getString("titol"));
        xollo.setDescripcio(resultat.getString("descripcio"));
        return xollo;
    }
}