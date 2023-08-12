package travelplanner.project.demo.global.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import travelplanner.project.demo.planner.domain.Chatting;
import travelplanner.project.demo.planner.repository.ChattingRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DeleteChatting {

    private final ChattingRepository chattingRepository;

    @Transactional
    @Scheduled(cron = "0 0 3 * * ?") // 매일 새벽 새시 오늘 기준으로 한달전 채팅은 삭제
    public void deleteOldChats() {

        LocalDateTime oneMonthAgo = LocalDateTime.now().minusMonths(1);

        int deleteCount;

        do {
            // 한달 전의 채팅 중 100개만 조회
            List<Chatting> chatsToDelete = chattingRepository.findTop100ByCreatedAtBefore(oneMonthAgo);

            // 조회된 채팅을 삭제
            chattingRepository.deleteAll(chatsToDelete);

            // 실제로 삭제된 채팅의 개수를 저장
            deleteCount = chatsToDelete.size();

        } while (deleteCount == 100);  // 100개 미만이 삭제되면 반복 중지
    }
}
