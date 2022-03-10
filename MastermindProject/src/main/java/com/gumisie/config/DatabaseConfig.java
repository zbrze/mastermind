package com.gumisie.config;

import com.gumisie.persistence.hibernate.UserDao;
import com.gumisie.persistence.interfaces.IUserDao;
import org.hibernate.SessionFactory;
import org.hibernate.service.spi.ServiceException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * A class holding the Spring DI configuration for classes related to persistence
 */
@Configuration
@ComponentScan(basePackages = "com.gumisie")
public class DatabaseConfig {

    @Bean
    public SessionFactory sessionFactory(){
        org.hibernate.cfg.Configuration config = new org.hibernate.cfg.Configuration();
        config.configure();
        try {
            SessionFactory sessionFactory = config.buildSessionFactory();
            sessionFactory.openSession();
            return sessionFactory;
        } catch (ServiceException e){
            System.err.println("Database not connected!");
            System.err.println("\nFor the app to work there must be a MySql database named 'mastermind' running on localhost:3306");
            System.err.println("Please create the specified database and try again\n");
            System.exit(1);
            throw e;
        }
    }


}
