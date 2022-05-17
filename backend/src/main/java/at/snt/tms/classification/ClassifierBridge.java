package at.snt.tms.classification;

/**
 * Class {@code ClassifierBridge.java}
 * <p>
 * Class abstracting over the external AI tender classification service.
 *
 * System works like this:
 * - Tender classifications done by the user are collected here.
 * - After reaching a threshold of manual classifications the external service
 * is ordered to retrain, based on the classifactions.
 * -> More informations can be found in the docs.
 *
 * @author Dominik Fluch
 */
public class ClassifierBridge {


    public void train(Tender... tenders) {

    }

}
