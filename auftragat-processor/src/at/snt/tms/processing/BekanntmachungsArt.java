package at.snt.tms.processing;

import java.util.regex.Pattern;

/**
 * Class {@code BekanntmachungsArt.java}
 * <p>
 * Enum modelling the different types of BekanntmachungsArt.
 *
 * @author Dominik Fluch
 */
public enum BekanntmachungsArt {
    ;

    private final Pattern regex;

    BekanntmachungsArt(Pattern regex) { this.regex = regex; }

    public Pattern getRegex() {
        return regex;
    }


}
