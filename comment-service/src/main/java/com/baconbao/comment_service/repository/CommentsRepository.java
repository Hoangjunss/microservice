package com.baconbao.comment_service.repository;

import com.baconbao.comment_service.model.Comments;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentsRepository extends JpaRepository<Comments, Integer> {
    Optional<Comments> findById(Integer integer);
}
