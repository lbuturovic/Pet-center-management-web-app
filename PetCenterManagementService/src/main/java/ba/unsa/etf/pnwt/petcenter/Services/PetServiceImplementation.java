package ba.unsa.etf.pnwt.petcenter.Services;

import ba.unsa.etf.pnwt.petcenter.Exception.NotFoundException;
import ba.unsa.etf.pnwt.petcenter.Models.Center;
import ba.unsa.etf.pnwt.petcenter.Models.Pet;
import ba.unsa.etf.pnwt.petcenter.Models.PetRequest;
import ba.unsa.etf.pnwt.petcenter.Models.User;
import ba.unsa.etf.pnwt.petcenter.Repositories.CenterRepository;
import ba.unsa.etf.pnwt.petcenter.Repositories.PetRepository;
import ba.unsa.etf.pnwt.petcenter.Repositories.UserRepository;
import com.fasterxml.jackson.databind.util.ArrayIterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Iterator;

@Service
public class PetServiceImplementation implements PetService {

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private CenterRepository centerRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public String addPet(PetRequest pet) {
        Pet p = new Pet();
        Center c = centerRepository.findById(pet.getCenter()).orElseThrow(() -> new NotFoundException("Center with id = " + pet.getCenter() + " does not exist!"));
        p.setCenter(c);
        p.setSpecies(pet.getSpecies());
        p.setRace(pet.getRace());
        p.setName(pet.getName());
        p.setAge(pet.getAge());
        p.setWeight(pet.getWeight());
        p.setHealthCondition(pet.getHealthCondition());
        p.setGender(pet.getGender());
        p.setImage(Base64.getDecoder().decode(pet.getImage()));
        p.setStatus(pet.getStatus());
        p.setCategory(pet.getCategory());
        petRepository.save(p);
        return "Pet successfully added!";
    }

    @Override
    public String updatePet(Integer id, PetRequest pet) {
        Pet p = petRepository.findById(id).orElseThrow(() -> new NotFoundException("Pet with id = " + id + " does not exist!"));
        Center c = centerRepository.findById(pet.getCenter()).orElseThrow(() -> new NotFoundException("Center with id = " + pet.getCenter() + " does not exist!"));
        p.setCenter(c);
        p.setSpecies(pet.getSpecies());
        p.setRace(pet.getRace());
        p.setName(pet.getName());
        p.setAge(pet.getAge());
        p.setWeight(pet.getWeight());
        p.setHealthCondition(pet.getHealthCondition());
        p.setGender(pet.getGender());
        p.setImage(Base64.getDecoder().decode(pet.getImage()));
        p.setStatus(pet.getStatus());
        p.setCategory(pet.getCategory());
        petRepository.save(p);
        return "Pet with id = " + id +" successfully updated!";
    }

    @Override
    public String deletePet(Integer id) {
        petRepository.findById(id).orElseThrow(() -> new NotFoundException("Pet with id = " + id + " does not exist!"));
        petRepository.deleteById(id);
        return "Pet with id = " + id +" successfully deleted!";
    }

    @Override
    public ArrayList<PetRequest> getPets() {

        Iterable<Pet> pets =  petRepository.findAll();
        ArrayList<PetRequest> petRequestsList = new ArrayList<>();

        for (Pet pet: pets) {
            PetRequest pr = new PetRequest();
            pr.setId(pet.getId());
            pr.setAge(pet.getAge());
            pr.setCategory(pet.getCategory());
            pr.setName(pet.getName());
            pr.setGender(pet.getGender());
            pr.setWeight(pet.getWeight());
            pr.setHealthCondition(pet.getHealthCondition());
            pr.setRace(pet.getRace());
            pr.setSpecies(pet.getSpecies());
            pr.setStatus(pet.getStatus());
            pr.setImage(Base64.getEncoder().encodeToString(pet.getImage()));
            pr.setCenter(pet.getCenter().getId());
            petRequestsList.add(pr);
        }
        return petRequestsList;
    }
    @Override
    public PetRequest getPet(Integer id) {

        Pet pet = petRepository.findById(id).orElseThrow(()->new NotFoundException("Pet with id = " + id + " does not exist!"));
        PetRequest pr = new PetRequest();
        pr.setAge(pet.getAge());
        pr.setCategory(pet.getCategory());
        pr.setName(pet.getName());
        pr.setGender(pet.getGender());
        pr.setWeight(pet.getWeight());
        pr.setHealthCondition(pet.getHealthCondition());
        pr.setRace(pet.getRace());
        pr.setSpecies(pet.getSpecies());
        pr.setStatus(pet.getStatus());
        pr.setImage(Base64.getEncoder().encodeToString(pet.getImage()));
        pr.setCenter(pet.getCenter().getId());
        return pr;
    }

    @Override
    public Iterable<Pet> findPetsByOwnerId(Integer id) {
        return petRepository.findPetsByOwner(id);
    }

    @Override
    public Iterable<Pet> findPetsByCenterId(Integer id) {
        return petRepository.findPetsByCenter(id);
    }

    @Override
    public Iterable<Pet> findAbandonedDogs() {
        return petRepository.findAbandonedDogs();
    }

    @Override
    public Iterable<Pet> findAbandonedCats() {
        return petRepository.findAbandonedCats();
    }

    @Override
    public void updatePetPatched(Pet petPatched) {
        petRepository.save(petPatched);
    }

    @Override
    public Iterable<Pet> findAbandonedPets() {
        return petRepository.findAbandonedPets();
    }
}
