package at.snt.tms.model.classifier;

import at.snt.tms.model.status.InternalStatus;

import java.util.Objects;

/**
 * Class {@code ClassifierTrainNode.java}
 * <p>
 * Class representing a single item in the request sent to the classification service.
 *
 * @author Dominik Fluch
 */
public class ClassifierTrainNode extends ClassifierPredictionRequest {

    private final InternalStatus.Static label;

    public ClassifierTrainNode(long tenderId, String text, InternalStatus.Static label) {
        super(tenderId, text);
        this.label = label;
    }

    public InternalStatus.Static getLabel() {
        return label;
    }

}
