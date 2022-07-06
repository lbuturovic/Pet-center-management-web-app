/*package ba.unsa.etf.pnwt.petcenter.Controller;

import ba.unsa.etf.pnwt.petcenter.Model.Status;
import ba.unsa.etf.pnwt.petcenter.Repository.StatusRepository;
import ba.unsa.etf.pnwt.petcenter.Service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.validation.Valid;
import java.util.List;

@RestController
@EnableSwagger2
@RequestMapping(path = "/api")
public class StatusController {

    @Autowired
    private StatusService statusService;

    @GetMapping("/status") //done
    public ResponseEntity<List<Status>> getAllStatuses(@RequestParam(required = false) String name) {
        List<Status> statuses = statusService.getStatuses(name);
        if (statuses.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(statuses, HttpStatus.OK);
    }
    @GetMapping("/status/{id}") //done
    public ResponseEntity<Status> getStatusById( @PathVariable("id") Integer id) {
        return new ResponseEntity<>(statusService.getStatus(id), HttpStatus.OK);
    }
    @PostMapping("/status") //done
    public ResponseEntity<Status> createStatus(@Valid @RequestBody Status status) {
        return new ResponseEntity<>(statusService.addStatus(status), HttpStatus.CREATED);
    }
    @PutMapping("/status/{id}") //done
    public ResponseEntity<Status> updateStatus(@PathVariable("id") Integer id, @Valid @RequestBody Status status) {
        return new ResponseEntity<>(statusService.updateStatus(id, status), HttpStatus.OK);
    }
    @DeleteMapping("/status/{id}") //done
    public ResponseEntity<HttpStatus> deleteStatus(@PathVariable("id") Integer id) {
        statusService.deleteStatus(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/status") //done
    public ResponseEntity<HttpStatus> deleteAllStatus() {
        statusService.deleteStatus(null);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /*@ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}*/
