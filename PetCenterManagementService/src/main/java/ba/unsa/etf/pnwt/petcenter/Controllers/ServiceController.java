package ba.unsa.etf.pnwt.petcenter.Controllers;

import ba.unsa.etf.pnwt.petcenter.Exception.NotFoundException;
import ba.unsa.etf.pnwt.petcenter.Models.Center;
import ba.unsa.etf.pnwt.petcenter.Models.Pet;
import ba.unsa.etf.pnwt.petcenter.Models.Service;
import ba.unsa.etf.pnwt.petcenter.Services.ServiceService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@EnableSwagger2
@RequestMapping(path = "/api")
public class ServiceController {

    @Autowired
    private ServiceService serviceService;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping(path="/services")
    public @ResponseBody Iterable<Service> getAllServices(){

        return serviceService.getServices();
    }

    @GetMapping(path = "/service/{id}")
    public Service getService(@PathVariable Integer id){

        return serviceService.getService(id);
    }

    @PutMapping(path = "/service/{id}")
    public ResponseEntity<String> updateService(@RequestBody  Service service, @PathVariable Integer id){
        return new ResponseEntity<>(serviceService.updateService(id, service), HttpStatus.OK);
    }

    @PostMapping(path = "/service")
    public ResponseEntity<String> addNewService(@RequestBody Service service) {
        return new ResponseEntity<>(serviceService.addService(service), HttpStatus.OK);
    }

    @DeleteMapping(value = "/service/{id}")
    public ResponseEntity<String> deleteService(@PathVariable Integer id) {
        return new ResponseEntity<>(serviceService.deleteService(id), HttpStatus.OK);
    }

    @GetMapping(path = "/services/center/{id}")
    public @ResponseBody Iterable<Service> getServicesByCenter(@PathVariable Integer id) {
        return serviceService.findServicesByCenterId(id);
    }

    private Service applyPatchToService(JsonPatch patch, Service targetService) throws JsonPatchException, JsonProcessingException {
        JsonNode patched = patch.apply(objectMapper.convertValue(targetService, JsonNode.class));
        return objectMapper.treeToValue(patched, Service.class);
    }

    @PatchMapping(path = "/service/{id}", consumes = "application/json-patch+json")
    public ResponseEntity<Service> updateServicePatch(@PathVariable Integer id, @RequestBody JsonPatch patch) {
        try {
            Service service = serviceService.getService(id);
            Service servicePatched = applyPatchToService(patch, service);
            serviceService.updateServicePatched(servicePatched);
            return ResponseEntity.ok(servicePatched);
        } catch (JsonPatchException | JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}