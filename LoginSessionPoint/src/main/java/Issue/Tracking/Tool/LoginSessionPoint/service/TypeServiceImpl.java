package Issue.Tracking.Tool.LoginSessionPoint.service;


import Issue.Tracking.Tool.LoginSessionPoint.domain.Type;
import Issue.Tracking.Tool.LoginSessionPoint.repo.TypeRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j

public class TypeServiceImpl implements TypeService{

    private final TypeRepo typeRepo;

    @Override
    public void SaveType(Type type) {
        typeRepo.save(type);
    }

    @Override
    public List<Type> findALL() {
      return  typeRepo.findAll();
    }
}
