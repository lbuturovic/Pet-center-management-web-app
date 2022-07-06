package ba.unsa.etf.pnwt.petcenter.Controller;

import ba.unsa.etf.pnwt.petcenter.Model.Report;
import ba.unsa.etf.pnwt.petcenter.Model.ReportRequest;
import ba.unsa.etf.pnwt.petcenter.Model.ReportResponse;
import ba.unsa.etf.pnwt.petcenter.Model.Status;
import ba.unsa.etf.pnwt.petcenter.Service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@EnableSwagger2
@RequestMapping(path = "/api")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping("/status/new-reports")
    public ResponseEntity<List<Report>> getAllReportsByStatus() {
        List<Report> reports = reportService.getAllReportsByStatus(Status.PENDING);
        if (reports.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(reports, HttpStatus.OK);
    }


    @GetMapping("/report/{id}")
    public ResponseEntity<ReportResponse> getReportsById(@PathVariable(value = "id") UUID id) {
        return new ResponseEntity<>(reportService.getReportsById(id), HttpStatus.OK);
    }

    @GetMapping("/reports")
    public ResponseEntity<List<Report>> getAllReports() {
        List<Report> reports = reportService.getAllReports();
        if (reports.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(reports, HttpStatus.OK);
    }

    @PostMapping("/report")
    public ResponseEntity<Report> createReport(@Valid @RequestBody ReportRequest reportRequest) {
        return new ResponseEntity<>(reportService.createReport(reportRequest), HttpStatus.CREATED);
    }

    @DeleteMapping("/reports/{id}")
    public ResponseEntity<HttpStatus> deleteReport(@PathVariable("id") UUID id) {
        reportService.deleteReport(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

  /*  @DeleteMapping("/status/{statusId}/reports")
    public ResponseEntity<List<Report>> deleteAllReportsOfStatus(@PathVariable(value = "statusId") Integer statusId) {
        reportService.deleteAllReportsOfStatus(statusId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }*/

    
}
