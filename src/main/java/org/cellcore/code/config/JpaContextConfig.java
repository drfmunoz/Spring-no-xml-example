package org.cellcore.code.config;

import org.apache.commons.dbcp.BasicDataSource;
import org.cellcore.code.dao.DaoMarker;
import org.cellcore.code.model.AbstractJPAEntity;
import org.hibernate.ejb.HibernatePersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

/**
 * Spring JPA configuration (backed with hibernate 4)
 */
@Configuration
/**
 * enable transaction management using the transaction manager defined here
 */
@EnableTransactionManagement
/**
 * continue to use property files
 */
@PropertySource("classpath:appConfig.properties")
/**
 * use a placeholder marker instead of a string to define the packages to scan for components
 *
 */
@ComponentScan(
        basePackageClasses = {DaoMarker.class}
)
public class JpaContextConfig {

    @Autowired
    Environment environment;

    /**
     * database configuration settings
     * @return
     */
    @Bean
    public DataSource dataSource(){
        /**
         * create a basic database connection
         */
        final BasicDataSource ds=new BasicDataSource();
        ds.setDriverClassName(environment.getProperty("db.driver"));
        ds.setUrl(environment.getProperty("db.url"));
        ds.setUsername(environment.getProperty("db.username"));
        ds.setPassword(environment.getProperty("db.password"));
        return ds;
    }


    @Bean
    public PlatformTransactionManager transactionManager(){
       JpaTransactionManager transactionManager = new JpaTransactionManager(entityManagerFactory());
		transactionManager.setDataSource(dataSource());
		transactionManager.setJpaDialect(new HibernateJpaDialect());
		return transactionManager;
    }

    @Bean
    public EntityManagerFactory entityManagerFactory(){
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean=new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource());
        /**
         * tell hibernate to either update, verify or create (create-drop) the database schema
         */
        entityManagerFactoryBean.getJpaPropertyMap().put("hibernate.hbm2ddl.auto", environment.getProperty("db.ddl"));

        /**
         * no string package dependency, instead use a placeholder.
         */
        entityManagerFactoryBean.setPackagesToScan(AbstractJPAEntity.class.getPackage().getName());

        /**
         * lines specific to hibernate
         */
        entityManagerFactoryBean.setPersistenceProvider(new HibernatePersistence());
        HibernateJpaVendorAdapter jpaVendorAdapter=new HibernateJpaVendorAdapter();
        jpaVendorAdapter.setDatabasePlatform(environment.getProperty("db.dialect"));
        jpaVendorAdapter.setShowSql(environment.getProperty("db.showSql",Boolean.class,false));


        entityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter);
        entityManagerFactoryBean.afterPropertiesSet();

        return entityManagerFactoryBean.getObject();
    }

}
