package cat.xtec.ioc.repository.impl;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

public class DBConnection {

    // Per evitar reinicialitzacions múltiples de la BD en memòria H2 durant les proves.
    private static final Set<String> BASES_INICIALITZADES = new HashSet<String>();

    private final String urlBd;
    private final String usuariBd;
    private final String contrasenyaBd;
    private final String urlInicialitzacio;
    private final String urlExecucio;

    /**
     * Llegeix la configuració de connexió i carrega el driver JDBC.
     */
    public DBConnection(String fitxerConnexio) throws ClassNotFoundException, IOException {
        Properties propietats = carregaPropietats(fitxerConnexio);// Llegeix propietats obligatòries i valida que existeixin.

        String classeDriver = obtePropietatObligatoria(propietats, "DB_DRIVER_CLASS", fitxerConnexio);//
        this.urlBd = obtePropietatObligatoria(propietats, "DB_URL", fitxerConnexio);
        this.usuariBd = obtePropietatObligatoria(propietats, "DB_USERNAME", fitxerConnexio);
        this.contrasenyaBd = obtePropietatObligatoria(propietats, "DB_PASSWORD", fitxerConnexio);

        this.urlInicialitzacio = asseguraDbCloseDelayPerH2Memoria(urlBd); // Assegura que les BD H2 en memòria no es tanquin quan es tanqui l'última connexió.
        this.urlExecucio = eliminaClausulaInit(urlInicialitzacio);// URL sense INIT per a connexions posteriors a la primera.

        Class.forName(classeDriver);// Carrega el driver JDBC (pot llençar ClassNotFoundException).
    }

    /**
     * Retorna una connexió JDBC.
     *
     * - Primera connexió de la BD: usa URL amb INIT (crea/esquema inicial).<br>
     * - Següents connexions: usa URL sense INIT (no reinicialitza dades).
     */
    public Connection getConnexio() throws SQLException {
        synchronized (BASES_INICIALITZADES) {
            if (!BASES_INICIALITZADES.contains(urlExecucio)) {
                BASES_INICIALITZADES.add(urlExecucio);
                return DriverManager.getConnection(urlInicialitzacio, usuariBd, contrasenyaBd);
            }
        }

        return DriverManager.getConnection(urlExecucio, usuariBd, contrasenyaBd);
    }

    
    private Properties carregaPropietats(String fitxerConnexio) throws IOException {// Carrega les propietats des del fitxer de configuració a classpath.
        Properties propietats = new Properties(); // Obté un InputStream per al fitxer de connexió des del classpath.

        /** Si el fitxer no existeix, getResourceAsStream retorna null.*/
        InputStream fluxEntrada = Thread.currentThread().getContextClassLoader().getResourceAsStream(fitxerConnexio);
        if (fluxEntrada == null) {
            throw new IllegalArgumentException("No s'ha trobat el fitxer de connexió: " + fitxerConnexio);
        }

        try (InputStream recurs = fluxEntrada) {
            propietats.load(recurs);
        }

        return propietats;
    }

    /** Valida que una propietat existeixi i la retorna. Si no existeix, llença una excepció. */
    private String obtePropietatObligatoria(Properties propietats, String clau, String fitxerConnexio) {
        String valor = propietats.getProperty(clau);
        if (valor == null) {
            throw new IllegalArgumentException("Falta la propietat '" + clau + "' a: " + fitxerConnexio);
        }
        return valor;
    }

    /**
     * Assegura que les BD H2 en memòria no es tanquin quan es tanqui l'última connexió, afegint DB_CLOSE_DELAY=-1 si no està present.
     */
    private String asseguraDbCloseDelayPerH2Memoria(String url) {
        if (url.startsWith("jdbc:h2:mem:") && !url.toUpperCase().contains("DB_CLOSE_DELAY=")) {
            if (url.endsWith(";")) {
                return url + "DB_CLOSE_DELAY=-1";
            }
            return url + ";DB_CLOSE_DELAY=-1";
        }
        return url;
    }
    /** Elimina la clausula INIT de la URL per a connexions posteriors a la primera, 
     * ja que H2 en memòria reinicialitza les dades cada cop que es fa una connexió amb INIT. */
    private String eliminaClausulaInit(String url) {
        return normalitzaUrl(url.replaceAll("(?i);INIT=[^;]*;?", ";"));
    }

    /** Normalitza la URL, eliminant clàusules duplicades o innecessàries. */
    private String normalitzaUrl(String url) {
        String urlNormalitzada = url.replaceAll(";+", ";");
        if (urlNormalitzada.endsWith(";")) {
            return urlNormalitzada.substring(0, urlNormalitzada.length() - 1);
        }
        return urlNormalitzada;
    }
}