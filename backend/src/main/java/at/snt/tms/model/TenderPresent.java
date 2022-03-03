package at.snt.tms.model;

import at.snt.tms.model.status.InternalStatus;

import javax.persistence.*;

/**
 * Class {@code TenderPresent}
 *
 * @author Oliver Sommer
 */
@Entity
/*@PrimaryKeyJoinColumns({
        @PrimaryKeyJoinColumn(name = "tp_timestamp", foreignKey = @ForeignKey(name = "t_timestamp")),
        @PrimaryKeyJoinColumn(name = "tp_document_nr", foreignKey = @ForeignKey(name = "t_document_nr")),
        @PrimaryKeyJoinColumn(name = "tp_platform", foreignKey = @ForeignKey(name = "t_platform"))
})*/
@Table(name = "tp_tender_present", schema = "tms_db")
public class TenderPresent extends Tender {
    private static final long serialVersionUID = 7066809793552860575L;

    /*@Embedded
    private InternalStatus externalInternalStatus;*/
}
