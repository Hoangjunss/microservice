package com.baconbao.comment_service.services.service;

import com.baconbao.comment_service.dto.CommentsDTO;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface CommentsService {
    CommentsDTO findById(Integer id);
    CommentsDTO create(CommentsDTO commentsDTO);
    CommentsDTO update(CommentsDTO commentsDTO);
    List<CommentsDTO> getCommentsByIdProfile(Integer idProfile);
}
