package com.baconbao.comment_service.controller;

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
    public ResponseEntity<CommentsDTO> create(@RequestBody CommentsDTO commentsDTO){
        return ResponseEntity.ok(commentService.create(commentsDTO));
    }
    @PostMapping("/update")
    public ResponseEntity<CommentsDTO> update(@RequestBody CommentsDTO commentsDTO){
        return ResponseEntity.ok(commentService.update(commentsDTO));
    }

}
