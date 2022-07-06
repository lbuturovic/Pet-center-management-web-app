package ba.unsa.etf.pnwt.petcenter.Services;

import ba.unsa.etf.pnwt.petcenter.Models.DesiredPet;
import ba.unsa.etf.pnwt.petcenter.Repositories.DesiredPetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DesiredPetServiceImplementation implements DesiredPetService {
    @Autowired
    private DesiredPetRepository desiredPetRepository;

    @Override
    public Iterable<DesiredPet> findAllDogs() {


        return desiredPetRepository.findAllDogs();
    }

    @Override
    public Iterable<DesiredPet> findAllCats() {
        return desiredPetRepository.findAllCats();
    }

    @Override
    public Iterable<DesiredPet> findAllJuniors() {
        return desiredPetRepository.findAllJuniors();
    }
}
