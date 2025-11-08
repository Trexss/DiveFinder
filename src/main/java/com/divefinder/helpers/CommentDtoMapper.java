package com.divefinder.helpers;

import com.divefinder.models.Comment;
import com.divefinder.models.CommentDto;
import com.divefinder.models.DiveSite;
import com.divefinder.models.User;
import com.divefinder.services.DiveSiteService;
import org.springframework.stereotype.Component;

@Component
public class CommentDtoMapper {
    private final DiveSiteService diveSiteService;
    public CommentDtoMapper(DiveSiteService diveSiteService) {
        this.diveSiteService = diveSiteService;
    }

    public  CommentDto toDto(Comment comment) {
        CommentDto dto = new CommentDto();
        dto.setCommentText(comment.getCommentText());
        dto.setDateCreated(comment.getDateCreated());
        dto.setUserId(comment.getUser().getId());
        dto.setDiveSiteId(comment.getDiveSite().getId());
        return dto;
    }


    public  Comment fromDto(CommentDto dto, int diveSiteId, int userId) {
        Comment comment = new Comment();
        //toDo fetch user from user service
        User user = new User();
        user.setId(dto.getUserId());
        comment.setCommentText(dto.getCommentText());
        comment.setDateCreated(dto.getDateCreated());
        comment.setUser(user);
        comment.setDiveSite(diveSiteService.getSiteById(diveSiteId));
        return comment;
    }
}

