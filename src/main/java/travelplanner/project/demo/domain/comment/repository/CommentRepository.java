package travelplanner.project.demo.domain.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import travelplanner.project.demo.domain.comment.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
