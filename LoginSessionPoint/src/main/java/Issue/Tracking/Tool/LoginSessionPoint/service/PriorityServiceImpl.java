package Issue.Tracking.Tool.LoginSessionPoint.service;

import Issue.Tracking.Tool.LoginSessionPoint.domain.Priority;
import Issue.Tracking.Tool.LoginSessionPoint.domain.Type;
import Issue.Tracking.Tool.LoginSessionPoint.repo.PriorityRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@RequiredArgsConstructor
@Transactional
@Slf4j

public class PriorityServiceImpl implements PriorityService {
    private final PriorityRepo priorityRepo;


    @Override
    public Priority getPrio(String name) {
        return  priorityRepo.findByName(name);
    }

    @Override
    public List<Priority> getPrios() {
        return priorityRepo.findAll();
    }

    @Override
    public void SavePriority(Priority priority) {
        priorityRepo.save(priority);
    }

    @Override
    public void deletePrio(String name) {
        priorityRepo.deleteByName(name);

    }

    @Override
    public List<Priority> findBy(String toSearch) {
        long intValue = 0L;
        try { intValue = Integer.parseInt(toSearch);}
        catch (NumberFormatException ignored){}

        return priorityRepo.findBy(toSearch,intValue);
    }

}
