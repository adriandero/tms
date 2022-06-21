package at.snt.tms.rest.services;

import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;


/**
 * Class {@code GenericCrudRepoService}
 *
 * Generic Repository Service with {@code findAll}, {@code findById} and {@code delete} methods.
 *
 * @author Maximilian Wolf
 */
// todo ?: not possible with global @ExceptionHandler @ControllerAdvice
// https://www.baeldung.com/spring-response-entity

//@Controller
public class GenericCrudRepoService<T, ID> {
    final CrudRepository<T, ID> repository;
    final Class<T> repoClass;

    public GenericCrudRepoService(CrudRepository<T, ID> repository, Class<T> repoClass) {
        this.repository = repository;
        this.repoClass = repoClass;
    }

    public ResponseEntity<? extends Iterable<T>> findAll(){
        return ResponseEntity.ok().body(repository.findAll());
    }

    public ResponseEntity<T> findById(ID id) {
        Optional<T> entity = repository.findById(id);
        if (entity.isEmpty()) throw new IllegalArgumentException("Could not find " + entityNameId(id));
        return ResponseEntity.ok().body(entity.get());
    }

    public ResponseEntity<String> delete(ID id) {
        T entity = repoClass.cast(findById(id).getBody());
        if(entity == null) {
            return new ResponseEntity<>("Could not find " + entityNameId(id), HttpStatus.NOT_FOUND);
        }
        repository.delete(entity);
        return ResponseEntity.ok().body("Successfully deleted " + entityNameId(id));
    }

    // todo ErrorMsg object ?
    private String entityNameId(ID id){
        return String.format("%s with Id=%s", repoClass.getSimpleName(), id);
    }


}
