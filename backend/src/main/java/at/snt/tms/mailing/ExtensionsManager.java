package at.snt.tms.mailing;

import com.google.common.reflect.ClassPath;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.HashMap;
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

    @Value("${tms.mail.receive.url}")
    private String extensionsFolder;

    private final Map<ClassLoader, MailHandler[]> loaded;

    public ExtensionsManager() {
        this.loaded = new HashMap<>();
    }

    /**
     * Loads the extension stored at the given url.
     * @param url  The file containing the extension code.
     */
    public void load(URL url) throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        final ClassLoader loader = new URLClassLoader(new URL[]{url});
        final String[] handlers = new String(loader.getResourceAsStream("extension.info").readAllBytes()).split("\n");

        final MailHandler[] mailHandlers = new MailHandler[handlers.length];

        for(int i = 0; i < mailHandlers.length; i++) {
            final Class<?> clasz = loader.loadClass(handlers[i]);

            if(clasz.isAssignableFrom(MailHandler.class)) {
                mailHandlers[i] = (MailHandler) clasz.getConstructor().newInstance();
            } else throw new InstantiationException("Class \"" + clasz.getName() + "\" is not subtype of \"" + MailHandler.class.getName() + "\".");
        }

        this.loaded.put(loader, mailHandlers);
    }

    /**
     * Unloads the extension associated with the given {@link ClassLoader}.
     * @param extension
     */
    public void unload(ClassLoader extension) throws IOException {
        for(MailHandler handler : this.getLoaded().remove(extension)) handler.onUnload();
        if(extension instanceof Closeable) ((Closeable) extension).close();
    }

    public Map<ClassLoader, MailHandler[]> getLoaded() {
        return loaded;
    }

    /**
     * @return the directory that contains all extensions.
     */
    public File getExtensionsFolder() {
        return new File(this.extensionsFolder);
    }
}
