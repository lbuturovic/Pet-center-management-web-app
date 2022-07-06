package ba.unsa.etf.pnwt.petcenter.Service;

import ba.unsa.etf.pnwt.petcenter.Exceptions.ApiError;
import ba.unsa.etf.pnwt.petcenter.Exceptions.NotFoundException;
import ba.unsa.etf.pnwt.petcenter.Model.Center;
import ba.unsa.etf.pnwt.petcenter.Model.Form;
import ba.unsa.etf.pnwt.petcenter.Model.Report;
import ba.unsa.etf.pnwt.petcenter.Repository.CenterRepository;
import ba.unsa.etf.pnwt.petcenter.Repository.FormRepository;
import ba.unsa.etf.pnwt.petcenter.Repository.ReportRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class FormServiceImplementation implements FormService {
    @Autowired
    FormRepository formRepository;
    @Autowired
    ReportRepository reportRepository;
    @Autowired
    CenterRepository centerRepository;
    @Autowired
    RestTemplate restTemplate;

    @Override
    public Form getFormsById(UUID id) {
        Form form = formRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Form with id = " + id + " does not exist!"));
        return form;
    }

    @Override
    public List<Form> getAllForms(String type) {
        List<Form> forms = new ArrayList<Form>();
            formRepository.findAll().forEach(forms::add);
        return forms;
    }

    @Override
    public Form createForm(UUID reportId, Integer centerId, Form formRequest) {
        Report report = reportRepository.findById(reportId).orElseThrow(() -> new NotFoundException("Report with id = " + reportId + " does not exist!"));
        Center center = centerRepository.findById(centerId).orElseThrow(() -> new NotFoundException("Center with id = " + centerId + " does not exist!"));
            HashMap<String, Integer> params = new HashMap<>();
            params.put("id", center.getId());
            ResponseEntity<Object> response = restTemplate.getForEntity("http://pet-center-management-service/api/center/free/{id}", Object.class, params);
            ObjectMapper oMapper = new ObjectMapper();
            Map<String, Object> map = oMapper.convertValue(response.getBody(), Map.class);
            if (map.get("avaliable").equals(false)) throw new ApiError("Not allowed", "Capacity of center is filled!");
            formRequest.setReport(report);
            formRequest.setCenter(center);
            formRequest.setStatus(formRequest.getStatus());
            report.setStatus(formRequest.getStatus());
            reportRepository.save(report);
            return formRepository.save(formRequest);
    }

    @Override
    public Form ChangeForm(UUID formId, Form form) {
        Form _form = formRepository.findById(formId).orElseThrow(() -> new NotFoundException("Form with id = " + formId + " does not exist!"));
        _form.setReport(form.getReport());
        _form.setDescription(form.getDescription());
        formRepository.save(_form);
        return _form;
    }

    @Override
    public void deleteForm(UUID id) {
        if (!formRepository.existsById(id)) {
            throw new NotFoundException("Form with id = " + id + " does not exist!");
        }
        formRepository.deleteById(id);
    }

    @Override
    public void deleteAllForms() {
        formRepository.deleteAll();
    }

    @Override
    public List<Form> getAllFormsByCenterId(Integer id) {
        if (!centerRepository.existsById(id)) {
            throw new NotFoundException("Center with id = " + id + " does not exist!");
        }
        List<Form> forms = formRepository.findFormsByCenterId(id);
        return forms;
    }

    @Override
    public List<Form> getFormsByReportId(UUID id) {
        Report report = reportRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Report with id = " + id + " does not exist!"));
        List<Form> forms = formRepository.findByReportOrderByTimestampDesc(report);
        return forms;
    }
}
