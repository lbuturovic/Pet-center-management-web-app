package ba.unsa.etf.pnwt.petcenter.Services;

import ba.unsa.etf.pnwt.petcenter.Models.Center;
import ba.unsa.etf.pnwt.petcenter.Models.Pet;
import ba.unsa.etf.pnwt.petcenter.Models.PetRequest;

import javax.ejb.Local;


@Local
public interface PetService {
    public abstract String addPet(PetRequest pet);
    public abstract String updatePet(Integer id, PetRequest pet);
    public abstract String deletePet(Integer id);
    public abstract Iterable<PetRequest> getPets();
    public abstract PetRequest getPet(Integer id);
    public abstract Iterable<Pet> findPetsByOwnerId(Integer id);
    public abstract Iterable<Pet> findPetsByCenterId(Integer id);
    public abstract Iterable<Pet> findAbandonedDogs();
    public abstract Iterable<Pet> findAbandonedCats();
    public abstract Iterable<Pet> findAbandonedPets();
    public abstract void updatePetPatched(Pet petPatched);

}
