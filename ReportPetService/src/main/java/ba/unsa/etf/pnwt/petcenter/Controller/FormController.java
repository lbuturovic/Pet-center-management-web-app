package ba.unsa.etf.pnwt.petcenter.Controller;

import ba.unsa.etf.pnwt.petcenter.Model.Form;
import ba.unsa.etf.pnwt.petcenter.Service.FormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@EnableSwagger2
@RequestMapping(path = "/api")
public class FormController {

    @Autowired
    private FormService formService;

    /*@GetMapping("/center/{centerId}/forms")
    public ResponseEntity<List<Report>> getAllFormsByCenter(@PathVariable(value = "centerId") Integer centerId) {
        List<Form> forms = formService.getAllFormsByCenterId(centerId);
        if (forms.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Form>(formService.getAllFormsByCenterId(centerId), HttpStatus.OK);
    }*/



    @GetMapping("/forms/{id}") //done
    public ResponseEntity<Form> getFormsById(@PathVariable(value = "id") UUID id) {
        return new ResponseEntity<>(formService.getFormsById(id), HttpStatus.OK);
    }

    @GetMapping("/report/{id}/forms") //done
    public ResponseEntity<List<Form>> getFormsByReportId(@PathVariable(value = "id") UUID id) {
        return new ResponseEntity<>(formService.getFormsByReportId(id), HttpStatus.OK);
    }

    @GetMapping("/forms")
    public ResponseEntity<List<Form>> getAllForms(@Valid @RequestParam(required = false) String type) {
        List<Form> forms = formService.getAllForms(type);
        if (forms.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(forms, HttpStatus.OK);
    }

    @PostMapping("/center/{centerId}/report/{reportId}/form")
    public ResponseEntity<Form> createForm(@PathVariable(value = "reportId") UUID reportId,
                                               @PathVariable(value = "centerId") Integer centerId,
                                               @RequestBody Form formRequest) {
        return new ResponseEntity<>(formService.createForm(reportId, centerId, formRequest), HttpStatus.CREATED);
    }

    @PutMapping("/forms/{id}")
    public ResponseEntity<Form> ChangeForm(@PathVariable(value = "formId") UUID formId,
                                           @RequestBody Form form) {
        return new ResponseEntity<>(formService.ChangeForm(formId, form), HttpStatus.OK);
    }

    @DeleteMapping("/forms/{id}")
    public ResponseEntity<HttpStatus> deleteForm(@PathVariable("id") UUID id) {
        formService.deleteForm(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/forms")
    public ResponseEntity<HttpStatus> deleteAllForms() {
        formService.deleteAllForms();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
