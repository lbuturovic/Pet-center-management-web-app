package ba.unsa.etf.pnwt.petcenter.Services;

import ba.unsa.etf.pnwt.petcenter.Models.Pet;
import ba.unsa.etf.pnwt.petcenter.Models.Service;

import java.util.Collection;

public interface ServiceService {
    public abstract String addService(Service service);
    public abstract String updateService(Integer id, Service service);
    public abstract String deleteService(Integer id);
    public abstract Iterable<Service> getServices();
    public abstract Service getService(Integer id);
    public abstract Iterable<Service> findServicesByCenterId(Integer id);
    public abstract void updateServicePatched(Service servicePatched);
}
