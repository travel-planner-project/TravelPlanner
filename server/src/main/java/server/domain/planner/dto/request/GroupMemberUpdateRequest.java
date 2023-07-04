package server.domain.planner.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GroupMemberUpdateRequest {

    // 이메일로 회원 조회
    private String email;
}