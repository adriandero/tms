package at.snt.tms.model.status;

import javax.persistence.*;

/**
 * Class {@code TenderInternalStatus}
 *
 * @author Oliver Sommer
 */
@Entity
@PrimaryKeyJoinColumns({
        /*@PrimaryKeyJoinColumn(name = "t_timestamp", foreignKey = @ForeignKey(name = "t_timestamp")),*/
        @PrimaryKeyJoinColumn(name = "t_document_nr", foreignKey = @ForeignKey(name = "t_document_nr")),
        @PrimaryKeyJoinColumn(name = "t_platform", foreignKey = @ForeignKey(name = "t_platform")),
        @PrimaryKeyJoinColumn(name = "p_id", foreignKey = @ForeignKey(name = ""))
})
@Table(name = "tender_int_status", schema = "tms_db")
public class TenderInternalStatus {
    // TODO joins (Tender.timestamp?), Tender.documentNumber, Tender.platform with InternalStatus
}
