package at.snt.tms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

/**
 * Class {@code TenderManager.java}
 * <p>
 * Main class of project used to bootstrap all components.
 *
 * @author Dominik Fluch
 */
@SpringBootApplication
@ComponentScan(basePackages="at.snt.tms")
public class TenderManager extends SpringBootServletInitializer {

    /**
     * Runs the tms backend.
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(TenderManager.class, args);
    }

}
