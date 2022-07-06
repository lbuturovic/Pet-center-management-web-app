package ba.unsa.etf.pnwt.petcenter.Services;

import ba.unsa.etf.pnwt.petcenter.Exception.ApiError;
import ba.unsa.etf.pnwt.petcenter.Exception.NotFoundException;
import ba.unsa.etf.pnwt.petcenter.Models.Center;
import ba.unsa.etf.pnwt.petcenter.Models.Pet;
import ba.unsa.etf.pnwt.petcenter.Repositories.CenterRepository;
import ba.unsa.etf.pnwt.petcenter.Repositories.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;




@Service
public class ServiceServiceImplementation implements ServiceService {

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private CenterRepository centerRepository;

    @Override
    public String addService(ba.unsa.etf.pnwt.petcenter.Models.Service service) {
       for (Center c: service.getCenters()) {
           centerRepository.findById(c.getId()).orElseThrow(() ->new NotFoundException("Center with id = " + c.getId() + " does not exist!"));
       }
       for (Center c: service.getCenters()) {
           Center c1 = centerRepository.findById(c.getId()).get();
           c1.getServices().add(service);
       }
        serviceRepository.save(service);
        return "Service successfully added!";
    }

    @Override
    public String updateService(Integer id, ba.unsa.etf.pnwt.petcenter.Models.Service service) {
        ba.unsa.etf.pnwt.petcenter.Models.Service s = serviceRepository.findById(id).orElseThrow(() -> new NotFoundException("Service with id = " + id + " does not exist!"));
        for (Center c: service.getCenters())
            centerRepository.findById(c.getId()).orElseThrow(() -> new NotFoundException("Center with id = " + c.getId() + " does not exist!"));
        s.setName(service.getName());
        s.setPrice(service.getPrice());
        s.setDuration(service.getDuration());
        s.setType(service.getType());
        for (Center c: service.getCenters()) {
            s.getCenters().add(c);
            c.getServices().add(s);
            centerRepository.save(c);
        }
        serviceRepository.save(s);
        return "Service with id = " + s.getId() +" successfully updated!";
    }

    @Override
    public String deleteService(Integer id) {
        ba.unsa.etf.pnwt.petcenter.Models.Service s = serviceRepository.findById(id).orElseThrow(() -> new NotFoundException("Service with id = " + id + " does not exist!"));
        for (Center c: s.getCenters()) {
            c.getServices().remove(s);
            s.getCenters().remove(c);
            centerRepository.save(c);
        }
        serviceRepository.deleteById(id);
        return "Service with id = " + id +" successfully deleted!";
    }

    @Override
    public Iterable<ba.unsa.etf.pnwt.petcenter.Models.Service> getServices() {
        return serviceRepository.findAll();
    }

    @Override
    public ba.unsa.etf.pnwt.petcenter.Models.Service getService(Integer id) {
        return serviceRepository.findById(id).orElseThrow(()->new NotFoundException("Service with id = " + id + " does not exist!"));
    }

    @Override
    public Iterable<ba.unsa.etf.pnwt.petcenter.Models.Service> findServicesByCenterId(Integer id) {
        return serviceRepository.findServiceByCenterId(id);
    }

    @Override
    public void updateServicePatched( ba.unsa.etf.pnwt.petcenter.Models.Service servicePatched) {
        serviceRepository.save(servicePatched);
    }
}
