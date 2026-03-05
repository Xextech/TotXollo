package cat.xtec.ioc.repository.impl;

import cat.xtec.ioc.domain.Xollo;
import cat.xtec.ioc.repository.XolloRepository;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public class XolloDAO implements XolloRepository {

    private final DBConnection dbConnection;

    public XolloDAO() {
        this("db.properties");
    }

    public XolloDAO(String connectionFile) {
        try {
            this.dbConnection = new DBConnection(connectionFile);
        } catch (ClassNotFoundException ex) {
            throw new IllegalStateException("No s'ha pogut carregar el driver JDBC.", ex);
        } catch (IOException ex) {
            throw new IllegalStateException("No s'ha pogut llegir el fitxer de configuració.", ex);
        }
    }

    @Override
    public void addXollo(Xollo xollo) {
        String sql = "INSERT INTO xollos (codi, numeroUnitats, numeroReserves, titol, descripcio) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = dbConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            Integer numeroReserves = xollo.getNumeroReserves();
            if (numeroReserves == null) {
                numeroReserves = 0;
            }

            statement.setString(1, xollo.getCodi());
            statement.setInt(2, xollo.getNumeroUnitats());
            statement.setInt(3, numeroReserves);
            statement.setString(4, xollo.getTitol());
            statement.setString(5, xollo.getDescripcio());
            statement.executeUpdate();

        } catch (SQLException ex) {
            throw new IllegalArgumentException("No s'ha pogut afegir el xollo amb codi: " + xollo.getCodi(), ex);
        }
    }

    @Override
    public Xollo getXolloByCodi(String codi) {
        String sql = "SELECT codi, numeroUnitats, numeroReserves, titol, descripcio FROM xollos WHERE codi = ?";

        try (Connection connection = dbConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, codi);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToXollo(resultSet);
                }
            }

        } catch (SQLException ex) {
            throw new IllegalStateException("Error en consultar el xollo amb codi: " + codi, ex);
        }

        throw new IllegalArgumentException("No s'ha trobat el xollo amb el codi: " + codi);
    }

    @Override
    public void updateXollo(Xollo xollo) {
        String sql = "UPDATE xollos SET numeroUnitats = ?, numeroReserves = ?, titol = ?, descripcio = ? WHERE codi = ?";

        try (Connection connection = dbConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, xollo.getNumeroUnitats());
            statement.setInt(2, xollo.getNumeroReserves());
            statement.setString(3, xollo.getTitol());
            statement.setString(4, xollo.getDescripcio());
            statement.setString(5, xollo.getCodi());

            int rows = statement.executeUpdate();
            if (rows == 0) {
                throw new IllegalArgumentException("No s'ha trobat el xollo amb el codi: " + xollo.getCodi());
            }

        } catch (SQLException ex) {
            throw new IllegalStateException("No s'ha pogut actualitzar el xollo amb codi: " + xollo.getCodi(), ex);
        }
    }

    @Override
    public List<Xollo> getAllXollos() {
        String sql = "SELECT codi, numeroUnitats, numeroReserves, titol, descripcio FROM xollos ORDER BY codi";
        List<Xollo> xollos = new ArrayList<Xollo>();

        try (Connection connection = dbConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                xollos.add(mapResultSetToXollo(resultSet));
            }

            return xollos;
        } catch (SQLException ex) {
            throw new IllegalStateException("No s'han pogut recuperar els xollos.", ex);
        }
    }

    private Xollo mapResultSetToXollo(ResultSet resultSet) throws SQLException {
        Xollo xollo = new Xollo();
        xollo.setCodi(resultSet.getString("codi"));
        xollo.setNumeroUnitats(resultSet.getInt("numeroUnitats"));
        xollo.setNumeroReserves(resultSet.getInt("numeroReserves"));
        xollo.setTitol(resultSet.getString("titol"));
        xollo.setDescripcio(resultSet.getString("descripcio"));
        return xollo;
    }
}