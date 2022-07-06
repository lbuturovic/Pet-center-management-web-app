package ba.unsa.etf.pnwt.petcenter.Controllers;

import ba.unsa.etf.pnwt.petcenter.Models.Age;
import ba.unsa.etf.pnwt.petcenter.Exceptions.ApiError;
import ba.unsa.etf.pnwt.petcenter.Models.DesiredPet;
import ba.unsa.etf.pnwt.petcenter.Models.User;
import ba.unsa.etf.pnwt.petcenter.Repositories.DesiredPetRepository;
import ba.unsa.etf.pnwt.petcenter.Repositories.UserRepository;
import ba.unsa.etf.pnwt.petcenter.Services.DesiredPetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;


import java.util.UUID;

import static ba.unsa.etf.pnwt.petcenter.Models.Species.CAT;
import static ba.unsa.etf.pnwt.petcenter.Models.Species.DOG;


@RestController
public class DesiredPetController {
    @Autowired
    private DesiredPetRepository desiredPetRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DesiredPetService desiredPetService;


    @PostMapping(path ="/desired-pet/{uuid}")
    DesiredPet newDesiredPet(@PathVariable String uuid, @Valid @RequestBody DesiredPet newDesiredPet) {
       if(newDesiredPet.getSpecies()== null) throw new ApiError("validation","Species is not valid");
       if(newDesiredPet.getSpecies()!=DOG && newDesiredPet.getSpecies()!=CAT) throw new ApiError("validation","Species is not valid");
       if(newDesiredPet.getRace()== null) throw new ApiError("validation","Race is not valid");

       if(newDesiredPet.getAge()!=  Age.NEWBORN
               && newDesiredPet.getAge()!=Age.JUNIOR
               && newDesiredPet.getAge()!=Age.PRETEEN
               && newDesiredPet.getAge()!=Age.TEEN
               && newDesiredPet.getAge()!=Age.POSTTEEN
               && newDesiredPet.getAge()!=Age.FULLGROWN) throw new ApiError("validation","Age is not valid");
       if(newDesiredPet.getAge()== null) throw new ApiError("validation","Age is not valid");
        User u = userRepository.findByUUID(uuid);
        if (u != null) {
            u.setDesiredPet(newDesiredPet);
            newDesiredPet.setUser(u);
            userRepository.save(u);
        }
    return desiredPetRepository.save(newDesiredPet);
}
    @GetMapping(path="/desired-pets")
    public @ResponseBody Iterable<DesiredPet> getAllDesiredPets(){
        return desiredPetRepository.findAll();
    }

    @GetMapping(path="/desired-pets/dogs")
    public @ResponseBody Iterable<DesiredPet> findAllDogs(){
        return desiredPetService.findAllDogs();
    }

    @GetMapping(path="/desired-pets/cats")
    public @ResponseBody Iterable<DesiredPet> findAllCat(){
        return desiredPetService.findAllCats();
    }

    @GetMapping(path="/desired-pets/juniors")
    public @ResponseBody Iterable<DesiredPet> findAllJuniors(){
        return desiredPetService.findAllJuniors();
    }
}
