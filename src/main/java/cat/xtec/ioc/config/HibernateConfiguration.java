package cat.xtec.ioc.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration // Indica que aquesta classe és una classe de configuració de Spring.
@EnableTransactionManagement // Habilita el suport de transaccions declaratives amb anotacions.
@PropertySource("classpath:hibernate.properties") // Especifica el fitxer de propietats que conté la configuració de Hibernate.
public class HibernateConfiguration {

    // Injecció de propietats de configuració de Hibernate des del fitxer hibernate.properties.
    @Value("${hibernate.dialect}") 
    private String dialect;

    @Value("${hibernate.show_sql}")
    private String showSql;

    @Value("${hibernate.format_sql}")
    private String formatSql;

    // Injecció del DataSource configurat a DataSourceConfiguration.
    @Bean
    public LocalSessionFactoryBean sessionFactory(DataSource dataSource) {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setPackagesToScan("cat.xtec.ioc.domain");// Especifica el paquet on es troben les entitats
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
    }

    // Configura el gestor de transaccions de Hibernate, associant-lo amb la SessionFactory.
    @Bean
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory);
        return transactionManager;
    }

    //
    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", dialect);
        properties.put("hibernate.show_sql", showSql);
        properties.put("hibernate.format_sql", formatSql);
        return properties;
    }
}
