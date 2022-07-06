package ba.unsa.etf.pnwt.petcenter.Services;

import ba.unsa.etf.pnwt.petcenter.Models.Center;
import ba.unsa.etf.pnwt.petcenter.Repositories.CenterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CenterServiceImplementation implements CenterService{
    @Autowired
    CenterRepository centerRepository;
    @Override
    public Center createCenter(Center center) {
        return centerRepository.save(center);
    }

    @Override
    public void deleteCentar(Integer id) {
        centerRepository.deleteCenterById(id);
    }
}
