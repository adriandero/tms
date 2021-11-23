package at.snt.tms.processing;

import org.apache.camel.CamelContext;
import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.support.DefaultEndpoint;

public class MailProcessingEndpoint extends DefaultEndpoint {

    private final CamelContext camelContext;

    public MailProcessingEndpoint(CamelContext camelContext) {
        this.camelContext = camelContext;
    }

    @Override
    public Producer createProducer() throws Exception {
        return new TenderProducer(this);
    }

    @Override
    public Consumer createConsumer(Processor processor) throws Exception {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public CamelContext getCamelContext() {
        return super.getCamelContext();
    }

    @Override
    public String getEndpointUri() {
        return "mail-processor";
    }
}
