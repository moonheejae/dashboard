package com.cknb.htPlatform.traffic.service;

import com.cknb.htPlatform.traffic.dto.ResponseCommunityDto;
import com.cknb.htPlatform.traffic.repository.CommDebateRepository;
import com.cknb.htPlatform.traffic.repository.CommInfoRepository;
import com.cknb.htPlatform.traffic.repository.CommReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class CommunityService {

    private final CommReviewRepository commReviewRepository;
    private final CommDebateRepository commDebateRepository;
    private final CommInfoRepository commInfoRepository;

    // 정품 제품 리뷰
    public ResponseCommunityDto getCommReviewCount(Integer selectedDays) {
        return ResponseCommunityDto.builder()
                .dto(commReviewRepository.getCommReviewCount(selectedDays))
                .build();
    }

    // 정품 Qna
    public ResponseCommunityDto getCommDebateCount(Integer selectedDays) {
        return ResponseCommunityDto.builder()
                .dto(commDebateRepository.getCommDebateCount(selectedDays))
                .build();
    }

    // 정품 판별 팁
    public ResponseCommunityDto getCommInfoCount(Integer selectedDays) {
        return ResponseCommunityDto.builder()
                .dto(commInfoRepository.getCommInfoCount(selectedDays))
                .build();
    }

}
