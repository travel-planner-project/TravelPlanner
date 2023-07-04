package server.domain.planner.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.domain.planner.domain.Date;
import server.domain.planner.domain.Planner;
import server.domain.planner.dto.request.DateCreateRequest;
import server.domain.planner.dto.request.DateUpdateRequest;
import server.domain.planner.dto.response.DateResponse;
import server.domain.planner.repository.DateRepository;
import server.domain.planner.repository.PlannerRepository;
import server.global.code.ErrorCode;
import server.global.exception.HandlableException;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class DateService {

    private final PlannerRepository plannerRepository;
    private final DateRepository dateRepository;


    // 날짜 추가
    @Transactional
    public DateResponse createDate (DateCreateRequest request, Long plannerId) {

        Date date = Date.createDateList(request);

        Planner planner = plannerRepository.findById(plannerId)
                .orElseThrow(() -> new HandlableException(ErrorCode.NOT_EXISTS_PLANNER));

        planner.createDate(date);
        dateRepository.saveAndFlush(date);

        return new DateResponse(date);
    }

    // 날짜 수정
    @Transactional
    public DateResponse updateDate (DateUpdateRequest request) {

        Date date = dateRepository.findById(request.getDateId())
                .orElseThrow(() -> new HandlableException(ErrorCode.NOT_EXISTS_DATE));

        date.updateDateList(request);

        return new DateResponse(date);
    }

    // 날짜 삭제
    @Transactional
    public void deleteDate (Long dateId, Long plannerId) {

        Date date = dateRepository.findById(dateId)
                .orElseThrow(() -> new HandlableException(ErrorCode.NOT_EXISTS_DATE));

        Planner planner = plannerRepository.findById(plannerId)
                .orElseThrow(() -> new HandlableException(ErrorCode.NOT_EXISTS_PLANNER));

        planner.deleteDate(date);
        dateRepository.delete(date);
    }
}
