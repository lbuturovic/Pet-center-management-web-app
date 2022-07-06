package ba.unsa.etf.pnwt.petcenter.Repository;

import ba.unsa.etf.pnwt.petcenter.Model.SystemEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SystemEventRepository extends JpaRepository<SystemEvent, Integer> {
}

