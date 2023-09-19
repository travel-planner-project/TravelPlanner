package travelplanner.project.demo.domain.post.post.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import travelplanner.project.demo.domain.post.post.domain.Post;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findAll(Pageable pageable, Sort createdAt);
}
