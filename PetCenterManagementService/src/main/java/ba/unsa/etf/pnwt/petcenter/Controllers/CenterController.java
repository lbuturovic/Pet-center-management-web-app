package ba.unsa.etf.pnwt.petcenter.Controllers;

import ba.unsa.etf.pnwt.petcenter.Exception.NotFoundException;
import ba.unsa.etf.pnwt.petcenter.Models.Center;
import ba.unsa.etf.pnwt.petcenter.Services.CenterService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.apache.tomcat.util.json.JSONParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.spring.web.json.Json;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.persistence.criteria.CriteriaBuilder;
import javax.validation.Valid;
import java.util.Map;


@RestController
@EnableSwagger2
@RefreshScope
@RequestMapping(path = "/api")
public class CenterController {

   /* @Value("${message}")
    private String message;*/

    @Autowired
    private CenterService centerService;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping(path="/centers")
    public @ResponseBody Iterable<Center> getAllCenters(){
       return centerService.getCenters();
    }

    @GetMapping(path = "/center/{id}")
    public @ResponseBody Center getCenter(@PathVariable Integer id){
       return centerService.getCenter(id);
    }

    @PutMapping(path = "/center/{id}")
    public ResponseEntity<String> updateCenter(@Valid @RequestBody Center center, @PathVariable Integer id){
        return new ResponseEntity<>(centerService.updateCenter(id, center), HttpStatus.OK);
    }

    @PostMapping(path = "/edit-center/{id}")
    public ResponseEntity<String> editCenter(@Valid @RequestBody Center center, @PathVariable Integer id){
        return new ResponseEntity<>(centerService.updateCenter(id, center), HttpStatus.OK);
    }

    @PutMapping(path = "/center/{centerId}/user/{userId}")
    public ResponseEntity<String> addUserForCenter(@PathVariable Integer centerId, @PathVariable Integer userId){
        return new ResponseEntity<>(centerService.addUserForCenter(centerId, userId), HttpStatus.OK);
    }

    @PostMapping(path = "/center")
    public ResponseEntity<String> addNewCenter(@Valid @RequestBody Center center) {
        return new ResponseEntity<>(centerService.addCenter(center), HttpStatus.OK);
        //return ResponseEntity.ok().body(new Json(centerService.addCenter(center)));
    }

    @DeleteMapping(value = "/center/{id}")
    public ResponseEntity<String> deleteCenter(@PathVariable Integer id) {
        return new ResponseEntity<>(centerService.deleteCenter(id), HttpStatus.OK);
    }

    @PostMapping(value = "/delete-center/{id}")
    public ResponseEntity<String> removeCenter(@PathVariable Integer id) {
        return new ResponseEntity<>(centerService.deleteCenter(id), HttpStatus.OK);
    }

    @GetMapping(path="/centers/service/{id}")
    public @ResponseBody Iterable<Center> findCentersByService(@PathVariable Integer id){
        return centerService.findCenterByServiceId(id);
    }

    /*@GetMapping(path = "/message")
    public String messaging(){
        return "Message: "+ message;
    }*/


    private Center applyPatchToCenter(JsonPatch patch, Center targetCenter) throws JsonPatchException, JsonProcessingException {
        JsonNode patched = patch.apply(objectMapper.convertValue(targetCenter, JsonNode.class));
        return objectMapper.treeToValue(patched, Center.class);
    }

   @PatchMapping(path = "/center/{id}", consumes = "application/json-patch+json")
    public ResponseEntity<Center> updateCenterPatch(@PathVariable Integer id, @RequestBody JsonPatch patch) {
        try {
            Center center =centerService.getCenter(id);
            Center centerPatched = applyPatchToCenter(patch, center);
            centerService.updateCenterPatched(centerPatched);
            return ResponseEntity.ok(centerPatched);
        } catch (JsonPatchException | JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping(path="/center/free/{id}")
    public ResponseEntity<Object> checkIfCenterIsFree(@PathVariable Integer id){
        return ResponseEntity.status(HttpStatus.OK).body(centerService.checkIfCenterIsFree(id));
    }

    @PutMapping(path = "/center/{centerId}/pet/{petId}")
    public ResponseEntity<String> addPetToCenter(@PathVariable Integer centerId, @PathVariable Integer petId) {
        return new ResponseEntity<>(centerService.addPetToCenter(centerId, petId), HttpStatus.OK);
        //return ResponseEntity.ok().body(new Json(centerService.addCenter(center)));
    }

}
