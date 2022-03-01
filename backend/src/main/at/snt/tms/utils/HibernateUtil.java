package at.snt.tms.utils;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static final SessionFactory sessionFactory;

    static {
        var file = HibernateUtil.class.getResource("hibernate.cfg.xml");
        sessionFactory = new Configuration().configure(file).buildSessionFactory();
    }

    public static SessionFactory getSessionFactory() throws HibernateException {
        return sessionFactory;
    }

    public static void shutdown() {
        sessionFactory.close();
    }
}