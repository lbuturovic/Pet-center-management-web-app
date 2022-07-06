package ba.unsa.etf.pnwt.petcenter.Service;

import ba.unsa.etf.pnwt.petcenter.Exceptions.NotFoundException;
import ba.unsa.etf.pnwt.petcenter.Model.*;
import ba.unsa.etf.pnwt.petcenter.Repository.CenterRepository;
import ba.unsa.etf.pnwt.petcenter.Repository.FormRepository;
import ba.unsa.etf.pnwt.petcenter.Repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@Service
public class ReportServiceImplementation implements ReportService{
    @Autowired
    ReportRepository reportRepository;
    @Autowired
    FormRepository formRepository;
    @Autowired
    CenterRepository centerRepository;
    @Override
    public List<Report> getAllReportsByStatus(Status status) {
        List<Report> reports = new ArrayList<>();
        reportRepository.findByStatus(status).forEach(reports::add);
        return reports;
    }

    @Override
    public ReportResponse getReportsById(UUID id) {
        Report report = reportRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Report with id = " + id + " does not exist!"));
        String image = Base64.getEncoder().encodeToString(report.getImagePath());
        ReportResponse response = new ReportResponse(image, report.getLongitude(), report.getLatitude(), report.getDescription(), report.getStatus(), report.getTimestamp(), report.getUpdateTimestamp(), report.getType());
        return response;
    }

    @Override
    public List<Report> getAllReports() {
        List<Report> reports = new ArrayList<Report>();
        reportRepository.findAll().forEach(reports::add);
        return reports;
    }

    @Override
    public Report createReport(ReportRequest reportRequest) {
        byte[] decodedBytes = Base64.getDecoder().decode(reportRequest.getImage());
        Report report = new Report(decodedBytes, reportRequest.getLongitude(), reportRequest.getLatitude(), reportRequest.getDescription(),
                Status.PENDING);
        //Form form = new Form(report, "Report was recorded. We hope our team will be at location soon.", null, Status.PENDING);
        //formRepository.save(form);
        report.setType(reportRequest.getType());
        reportRepository.save(report);
        return report;
    }

    @Override
    public void deleteReport(UUID id) {
        if (!reportRepository.existsById(id)) {
            throw new NotFoundException("Report with id = " + id + " does not exist!");
        }
        reportRepository.deleteById(id);
        return;
    }

/*    @Override
    public void deleteAllReportsOfStatus(Integer statusId) {
        if (!statusRepository.existsById(statusId)) {
            throw new ApiError("Validation", "Invalid id");
        }
        reportRepository.deleteReportsByStatus(statusId);
    }*/
}
