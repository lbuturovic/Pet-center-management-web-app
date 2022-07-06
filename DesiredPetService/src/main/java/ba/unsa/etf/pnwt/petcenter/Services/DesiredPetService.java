package ba.unsa.etf.pnwt.petcenter.Services;

import ba.unsa.etf.pnwt.petcenter.Models.DesiredPet;

public interface DesiredPetService {
    Iterable<DesiredPet> findAllDogs();

    Iterable<DesiredPet> findAllCats();

    Iterable<DesiredPet> findAllJuniors();
}
