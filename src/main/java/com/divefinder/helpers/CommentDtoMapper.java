package com.divefinder.helpers;

import com.divefinder.models.Comment;
import com.divefinder.models.CommentDto;
import com.divefinder.models.DiveSite;
import com.divefinder.models.User;
import com.divefinder.services.DiveSiteService;
import com.divefinder.services.UserService;
import org.springframework.stereotype.Component;

@Component
public class CommentDtoMapper {
    private final DiveSiteService diveSiteService;
    private final UserService userService;

    public CommentDtoMapper(DiveSiteService diveSiteService, UserService userService) {
        this.diveSiteService = diveSiteService;
        this.userService = userService;
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
        comment.setCommentText(dto.getCommentText());


        User userRef = new User();
        userRef.setId(userId);  // only set id
        comment.setUser(userRef);

        DiveSite siteRef = new DiveSite();
        siteRef.setId(diveSiteId);
        comment.setDiveSite(siteRef);

        return comment;
    }
}

