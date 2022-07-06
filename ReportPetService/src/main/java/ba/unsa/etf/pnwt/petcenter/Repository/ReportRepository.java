package ba.unsa.etf.pnwt.petcenter.Repository;

import ba.unsa.etf.pnwt.petcenter.Model.Center;
import ba.unsa.etf.pnwt.petcenter.Model.Form;
import ba.unsa.etf.pnwt.petcenter.Model.Report;
import ba.unsa.etf.pnwt.petcenter.Model.Status;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReportRepository extends CrudRepository<Report, UUID> {
    /*
    List<Report> findByStatus(Integer status);
    //List<Report> findByLocationContaining(String location);

    @Transactional
    void deleteByStatus(Integer status); */

    @Query(value = "SELECT * FROM report WHERE status = :id ORDER BY update_timestamp DESC ", nativeQuery = true)
    List<Report> findReportsByStatus(@Param("id") Integer id);
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM report  WHERE status =:id", nativeQuery = true)
    void deleteReportsByStatus(@Param("id") Integer id);

    List<Report> findByStatus(Status status);
}
