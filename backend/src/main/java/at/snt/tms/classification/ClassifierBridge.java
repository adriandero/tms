package at.snt.tms.classification;

import at.snt.tms.mailing.MailOAuthenticator;
import at.snt.tms.model.classifier.ClassifierPredictionDetails;
import at.snt.tms.model.classifier.ClassifierPredictionRequest;
import at.snt.tms.model.classifier.ClassifierTrainNode;
import at.snt.tms.model.status.InternalStatus;
import at.snt.tms.model.tender.Tender;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

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
@Component
public class ClassifierBridge {

    private static final Logger LOGGER = LoggerFactory.getLogger(MailOAuthenticator.class);
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Value("${tms.classification.url}")
    private String url;

    @Value("${tms.classification.threshold}")
    private int threshold;

    private final Set<ClassifierTrainNode> currentNodes;

    public ClassifierBridge() {
        this.currentNodes = new HashSet<>(this.threshold);
    }

    /*
    @EventListener(ApplicationReadyEvent.class)
    public void test() {
        for(int i = 0; i < 102; i++) {
            final Tender tender = new Tender(i, "#123", null, "http://link.demo.at", "test", null, "seas", null, null);

            this.manualClassification(tender, i % 2 == 0 ? InternalStatus.Static.INTERESTING : InternalStatus.Static.IRRELEVANT);
        }

        try {
            final Tender tender = new Tender(1, "#123", null, "http://link.demo.at", "test", null, "seas", null, null);

            System.out.println("Prediction: " + this.predict(tender).get().getConfidence());
        } catch(Exception exception) {
            exception.printStackTrace();
        }
    }*/

    /**
     * Remembers the details of the manual classification.
     * @param tender
     * @param status
     */
    public synchronized void manualClassification(Tender tender, InternalStatus.Static status) {
        final ClassifierTrainNode node = new ClassifierTrainNode(tender.getId(), tender.getName() + tender.getDescription(), status);

        this.currentNodes.remove(node);
        this.currentNodes.add(node);

        if(this.currentNodes.size() >= this.threshold) this.triggerTrain();
    }

    /**
     * Trains the external service with the current {@link ClassifierTrainNode}s.
     */
    private synchronized void triggerTrain() {
        try {
            final HttpClient client = HttpClient.newHttpClient();
            final HttpRequest request = HttpRequest.newBuilder().header("Content-Type", "application/json").uri(new URI(this.url).resolve("/train")).POST(HttpRequest.BodyPublishers.ofString(ClassifierBridge.OBJECT_MAPPER.writeValueAsString(this.currentNodes))).build();

            final HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if(response.statusCode() != 200) throw new IllegalStateException("AI service returned code: " + response.statusCode());

            LOGGER.info("Trained successfully: " + response.body().replace("\n", " "));
        } catch (IOException | URISyntaxException | InterruptedException | IllegalStateException exception) {
            LOGGER.error("Failed to process classification data: " + exception.getMessage());
        } finally {
            this.currentNodes.clear(); // We don't want exception to loop.
        }
    }

    /**
     * Makes the service predict the probability of the tender being interesting ({@link InternalStatus.Static}).
     * @param tender
     * @return the AI services prediction.
     */
    public CompletableFuture<ClassifierPredictionDetails> predict(Tender tender) throws IOException, URISyntaxException {
        final HttpClient client = HttpClient.newHttpClient();
        final HttpRequest request = HttpRequest.newBuilder().header("Content-Type", "application/json").uri(new URI(this.url).resolve("/predict")).POST(HttpRequest.BodyPublishers.ofByteArray(ClassifierBridge.OBJECT_MAPPER
                .writeValueAsBytes(new ClassifierPredictionRequest(tender.getId(),
                        tender.getName() + tender.getDescription())))).build();

        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenApplyAsync(data -> {
            try {
                return ClassifierBridge.OBJECT_MAPPER.readValue(data.body(), ClassifierPredictionDetails.class);
            } catch (IOException e) {
                throw new IllegalStateException(e); // Will be caught in CompletableFuture.
            }
        });
    }

}
