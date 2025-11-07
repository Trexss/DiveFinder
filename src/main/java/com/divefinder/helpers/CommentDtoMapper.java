package com.divefinder.helpers;

import com.divefinder.models.Comment;
import com.divefinder.models.CommentDto;
import com.divefinder.models.DiveSite;
import com.divefinder.models.User;

public class CommentDtoMapper {

    public static CommentDto toDto(Comment comment) {
        CommentDto dto = new CommentDto();
        dto.setCommentText(comment.getCommentText());
        dto.setDateCreated(comment.getDateCreated());
        dto.setUserId(comment.getUser().getId());
        dto.setDiveSiteId(comment.getDiveSite().getId());
        return dto;
    }


    public static Comment fromDto(CommentDto dto, User user, DiveSite diveSite) {
        Comment comment = new Comment();
        comment.setCommentText(dto.getCommentText());
        comment.setDateCreated(dto.getDateCreated());
        comment.setUser(user);
        comment.setDiveSite(diveSite);
        return comment;
    }
}

