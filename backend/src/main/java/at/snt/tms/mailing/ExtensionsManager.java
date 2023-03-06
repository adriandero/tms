package at.snt.tms.mailing;

import at.snt.tms.classification.ClassifierBridge;
import at.snt.tms.rest.Database;
import com.google.common.reflect.ClassPath;
import org.apache.juli.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Function;

/**
 * Class {@code ExtensionsManager.java}
 * <p>
 * Class responsible for loading extensions.
 *
 * @author Dominik Fluch
 */
@Component
public class ExtensionsManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(MailOAuthenticator.class);

    @Value("${tms.mail.extensions}")
    private String extensionsFolder;

    private final Map<ClassLoader, MailHandler[]> loaded;

    @Autowired
    private Database database;

    @Autowired
    private ClassifierBridge classifierBridge;

    public ExtensionsManager() {
        this.loaded = new HashMap<>();
    }

    /**
     * Loads all extensions in the extensions folder.
     */
    @EventListener(ApplicationReadyEvent.class)
    public void loadDefault() {
        LOGGER.info("Loading extensions from: " + this.extensionsFolder);
        try {
            final Path extensionsFolder = Paths.get(this.extensionsFolder);

            if(!Files.exists(extensionsFolder)) Files.createDirectory(extensionsFolder);

            final long start = System.currentTimeMillis();
            final Iterator<Path> extensions = Files.list(extensionsFolder).iterator();

            while(extensions.hasNext()) {
                final Path extension = extensions.next();
                try {
                    this.load(extension.toUri().toURL());
                } catch(Exception exception) {
                    LOGGER.error("Failed loading extension in \"" + extension.getFileName() + "\".", exception);
                }
            }

            LOGGER.info("Done loading extensions: Took " + (System.currentTimeMillis() - start) + "ms");
        } catch(IOException exception) {
            LOGGER.error("Failed to load extensions.", exception);
        }
    }

    /**
     * Loads the extension stored at the given url.
     * @param url  The file containing the extension code.
     */
    public void load(URL url) throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        final ClassLoader loader = new URLClassLoader(new URL[]{url}, this.getClass().getClassLoader());
        final String[] handlers = new String(loader.getResourceAsStream("extension.info").readAllBytes()).split("\n");

        final MailHandler[] mailHandlers = new MailHandler[handlers.length];

        for(int i = 0; i < mailHandlers.length; i++) {
            final Class<?> clasz = loader.loadClass(handlers[i]);

            if(MailHandler.class.isAssignableFrom(clasz)) {
                (mailHandlers[i] = (MailHandler) clasz.getConstructor().newInstance()).onLoad(this);
            } else throw new InstantiationException("Class \"" + clasz.getName() + "\" is not subtype of \"" + MailHandler.class.getName() + "\".");
        }

        this.loaded.put(loader, mailHandlers);
    }

    /**
     * Unloads the extension associated with the given {@link ClassLoader}.
     * @param extension
     */
    public void unload(ClassLoader extension) throws IOException {
        for(MailHandler handler : this.getLoaded().remove(extension)) handler.onUnload(this);
        if(extension instanceof Closeable) ((Closeable) extension).close();
    }

    public Map<ClassLoader, MailHandler[]> getLoaded() {
        return loaded;
    }

    public Database getDatabase() {
        return database;
    }

    public ClassifierBridge getClassifierBridge() {
        return classifierBridge;
    }

}
