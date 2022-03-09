import at.snt.tms.utils.HibernateUtil;
import at.snt.tms.model.tender.Platform;
import at.snt.tms.model.tender.Tender;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.persistence.Query;

/**
 * Class {@code TestConnection}
 *
 * @author Oliver Sommer
 */
public class TestConnection {
    // VM options: -ea -Ddb_driver=MySQL -Ddb_url=jdbc:mysql://127.0.0.1:3306/tms_db -Ddb_user=root -Ddb_password=""

    @Test
    void connectToDatabase() {
        //https://stackabuse.com/guide-to-jpa-with-hibernate-basic-mapping/
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            Transaction transaction = session.beginTransaction();

            Query query = session.createQuery("delete from Tender where name = :name ");
            query.setParameter("name", "First Tender");
            query.executeUpdate();

            /*Platform p = new Platform();
            p.setLink("auftrag.at");*/
            Platform p = session.find(Platform.class, 1L);
            if(p == null) {
                p = new Platform();
                p.setLink("auftrag.at");
            }

            Tender tender = new Tender();
            tender.setName("First Tender");
            tender.setDocumentNumber("AUT-1");

            tender.setPlatform(p);
            tender.setLink(tender.getPlatform().getLink() + '/' + tender.getDocumentNumber());
            // https://stackoverflow.com/a/19994539
            session.persist(p);
            session.persist(tender);

            transaction.commit();
            session.clear();

            Tender foundTender = session.find(Tender.class, tender.getId());

            System.out.println("Initial tender:\t" + tender);
            System.out.println("Found in DB:\t" + foundTender);

            Assertions.assertEquals(tender, foundTender);
        }
        finally {
            HibernateUtil.shutdown();
        }
    }
}
