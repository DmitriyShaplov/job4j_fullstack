package ru.shaplov.rmi.example.persistence;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

/**
 * @author shaplov
 * @since 25.09.2019
 */
public class HibernateUtil {
    private static final Logger LOGGER = LogManager.getLogger(HibernateUtil.class);

    private static SessionFactory sf;

    public static SessionFactory getSessionFactory() {
        if (sf == null) {
            final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                    .configure()
                    .build();
            try {
                sf = new MetadataSources(registry)
                        .buildMetadata().buildSessionFactory();
                LOGGER.info("Session factory initialized");
            } catch (Exception e) {
                StandardServiceRegistryBuilder.destroy(registry);
                LOGGER.error("Session factory couldn't initialize", e);
                throw e;
            }
        }
        return sf;
    }
}
