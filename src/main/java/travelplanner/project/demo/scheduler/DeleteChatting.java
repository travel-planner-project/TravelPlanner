package travelplanner.project.demo.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import travelplanner.project.demo.planner.chat.ChattingRepository;

import java.time.LocalDateTime;

@Service
public class DeleteChatting {

    @Autowired
    private ChattingRepository chattingRepository;

    @Transactional
    @Scheduled(cron = "0 0 3 * * ?")
    public void deleteOldChats() {
        LocalDateTime oneMonthAgo = LocalDateTime.now().minusMonths(1);
        chattingRepository.deleteAllByCreatedAtBefore(oneMonthAgo);
    }
}
