package com.baconbao.comment_service.controller;

import com.baconbao.comment_service.dto.ApiResponse;
import com.baconbao.comment_service.dto.CommentsDTO;
import com.baconbao.comment_service.services.service.CommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/comment")
public class CommentsController {
    @Autowired
    private CommentsService commentService;
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<CommentsDTO>> create(@RequestBody CommentsDTO commentsDTO){
        CommentsDTO commentsDTO2 = commentService.create(commentsDTO);
        return ResponseEntity.ok(new ApiResponse<>(true, "Create is success", commentsDTO2));
    }
    @PostMapping("/update")
    public ResponseEntity<ApiResponse<CommentsDTO>> update(@RequestBody CommentsDTO commentsDTO){
        CommentsDTO commentsDTO2 = commentService.update(commentsDTO);
        return ResponseEntity.ok(new ApiResponse<>(true, "Update is success", commentsDTO2));
    }

}
