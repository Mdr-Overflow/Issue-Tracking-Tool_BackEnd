package Issue.Tracking.Tool.LoginSessionPoint.service;

import Issue.Tracking.Tool.LoginSessionPoint.domain.Status;
import Issue.Tracking.Tool.LoginSessionPoint.repo.StatusRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j

public class StatusServiceImpl  implements  StatusService{
    private final StatusRepo statusRepo;

    @Override
    public Status getStatus(String name) { return  statusRepo.getStatusByName(name);
    }

    @Override
    public List<Status> getStatuses() {
        return  statusRepo.findAll();
    }

    @Override
    public void SaveStatus(Status status) {
        statusRepo.save(status);
    }

    @Override
    public void deleteStatus(String name) {
        statusRepo.deleteByName(name);
    }


    @Override
    public List<Status> findBy(String toSearch) {
        long intValue = 0L;
        try { intValue = Integer.parseInt(toSearch);}
        catch (NumberFormatException ignored){}

        return  statusRepo.findBy("%"+toSearch +"%",intValue);
    }

}
