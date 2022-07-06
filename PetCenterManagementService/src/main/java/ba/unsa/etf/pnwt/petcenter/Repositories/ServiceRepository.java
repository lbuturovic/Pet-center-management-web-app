package ba.unsa.etf.pnwt.petcenter.Repositories;

import ba.unsa.etf.pnwt.petcenter.Models.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;


public interface ServiceRepository extends JpaRepository<Service, Integer> {

    @Query(value = "SELECT * FROM service s, center_service cs WHERE  s.id = cs.service_id AND cs.center_id = :id", nativeQuery = true)
    @Modifying
    @Transactional
    public Iterable<Service> findServiceByCenterId(@Param("id") Integer id);

}
