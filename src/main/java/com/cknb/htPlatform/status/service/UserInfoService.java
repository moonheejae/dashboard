package com.cknb.htPlatform.status.service;

import com.cknb.htPlatform.status.dto.vo.NewUserInterface;
import com.cknb.htPlatform.status.dto.vo.ReVisitInterface;
import com.cknb.htPlatform.status.repository.NewUserRepository;
import com.cknb.htPlatform.status.repository.ReVisitRepository;
import com.cknb.htPlatform.status.repository.UserTotalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class UserInfoService {
    private final String USER_ROLE = "USER";


    private final NewUserRepository newUserRepository;
    private final UserTotalRepository userTotalRepository;
    private final ReVisitRepository reVisitRepository;


    public NewUserInterface getNewUserCountAndJoinPath(Integer selectedDays) {
        return newUserRepository.getNewUserCountAndJoinPath(selectedDays);
    }

    public Long getUserTotal() {
        return userTotalRepository.countByUserRole(USER_ROLE);
    }

    public ReVisitInterface getReVisitUser(Integer selectedDays){
        return reVisitRepository.getReVisitUser(selectedDays);
    }
}
