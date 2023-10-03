package travelplanner.project.demo.domain.post.image.domain;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import travelplanner.project.demo.domain.post.post.domain.Post;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String keyName;

    private String postImgUrl;

    private Boolean isThumbnail;

    private Long sort;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="post_id")
    @JsonBackReference
    private Post post;

}
