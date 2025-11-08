package com.divefinder.controllers.rest;

import com.divefinder.exceptions.EntityDuplicateException;
import com.divefinder.helpers.CommentDtoMapper;
import com.divefinder.helpers.DiveSiteDtoMapper;
import com.divefinder.models.Comment;
import com.divefinder.models.CommentDto;
import com.divefinder.models.DiveSite;
import com.divefinder.models.DiveSiteDto;
import com.divefinder.services.DiveSiteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/sites")
public class DiveSiteRestController {
    private final DiveSiteService diveSiteService;
    private final DiveSiteDtoMapper diveSiteDtoMapper;
   private final CommentDtoMapper commentDtoMapper;

    @Autowired
    public DiveSiteRestController(DiveSiteService diveSiteService, DiveSiteDtoMapper diveSiteDtoMapper, CommentDtoMapper commentDtoMapper) {
        this.diveSiteService = diveSiteService;
        this.diveSiteDtoMapper = diveSiteDtoMapper;
        this.commentDtoMapper = commentDtoMapper;
    }

    @GetMapping
    public List<DiveSiteDto> getApprovedSites() {
        return diveSiteService.getAllApprovedSites()
                .stream()
                .map(diveSiteDtoMapper::diveSiteToDto)
                .toList();
    }

    @GetMapping("/{id}")
    public DiveSiteDto getSiteById(@PathVariable int id) {
        try {
            return diveSiteDtoMapper.diveSiteToDto(diveSiteService.getSiteById(id));

        } catch (com.exceptions.EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping
    public DiveSiteDto createSite(@Valid @RequestBody DiveSiteDto dto) {
        DiveSite diveSite = diveSiteDtoMapper.diveSiteDtoToDiveSite(dto);
        try {
            DiveSite createdSite = diveSiteService.createDiveSite(diveSite);
            return diveSiteDtoMapper.diveSiteToDto(createdSite);
        } catch (EntityDuplicateException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());

        }
    }

    @DeleteMapping("/{id}")
    public void deleteSite(@PathVariable int id) {
        try {
            diveSiteService.deleteDiveSite(id);
        } catch (com.exceptions.EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }

    }
    @GetMapping("/{id}/comments")
    public Set<CommentDto> getCommentsForSite(@PathVariable int id) {
        Set<Comment> comments = diveSiteService.getCommentsForDiveSite(id);
        return comments.stream()
               .map(commentDtoMapper::toDto)
               .collect(Collectors.toSet());

    }
    @PostMapping("/{id}/comments")
    public void addCommentToSite(@PathVariable int id, @Valid @RequestBody CommentDto commentDto) {
        try {
            //toDo will need to get user from auth service
            int userId = 1;
            Comment comment = commentDtoMapper.fromDto(commentDto, id, userId);
            diveSiteService.addCommentToDiveSite(comment);
        }catch (com.exceptions.EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
