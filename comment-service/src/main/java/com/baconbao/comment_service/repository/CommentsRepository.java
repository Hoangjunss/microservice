package com.baconbao.comment_service.repository;

import com.baconbao.comment_service.model.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CommentsRepository extends JpaRepository<Comments, Integer> {
    Optional<Comments> findById(Integer integer);
    @Query("SELECT c FROM Comments c WHERE c.idProfile = :idProfile")
    List<Comments> getCommentsByIdProfile(Integer idProfile);
}
