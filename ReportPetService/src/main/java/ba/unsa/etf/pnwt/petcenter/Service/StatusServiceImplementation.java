/*package ba.unsa.etf.pnwt.petcenter.Service;

import ba.unsa.etf.pnwt.petcenter.Exceptions.NotFoundException;
import ba.unsa.etf.pnwt.petcenter.Model.Status;
import ba.unsa.etf.pnwt.petcenter.Repository.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class StatusServiceImplementation implements StatusService {
    @Autowired
    private StatusRepository statusRepository;
    @Override
    public Status addStatus(Status status) {
        Status _status = statusRepository.save(new Status(status.getName()));
        return _status;
    }

    @Override
    public Status updateStatus(Integer id, Status status) {
        Status _status = statusRepository.findById(id).
        orElseThrow(() -> new NotFoundException("Status with id = " + id + " does not exist!"));
        _status.setName(status.getName());
        return statusRepository.save(_status);
    }

    @Override
    public void deleteStatus(Integer id) {
        if(id == null) {
            statusRepository.deleteAll();
            return;
        }
        if (!statusRepository.existsById(id)) {
            throw new NotFoundException("Status with id = " + id + " does not exist!");
        }
        statusRepository.deleteById(id);
        return;
    }

    @Override
    public List<Status> getStatuses(String name) {
        List<Status> statuses = new ArrayList<Status>();
        if (name == null)
            statusRepository.findAll().forEach(statuses::add);
        else
            statusRepository.findByName(name).forEach(statuses::add);
        return statuses;
    }

    @Override
    public Status getStatus(Integer id) {
        Status status = statusRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Status with id = " + id + " does not exist!"));
        return status;
    }
}*/
