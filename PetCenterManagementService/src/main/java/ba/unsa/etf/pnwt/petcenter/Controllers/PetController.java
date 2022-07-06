package ba.unsa.etf.pnwt.petcenter.Controllers;

import ba.unsa.etf.pnwt.petcenter.Exception.NotFoundException;
import ba.unsa.etf.pnwt.petcenter.Models.Center;
import ba.unsa.etf.pnwt.petcenter.Models.Pet;
import ba.unsa.etf.pnwt.petcenter.Models.PetRequest;
import ba.unsa.etf.pnwt.petcenter.Services.PetService;
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

import javax.validation.Valid;
import java.util.Base64;


@RestController
@EnableSwagger2
@RequestMapping(path = "/api")
public class PetController {

    @Autowired
    private PetService petService;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping(path="/pets")
    public @ResponseBody Iterable<PetRequest> getAllPets(){
        return petService.getPets();
    }

    @GetMapping(path = "/pet/{id}")
    public PetRequest getPet(@PathVariable Integer id){
        return petService.getPet(id);
    }

    @PostMapping(path = "/edit/pet/{id}")
    public ResponseEntity<String> updatePet(@RequestBody  PetRequest pet, @PathVariable Integer id) {
        return new ResponseEntity<>(petService.updatePet(id, pet), HttpStatus.OK);
    }

    @PostMapping(path = "/pet")
    public ResponseEntity<String> addNewPet(@RequestBody PetRequest pet) {
        return new ResponseEntity<>(petService.addPet(pet), HttpStatus.OK);
    }

    @DeleteMapping(value = "/pet/{id}")
    public ResponseEntity<String> deletePet(@PathVariable Integer id) {
        return new ResponseEntity<>(petService.deletePet(id), HttpStatus.OK);
    }

    @GetMapping(value = "/delete/pet/{id}")
    public ResponseEntity<String> deletePet2(@PathVariable Integer id) {
        return new ResponseEntity<>(petService.deletePet(id), HttpStatus.OK);
    }

    @GetMapping(path = "/pets/owner/{id}")
    public @ResponseBody Iterable<Pet> getPetsByOwner(@PathVariable Integer id) {
        return petService.findPetsByOwnerId(id);
    }

    @GetMapping(path = "/pets/center/{id}")
    public @ResponseBody Iterable<Pet> getPetsByCenter(@PathVariable Integer id) {
        return petService.findPetsByCenterId(id);
    }

    @GetMapping(path = "/dogs/abandoned")
    public @ResponseBody Iterable<Pet> getAbandonedDogs() {
        return petService.findAbandonedDogs();
    }

    @GetMapping(path = "/pets/abandoned")
    public @ResponseBody Iterable<Pet> getPetsAbandoned() {
        return petService.findAbandonedPets();
    }

    @GetMapping(path = "/cats/abandoned")
    public @ResponseBody Iterable<Pet> getAbandonedCats() {
        return petService.findAbandonedCats();
    }


    private Pet applyPatchToPet(JsonPatch patch, Pet targetPet) throws JsonPatchException, JsonProcessingException {
        JsonNode patched = patch.apply(objectMapper.convertValue(targetPet, JsonNode.class));
        return objectMapper.treeToValue(patched, Pet.class);
    }

    /*@PatchMapping(path = "/pet/{id}", consumes = "application/json-patch+json")
    public ResponseEntity<Pet> updatePetPatch(@PathVariable Integer id, @RequestBody JsonPatch patch) {
        try {
            Pet pet =petService.getPet(id);
            Pet petPatched = applyPatchToPet(patch, pet);
            petService.updatePetPatched(pet);
            return ResponseEntity.ok(petPatched);
        } catch (JsonPatchException | JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }*/
}
