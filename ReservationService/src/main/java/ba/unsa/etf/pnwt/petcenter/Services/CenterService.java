package ba.unsa.etf.pnwt.petcenter.Services;

import ba.unsa.etf.pnwt.petcenter.Models.Center;
import org.springframework.stereotype.Service;

@Service
public interface CenterService {
    public abstract Center createCenter(Center center);
    public abstract void deleteCentar(Integer id);
}
