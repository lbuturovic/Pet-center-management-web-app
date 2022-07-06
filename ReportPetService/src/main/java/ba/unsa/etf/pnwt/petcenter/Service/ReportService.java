package ba.unsa.etf.pnwt.petcenter.Service;

import ba.unsa.etf.pnwt.petcenter.Model.Report;
import ba.unsa.etf.pnwt.petcenter.Model.ReportRequest;
import ba.unsa.etf.pnwt.petcenter.Model.ReportResponse;
import ba.unsa.etf.pnwt.petcenter.Model.Status;

import java.util.List;
import java.util.UUID;

public interface ReportService {
    public abstract List<Report> getAllReportsByStatus(Status status);
    public abstract ReportResponse getReportsById(UUID id);
    public abstract List<Report> getAllReports();
    public abstract Report createReport(ReportRequest reportRequest);
    public abstract void deleteReport(UUID id);
   // public abstract void deleteAllReportsOfStatus(Integer statusId);

}
