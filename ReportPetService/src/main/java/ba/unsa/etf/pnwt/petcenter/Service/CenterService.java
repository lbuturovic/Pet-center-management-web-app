package ba.unsa.etf.pnwt.petcenter.Service;

import ba.unsa.etf.pnwt.petcenter.Model.Center;

import java.util.List;


public interface CenterService {
    Center createCenter(Center center);
    void deleteCentar(Integer id);
    List<Center> getCenters();
}
