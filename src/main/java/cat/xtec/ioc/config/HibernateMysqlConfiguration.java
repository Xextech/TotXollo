package cat.xtec.ioc.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

// Configuració de Spring per a l'aplicació, que inclou la configuració de Hibernate i la connexió a MySQL.
@Configuration
@EnableTransactionManagement
@PropertySource({"classpath:jdbc.properties", "classpath:hibernate.properties"})
@ComponentScan(basePackages = "cat.xtec.ioc.repository.impl") // Escaneja el paquet on es troben les implementacions del repositori
@Import(HibernateConfiguration.class)
public class HibernateMysqlConfiguration {

    // Propietats de connexió a la base de dades, inyectades des del fitxer jdbc.properties
    @Value("${jdbc.driverClassName}")
    private String driverClassName;

    @Value("${jdbc.url}")
    private String url;

    @Value("${jdbc.username}")
    private String username;

    @Value("${jdbc.password}")
    private String password;

    // Configura el DataSource per a la connexió a MySQL, utilitzant les propietats inyectades
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }
}
