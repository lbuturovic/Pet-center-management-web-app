package ba.unsa.etf.pnwt.petcenter.Repository;

import ba.unsa.etf.pnwt.petcenter.Model.Center;
import ba.unsa.etf.pnwt.petcenter.Model.Form;
import ba.unsa.etf.pnwt.petcenter.Model.Report;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FormRepository extends CrudRepository<Form, UUID> {
   // List<Form> findByTypeContaining(String type);
    @Query(value = "SELECT * FROM form WHERE center = :id ORDER BY update_timestamp DESC ", nativeQuery = true)
    List<Form> findFormsByCenterId(@Param("id")Integer id);
    List<Form> findByReportOrderByTimestampDesc(Report report);
    //List<Form> findByCenter(Center c);
}

