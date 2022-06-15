package at.snt.tms.rest.services.tender;

import at.snt.tms.model.tender.Tender;
import at.snt.tms.repositories.tender.TenderRepository;
import at.snt.tms.rest.services.GenericCrudRepoService;
import org.apache.camel.Body;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

//@Controller
@Service  // works even without @Service interestingly?
public class TenderService extends GenericCrudRepoService<Tender, Long> {

    @Autowired
    public TenderService(TenderRepository tenders){
        super(tenders, Tender.class);
    }

    public ResponseEntity<? extends Iterable<Tender>> findFiltered(@Body FilterConfiguration config) {
        // TODO: Paging - So that we do not download everything every time.
        final List<Tender> filtered = new ArrayList<>();

        for(Tender tender : Objects.requireNonNull(super.findAll().getBody())) {
            if((config.getCompanies() == null || config.getCompanies().length == 0 || Arrays.stream(config.getCompanies()).anyMatch(item -> tender.getCompany().getName().contains(item))) &&
                    (config.getPlatforms() == null || config.getPlatforms().length == 0 || Arrays.stream(config.getPlatforms()).anyMatch(item -> tender.getPlatform().getLink().contains(item))) &&
                    (config.getTitles() == null || config.getTitles().length == 0 || Arrays.stream(config.getTitles()).anyMatch(item -> tender.getName().contains(item))) &&
                    (config.getIntStatus() == null || config.getIntStatus().length == 0 || Arrays.stream(config.getIntStatus()).anyMatch(expected -> tender.getAssignedIntStatus().stream().anyMatch(assignment -> assignment.getInternalStatus().getLabel().contains(expected)))) &&
                    (config.getExtStatus() == null || config.getExtStatus().length == 0 || Arrays.stream(config.getExtStatus()).anyMatch(expected -> tender.getUpdates().stream().anyMatch(update -> update.getExternalStatus().getLabel().contains(expected)))) &&
                    (config.getFiles() == null || config.getFiles().length == 0 || Arrays.stream(config.getFiles()).anyMatch(file -> tender.getUpdates().stream().anyMatch(update -> update.getAttachedFiles().stream().anyMatch(attachment -> attachment.getFileName().contains(file))))) &&
                    (config.getUptDetails() == null || config.getUptDetails().length == 0 || Arrays.stream(config.getUptDetails()).anyMatch(details -> tender.getUpdates().stream().anyMatch(update -> update.getDetails().contains(details)))) &&
                    (config.getUsers() == null || config.getUsers().length == 0 || Arrays.stream(config.getUsers()).anyMatch(user -> tender.getAssignedIntStatus().stream().anyMatch(status -> status.getUser().getMail().contains(user) || status.getUser().getUsername().contains(user) || status.getUser().getFirstName().contains(user) || status.getUser().getLastName().contains(user))))
            ) {
                filtered.add(tender);
            }

        }

        return new ResponseEntity<>(filtered, HttpStatus.OK);
    }
}