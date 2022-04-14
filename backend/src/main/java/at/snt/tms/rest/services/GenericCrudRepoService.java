package at.snt.tms.rest.services;

import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;


/**
 * Class {@code GenericCrudRepoService}
 *
 * @author Maximilian Wolf
 */
// todo ?: not possible with global @ExceptionHandler @ControllerAdvice
// https://www.baeldung.com/spring-response-entity

//@Controller
public class GenericCrudRepoService<T> {
    final CrudRepository<T, Long> repository;
    final Class<T> repoClass;

    public GenericCrudRepoService(CrudRepository<T, Long> repository, Class<T> repoClass) {
        this.repository = repository;
        this.repoClass = repoClass;
    }

    public ResponseEntity<?> findAll(){
        return ResponseEntity.ok().body(repository.findAll());
    }

    public ResponseEntity<?> findById(Long id) {
        Optional<T> entity = repository.findById(id);
        if (entity.isEmpty())
            return new ResponseEntity<>("Could not find " + entityNameId(id), HttpStatus.NOT_FOUND);
        return ResponseEntity.ok().body(entity.get());
    }

    public ResponseEntity<?> delete(Long id) {
        T entity = repoClass.cast(findById(id).getBody());
        repository.delete(entity);  //'Argument 'entity' might be null': IntelliJ or I too dumb ?
        return ResponseEntity.ok().body("Successfully deleted " + entityNameId(id));
    }

    // todo ErrorMsg object ?
    private String entityNameId(Long id){
        return String.format("%s with Id=%d", repoClass.getSimpleName(), id);
    }


}
