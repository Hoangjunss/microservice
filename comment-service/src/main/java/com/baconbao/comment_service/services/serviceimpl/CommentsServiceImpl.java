package com.baconbao.comment_service.services.serviceimpl;

import com.baconbao.comment_service.dto.CommentsDTO;
import com.baconbao.comment_service.dto.ProfileDTO;
import com.baconbao.comment_service.exception.CustomException;
import com.baconbao.comment_service.exception.Error;
import com.baconbao.comment_service.model.Comments;
import com.baconbao.comment_service.openfeign.ProfileClient;
import com.baconbao.comment_service.repository.CommentsRepository;
import com.baconbao.comment_service.services.service.CommentsService;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CommentsServiceImpl implements CommentsService {
    @Autowired
    private CommentsRepository commentsRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ProfileClient profileClient;

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
        try {
            log.info("Saving comments");
            ProfileDTO profileDTO=profileClient.getProfileById(commentsDTO.getIdProfile());
            Comments comments = Comments.builder()
                    .id(getGenerationId())
                    .content(commentsDTO.getContent())
                    .createAt(commentsDTO.getCreateAt())
                    .idProfile(profileDTO.getId())
                    .build();
            return commentsRepository.save(comments);
        } catch (DataIntegrityViolationException e){
            throw new CustomException(Error.COMMENT_UNABLE_TO_SAVE);
        } catch (DataAccessException e){
            throw new CustomException(Error.DATABASE_ACCESS_ERROR);
        }
    }

    @Override
    public CommentsDTO findById(Integer id) {
        log.info("Find comments by id: {}", id);
        return convertToDTO(commentsRepository.findById(id)
                .orElseThrow(()-> new CustomException(Error.COMMENT_NOT_FOUND)));
    }

    @Override
    public CommentsDTO create(CommentsDTO commentsDTO) {
        log.info("Create comments");
        return convertToDTO(save(commentsDTO));
    }

    @Override
    public CommentsDTO update(CommentsDTO commentsDTO) {
        try {
            log.info("Update comments");
            return convertToDTO(commentsRepository.save(convertToModel(commentsDTO)));
        } catch (DataIntegrityViolationException e){
            throw new CustomException(Error.COMMENT_UNABLE_TO_UPDATE);
        } catch (DataAccessException e){
            throw new CustomException(Error.DATABASE_ACCESS_ERROR);
        }
    }
    @Override
    public List<CommentsDTO> getCommentsByIdProfile(Integer idProfile) {
        try {
            log.info("Find all comments by idProfile: {}", idProfile);
            List<Comments> comments = commentsRepository.getCommentsByIdProfile(idProfile);
            return convertToDTOList(comments);
        } catch (DataAccessException e){
            throw new CustomException(Error.DATABASE_ACCESS_ERROR);
        }

    }

    private List<CommentsDTO> convertToDTOList(List<Comments> comments){
        return comments.stream()
                .map(comment -> modelMapper.map(comment, CommentsDTO.class))
                .collect(Collectors.toList());
    }

}
