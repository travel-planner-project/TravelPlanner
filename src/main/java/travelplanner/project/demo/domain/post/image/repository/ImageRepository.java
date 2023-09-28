package travelplanner.project.demo.domain.post.image.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import travelplanner.project.demo.domain.post.image.domain.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {
    void deleteAllByPostId(Long postId);
}
