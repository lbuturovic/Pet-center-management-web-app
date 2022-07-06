package ba.unsa.etf.pnwt.petcenter.Service;

import ba.unsa.etf.pnwt.petcenter.Model.Center;
import ba.unsa.etf.pnwt.petcenter.Model.Form;
import ba.unsa.etf.pnwt.petcenter.Repository.CenterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public List<Center> getCenters() {
        List<Center> centers = new ArrayList<>();
        centerRepository.findAllByActive(true).forEach(centers::add);
        return centers;
    }
}
