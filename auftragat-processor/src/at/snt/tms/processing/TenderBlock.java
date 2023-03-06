package at.snt.tms.processing;

/**
 * Class {@code null.java}
 * <p>
 * A tender block in an auftragat mail.
 *
 * @author Dominik Fluch
 */
public class TenderBlock {

    private final String title;
    private final BekanntmachungsArt bekanntmachungsArt;
    private final String description;
    private final String auftraggeber;

    public TenderBlock(String title, BekanntmachungsArt bekanntmachungsArt, String description, String auftraggeber) {
        this.title = title;
        this.bekanntmachungsArt = bekanntmachungsArt;
        this.description = description;
        this.auftraggeber = auftraggeber;
    }

    public String getTitle() {
        return title;
    }

    public BekanntmachungsArt getBekanntmachungsArt() {
        return bekanntmachungsArt;
    }

    public String getDescription() {
        return description;
    }

    public String getAuftraggeber() {
        return auftraggeber;
    }

}
