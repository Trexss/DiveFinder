package com.divefinder.controllers.rest;

import com.divefinder.exceptions.EntityDuplicateException;
import com.divefinder.helpers.AuthenticationHelper;
import com.divefinder.helpers.CommentDtoMapper;
import com.divefinder.helpers.DiveSiteDtoMapper;
import com.divefinder.models.*;
import com.divefinder.services.DiveSiteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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
   private final AuthenticationHelper authenticationHelper;

    @Autowired
    public DiveSiteRestController(DiveSiteService diveSiteService, DiveSiteDtoMapper diveSiteDtoMapper, CommentDtoMapper commentDtoMapper, AuthenticationHelper authenticationHelper) {
        this.diveSiteService = diveSiteService;
        this.diveSiteDtoMapper = diveSiteDtoMapper;
        this.commentDtoMapper = commentDtoMapper;
        this.authenticationHelper = authenticationHelper;
    }
    //toDo make errors trace show up in postman

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
            return diveSiteDtoMapper.diveSiteToDto(diveSiteService.getApprovedSiteById(id));

        } catch (com.exceptions.EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping
    public DiveSiteDto createSite(@Valid @RequestBody DiveSiteDto dto, @RequestHeader HttpHeaders headers) {
        DiveSite diveSite = diveSiteDtoMapper.diveSiteDtoToDiveSite(dto);
        try {
            User user = authenticationHelper.tryGetUser(headers);
            DiveSite createdSite = diveSiteService.createDiveSite(diveSite);
            return diveSiteDtoMapper.diveSiteToDto(createdSite);
        } catch (EntityDuplicateException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }catch (com.exceptions.AuthorizationException e){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void deleteSite(@PathVariable int id, @RequestHeader HttpHeaders headers) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            diveSiteService.deleteDiveSite(id, user);
        } catch (com.exceptions.EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }catch (com.exceptions.AuthorizationException e){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }

    }
    @GetMapping("/{id}/comments")
    public Set<CommentDto> getCommentsForSite(@PathVariable int id) {
        Set<Comment> comments;
        try{
             comments = diveSiteService.getCommentsForDiveSite(id);
        }catch (com.exceptions.EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }

        return comments.stream()
               .map(commentDtoMapper::toDto)
               .collect(Collectors.toSet());

    }
    @PostMapping("/{id}/comments")
    public void addCommentToSite(@PathVariable int id, @Valid @RequestBody CommentDto commentDto, @RequestHeader HttpHeaders headers) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            int userId = user.getId();
            Comment comment = commentDtoMapper.fromDto(commentDto, id, userId);
            diveSiteService.addCommentToDiveSite(comment);
        }catch (com.exceptions.EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }catch (com.exceptions.AuthorizationException e){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }
}
