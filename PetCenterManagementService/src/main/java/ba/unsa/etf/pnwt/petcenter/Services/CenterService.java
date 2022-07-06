package ba.unsa.etf.pnwt.petcenter.Services;


import ba.unsa.etf.pnwt.petcenter.Models.Center;
import org.springframework.http.ResponseEntity;

import java.util.Map;


public interface CenterService {
    public abstract String addCenter(Center center);
    public abstract String updateCenter(Integer id, Center center);
    public abstract String deleteCenter(Integer id);
    public abstract Center getCenter(Integer id);
    public abstract Iterable<Center> getCenters();
    public abstract Iterable<Center> findCenterByServiceId(Integer id);
    public abstract void updateCenterPatched(Center centerPatched);
    public abstract Map<String, Object> checkIfCenterIsFree(Integer id);
    public abstract String addPetToCenter(Integer centerId, Integer petId);
    public String addUserForCenter(Integer centerId, Integer userId);
}
