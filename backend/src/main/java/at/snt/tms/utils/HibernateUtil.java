package at.snt.tms.utils;

import at.snt.tms.model.operator.Role;
import at.snt.tms.model.operator.User;
import at.snt.tms.model.status.AssignedIntStatus;
import at.snt.tms.model.status.ExternalStatus;
import at.snt.tms.model.status.InternalStatus;
import at.snt.tms.model.tender.*;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Properties;

public class HibernateUtil {
    private static final SessionFactory sessionFactory;

    private static final String HIBERNATE_DRIVER_CLASS = "hibernate.connection.driver_class";
    private static final String HIBERNATE_URL = "hibernate.connection.url";
    private static final String HIBERNATE_USER = "hibernate.connection.username";
    private static final String HIBERNATE_PASSWORD = "hibernate.connection.password";

    private static final String PROPERTY_DB_DRIVER = "db_driver";
    private static final String PROPERTY_DB_URL = "db_url";
    private static final String PROPERTY_DB_USER = "db_user";
    private static final String PROPERTY_DB_PASSWORD = "db_password";

    static {
        Properties prop = System.getProperties();
        //prop.list(System.out);
        Properties hibernate_prop = new Properties();

        System.out.println(prop.getProperty(PROPERTY_DB_DRIVER));
        System.out.println(prop.getProperty(PROPERTY_DB_URL));
        System.out.println(prop.getProperty(PROPERTY_DB_USER));
        System.out.println(prop.getProperty(PROPERTY_DB_PASSWORD));

        if(!prop.containsKey(PROPERTY_DB_DRIVER) || !prop.containsKey(PROPERTY_DB_URL) || !prop.containsKey(PROPERTY_DB_USER) || !prop.containsKey(PROPERTY_DB_PASSWORD)) {
            throw new IllegalArgumentException("Parameters for database not provided.");
        }

        switch(prop.getProperty(PROPERTY_DB_DRIVER)) {
            case "MySQL":
                hibernate_prop.put(HIBERNATE_DRIVER_CLASS, com.mysql.cj.jdbc.Driver.class.getName());
                break;
            case "Oracle":
                throw new UnsupportedOperationException("OracleDB currently not supported.");
            default:
                throw new IllegalArgumentException("Unknown DB.");
        }
        hibernate_prop.put(HIBERNATE_URL, prop.get(PROPERTY_DB_URL));
        hibernate_prop.put(HIBERNATE_USER, prop.get(PROPERTY_DB_USER));
        hibernate_prop.put(HIBERNATE_PASSWORD, prop.get(PROPERTY_DB_PASSWORD));

        hibernate_prop.put("hibernate.show_sql", true);
        hibernate_prop.put("hibernate.hbm2ddl.auto", "update");

        Configuration config = new Configuration();
        //var file = HibernateUtil.class.getResource("hibernate.cfg.xml");
        //sessionFactory = config.configure(file).buildSessionFactory();
        config.setProperties(hibernate_prop);
        config.addAnnotatedClass(Tender.class);
        config.addAnnotatedClass(TenderUpdate.class);
        config.addAnnotatedClass(Attachment.class);
        config.addAnnotatedClass(Platform.class);
        config.addAnnotatedClass(Company.class);
        config.addAnnotatedClass(User.class);
        config.addAnnotatedClass(Role.class);
        config.addAnnotatedClass(InternalStatus.class);
        config.addAnnotatedClass(AssignedIntStatus.class);
        config.addAnnotatedClass(ExternalStatus.class);
        config.addAnnotatedClass(Assignment.class);
        config.addAnnotatedClass(Task.class);
        sessionFactory = config.buildSessionFactory();
    }

    public static SessionFactory getSessionFactory() throws HibernateException {
        return sessionFactory;
    }

    public static void shutdown() {
        sessionFactory.close();
    }
}