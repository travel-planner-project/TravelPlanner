package travelplanner.project.demo.global.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import travelplanner.project.demo.planner.repository.ChattingRepository;

import java.time.LocalDateTime;

@Service
public class DeleteChatting {

    @Autowired
    private ChattingRepository chattingRepository;

    @Transactional
    @Scheduled(cron = "0 0 3 * * ?") // 매일 새벽 새시 오늘 기준으로 한달전 채팅은 삭제
    public void deleteOldChats() {
        LocalDateTime oneMonthAgo = LocalDateTime.now().minusMonths(1);
        chattingRepository.deleteAllByCreatedAtBefore(oneMonthAgo);
    }
}
