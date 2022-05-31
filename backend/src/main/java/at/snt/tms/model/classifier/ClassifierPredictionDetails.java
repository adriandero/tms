package at.snt.tms.model.classifier;

import at.snt.tms.model.status.InternalStatus;

/**
 * Class {@code ClassifierPredictionDetails.java}
 * <p>
 * Response from classification microservice.
 *
 * @author Dominik Fluch
 */
public class ClassifierPredictionDetails {
    private long tenderId;
    private InternalStatus.Static label;
    private short confidence;

    protected ClassifierPredictionDetails() {}

    public long getTenderId() {
        return tenderId;
    }

    protected void setTenderId(long tenderId) {
        this.tenderId = tenderId;
    }

    public InternalStatus.Static getLabel() {
        return label;
    }

    protected void setLabel(InternalStatus.Static label) {
        this.label = label;
    }

    public short getConfidence() {
        return confidence;
    }

    protected void setConfidence(short confidence) {
        this.confidence = confidence;
    }
}
