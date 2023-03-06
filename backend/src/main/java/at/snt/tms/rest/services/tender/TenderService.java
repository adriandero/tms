package at.snt.tms.rest.services.tender;

import at.snt.tms.model.dtos.response.MessageResponse;
import at.snt.tms.model.operator.Permission;
import at.snt.tms.classification.ClassifierBridge;
import at.snt.tms.model.operator.User;
import at.snt.tms.model.status.AssignedIntStatus;
import at.snt.tms.model.status.InternalStatus;
import at.snt.tms.model.tender.Assignment;
import at.snt.tms.model.tender.Tender;
import at.snt.tms.repositories.status.InternalStatusRepository;
import at.snt.tms.repositories.tender.AssignmentRepository;
import at.snt.tms.repositories.tender.TenderRepository;
import at.snt.tms.rest.services.GenericCrudRepoService;
import org.apache.camel.Body;
import org.apache.camel.Header;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

//@Controller
@Service  // works even without @Service interestingly?
public class TenderService extends GenericCrudRepoService<Tender, Long> {

    private AssignmentRepository assignmentRepository;
    private TenderRepository tenders;
    private InternalStatusRepository internalStatus;
    private ClassifierBridge classifierBridge;

    @Autowired
    public TenderService(TenderRepository tenders, AssignmentRepository assignmentRepository, InternalStatusRepository internalStatus, ClassifierBridge classifierBridge){
        super(tenders, Tender.class);
        this.assignmentRepository = assignmentRepository;
        this.tenders = tenders;
        this.internalStatus = internalStatus;
        this.classifierBridge = classifierBridge;
    }

    public ResponseEntity<?> updateInternal(@Header(value = "id") long tender, @Body InternalStatus status) {
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            final Tender foundTender = this.findById(tender).getBody();

            if(foundTender == null) return ResponseEntity.badRequest().body("Given tender id is unknown.");

            InternalStatus found = this.internalStatus.findByLabel(status.getLabel());

            if(found == null) found = status;
            else {
                found.setTerminatesTender(status.getTerminatesTender()); // Apply requested changes expect transitions.
            }

            for(InternalStatus.Static staticStatus : InternalStatus.Static.values()) {
                if(staticStatus.getInner().equals(status)) {
                    this.classifierBridge.manualClassification(foundTender, staticStatus);
                    break;
                }
            }

            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            foundTender.getAssignedIntStatus().add(new AssignedIntStatus(found, foundTender, user, Timestamp.from(Instant.now())));
            // Set & (Update | Create internal status)
            foundTender.setLatestIntStatus(found);

            this.tenders.save(foundTender);

            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().body(new MessageResponse("Couldn't associate user."));
        }

    }

    public ResponseEntity<? extends Iterable<Tender>> findFiltered(@Body FilterConfiguration config) {
        // TODO: Paging - So that we do not download everything every time.
        final List<Tender> filtered = new ArrayList<>();

        for(Tender tender : Objects.requireNonNull(super.findAll().getBody())) {
            List<Assignment> assignments = this.assignmentRepository.findAssignmentsByTenderId(tender.getId());

            if((config.getCompanies() == null || config.getCompanies().length == 0 || Arrays.stream(config.getCompanies()).anyMatch(item -> tender.getCompany().getName().contains(item))) &&
                    (config.getPlatforms() == null || config.getPlatforms().length == 0 || Arrays.stream(config.getPlatforms()).anyMatch(item -> tender.getPlatform().getLink().contains(item))) &&
                    (config.getTitles() == null || config.getTitles().length == 0 || Arrays.stream(config.getTitles()).anyMatch(item -> tender.getName().contains(item))) &&
                    (config.getIntStatus() == null || config.getIntStatus().length == 0 || Arrays.stream(config.getIntStatus()).anyMatch(expected -> tender.getLatestIntStatus().getLabel().contains(expected) || tender.getAssignedIntStatus().stream().anyMatch(assignment -> assignment.getInternalStatus().getLabel().contains(expected)))) &&
                    (config.getExtStatus() == null || config.getExtStatus().length == 0 || Arrays.stream(config.getExtStatus()).anyMatch(expected -> tender.getUpdates().stream().anyMatch(update -> update.getExternalStatus().getLabel().contains(expected)))) &&
                    (config.getFiles() == null || config.getFiles().length == 0 || Arrays.stream(config.getFiles()).anyMatch(file -> tender.getUpdates().stream().anyMatch(update -> update.getAttachedFiles().stream().anyMatch(attachment -> attachment.getFileName().contains(file))))) &&
                    (config.getUptDetails() == null || config.getUptDetails().length == 0 || Arrays.stream(config.getUptDetails()).anyMatch(details -> tender.getUpdates().stream().anyMatch(update -> update.getDetails().contains(details)))) &&
                    (config.getUsers() == null || config.getUsers().length == 0 || Arrays.stream(config.getUsers()).anyMatch(u -> {
                        final String user = u.toLowerCase();

                        final Set<User> users = assignments.stream().map(assignment -> assignment.getUser()).collect(Collectors.toSet());

                        users.addAll(tender.getAssignedIntStatus().stream().map(status -> status.getUser()).collect(Collectors.toSet()));

                        return users.stream().anyMatch(found -> found != null && (found.getLastName() != null && found.getLastName().toLowerCase().contains(user) || found.getFirstName() != null && found.getFirstName().toLowerCase().contains(user) || found.getMail() != null && found.getMail().toLowerCase().contains(user)));
                    }))) {
                filtered.add(tender);
            }

        }

        if(config.getSortBy() != null) {
            switch(config.getSortBy()) {
                case ALPHABETICAL_ASC:
                    filtered.sort(Comparator.comparing((key) -> key.getName(), String::compareTo));
                    break;
                case ALPHABETICAL_DESC:
                    filtered.sort(Comparator.comparing((key) -> key.getName(), String::compareTo));
                    Collections.reverse(filtered);
                    break;
                case LATEST:
                    filtered.sort(Comparator.comparing((key) -> key.getLatestUpdate().getValidFrom()));
                    break;
                case OLDEST:
                    filtered.sort(Comparator.comparing((key) -> key.getLatestUpdate().getValidFrom()));
                    Collections.reverse(filtered);
                    break;
                case DEFAULT:
            }
        }

        return new ResponseEntity<>(filtered, HttpStatus.OK);
    }
}