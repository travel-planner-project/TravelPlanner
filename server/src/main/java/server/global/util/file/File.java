package server.global.util.file;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class File {

    // 파일 인덱스
    @Id
    @GeneratedValue
    private Long fileId;

    // 파일 태그
    private String fileTag;

    // 파일 원래 이름
    private String originFileName;

    // 새로 만든 파일 이름
    private String renameFileName;

    // 파일 url
    private String filePath;

    // 파일 생성 날짜
    @Column(columnDefinition = "timestamp default now()")
    private LocalDateTime createdAt;
}
