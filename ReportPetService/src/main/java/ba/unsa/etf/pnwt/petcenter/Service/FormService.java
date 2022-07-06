package ba.unsa.etf.pnwt.petcenter.Service;
import ba.unsa.etf.pnwt.petcenter.Model.Form;

import java.util.List;
import java.util.UUID;

public interface FormService {
    Form getFormsById(UUID id);
    List<Form> getAllForms(String type);
    Form createForm(UUID reportId, Integer centerId, Form formRequest);
    Form ChangeForm(UUID formId, Form form);
    void deleteForm(UUID id);
    void deleteAllForms();
    List<Form> getAllFormsByCenterId(Integer id);
    List<Form> getFormsByReportId(UUID id);
}
