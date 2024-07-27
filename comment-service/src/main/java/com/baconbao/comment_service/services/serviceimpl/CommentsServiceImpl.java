package com.baconbao.comment_service.services.serviceimpl;

import com.baconbao.comment_service.dto.CommentsDTO;
import com.baconbao.comment_service.model.Comments;
import com.baconbao.comment_service.repository.CommentsRepository;
import com.baconbao.comment_service.services.service.CommentsService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class CommentsServiceImpl implements CommentsService {
    @Autowired
    private CommentsRepository commentsRepository;
    @Autowired
    private ModelMapper modelMapper;

    private Integer getGenerationId() {
        UUID uuid = UUID.randomUUID();
        return (int) (uuid.getMostSignificantBits() & 0xFFFFFFFFL);
    }
    private Comments convertToModel(CommentsDTO commentsDTO){
        return modelMapper.map(commentsDTO, Comments.class);
    }
    private CommentsDTO convertToDTO(Comments comments){
        return modelMapper.map(comments, CommentsDTO.class);
    }

    private Comments save(CommentsDTO commentsDTO){
        Comments comments = Comments.builder()
                .id(getGenerationId())
                .content(commentsDTO.getContent())
                .createAt(commentsDTO.getCreateAt())
                .build();
        return commentsRepository.save(comments);
    }

    @Override
    public CommentsDTO findById(Integer id) {
        return convertToDTO(commentsRepository.findById(id).orElseThrow());
    }

    @Override
    public CommentsDTO create(CommentsDTO commentsDTO) {
        return convertToDTO(save(commentsDTO));
    }

    @Override
    public CommentsDTO update(CommentsDTO commentsDTO) {
        return convertToDTO(commentsRepository.save(convertToModel(commentsDTO)));
    }
}
