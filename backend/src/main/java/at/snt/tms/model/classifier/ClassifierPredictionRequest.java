package at.snt.tms.model.classifier;

import java.util.Objects;

/**
 * Class {@code ClassifierPredictionRequest.java}
 * <p>
 * Dto representing prediction request.
 *
 * @author Dominik Fluch
 */
public class ClassifierPredictionRequest {

    private final long tenderId;
    private final String text;

    public ClassifierPredictionRequest(long tenderId, String text) {
        this.tenderId = tenderId;
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClassifierPredictionRequest that = (ClassifierPredictionRequest) o;
        return tenderId == that.tenderId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(tenderId);
    }

    public long getTenderId() {
        return tenderId;
    }

    public String getText() {
        return text;
    }

}
