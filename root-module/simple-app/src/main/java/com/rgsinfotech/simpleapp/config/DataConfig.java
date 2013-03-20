package com.rgsinfotech.simpleapp.config;

import java.util.Properties;

import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.dao.support.PersistenceExceptionTranslator;
import org.springframework.orm.hibernate3.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.jolbox.bonecp.BoneCPDataSource;

@Configuration
@EnableTransactionManagement
public class DataConfig {
	
    private static final String PROP_DATABASE_DRIVER = "db.driver";
    private static final String PROP_DATABASE_PASSWORD = "db.password";
    private static final String PROP_DATABASE_URL = "db.url";
    private static final String PROP_DATABASE_USERNAME = "db.username";

    private static final String PROP_HIBERNATE_DIALECT = "hibernate.dialect";
    private static final String PROP_HIBERNATE_FORMAT_SQL = "hibernate.format_sql";
    private static final String PROP_HIBERNATE_HBM2DDL_AUTO = "hibernate.hbm2ddl.auto";
    private static final String PROP_HIBERNATE_NAMING_STRATEGY = "hibernate.ejb.naming_strategy";
    private static final String PROP_HIBERNATE_SHOW_SQL = "hibernate.show_sql";
    private static final String PROP_ENTITYMANAGER_PACKAGES_TO_SCAN = "entitymanager.packages_to_scan";
    
	@Resource
	private Environment environment;
	
    @Bean
    public EntityManagerFactory entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();

        entityManagerFactoryBean.setDataSource(dataSource());
        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManagerFactoryBean.setPackagesToScan(environment.getRequiredProperty(PROP_ENTITYMANAGER_PACKAGES_TO_SCAN));
        

        entityManagerFactoryBean.setJpaProperties(hibernateProperties());

        entityManagerFactoryBean.afterPropertiesSet();
      
        return entityManagerFactoryBean.getObject();
    }    
    
    @Bean
    public JpaTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager(entityManagerFactory());

        return transactionManager;
    }

    @Bean
    public Properties hibernateProperties() {
        Properties hibernateJpaProperties = new Properties();
        hibernateJpaProperties.put(PROP_HIBERNATE_DIALECT, environment.getRequiredProperty(PROP_HIBERNATE_DIALECT));
        hibernateJpaProperties.put(PROP_HIBERNATE_HBM2DDL_AUTO, environment.getRequiredProperty(PROP_HIBERNATE_HBM2DDL_AUTO));
        hibernateJpaProperties.put(PROP_HIBERNATE_NAMING_STRATEGY, environment.getRequiredProperty(PROP_HIBERNATE_NAMING_STRATEGY));
        hibernateJpaProperties.put(PROP_HIBERNATE_SHOW_SQL, environment.getRequiredProperty(PROP_HIBERNATE_SHOW_SQL));
        hibernateJpaProperties.put(PROP_HIBERNATE_FORMAT_SQL, environment.getRequiredProperty(PROP_HIBERNATE_FORMAT_SQL));
        return hibernateJpaProperties;
    }
    
    @Bean
    public DataSource dataSource() {
        final BoneCPDataSource dataSource = new BoneCPDataSource();

        dataSource.setDriverClass(environment.getRequiredProperty(PROP_DATABASE_DRIVER));
        dataSource.setJdbcUrl(environment.getRequiredProperty(PROP_DATABASE_URL));
        dataSource.setUsername(environment.getRequiredProperty(PROP_DATABASE_USERNAME));
        dataSource.setPassword(environment.getRequiredProperty(PROP_DATABASE_PASSWORD));

        return dataSource;
    }
    
    @Bean
    public PersistenceExceptionTranslator hibernateExceptionTranslator() {
    	return new HibernateExceptionTranslator();
    }
    
}
