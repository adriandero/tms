package at.snt.tms.model;

import javax.persistence.*;

/**
 * Class {@code TenderHistory}
 *
 * @author Oliver Sommer
 */
@Entity
/*@PrimaryKeyJoinColumns({
        @PrimaryKeyJoinColumn(name = "th_timestamp", foreignKey = @ForeignKey(name = "t_timestamp")),
        @PrimaryKeyJoinColumn(name = "th_document_nr", foreignKey = @ForeignKey(name = "t_document_nr")),
        @PrimaryKeyJoinColumn(name = "th_platform", foreignKey = @ForeignKey(name = "t_platform"))
})*/
@Table(name = "th_tender_history", schema = "tms_db")
public class TenderHistory extends Tender {
    private static final long serialVersionUID = -6596383593912158909L;


}
