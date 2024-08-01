package com.baconbao.comment_service.repository;

import com.baconbao.comment_service.model.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CommentsRepository extends JpaRepository<Comments, Integer> {
    Optional<Comments> findById(Integer integer);
    @Query(value = "SELECT * FROM comments WHERE profile_id = :idProfile", nativeQuery = true)
    List<Comments> getCommentsByIdProfile(Integer idProfile);
}
