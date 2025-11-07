package com.divefinder.controllers.rest;

import com.divefinder.helpers.DiveSiteDtoMapper;
import com.divefinder.models.DiveSite;
import com.divefinder.models.DiveSiteDto;
import com.divefinder.services.DiveSiteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sites")
public class DiveSiteRestController {
    private final DiveSiteService diveSiteService;
    private final DiveSiteDtoMapper diveSiteDtoMapper;

    @Autowired
    public DiveSiteRestController(DiveSiteService diveSiteService, DiveSiteDtoMapper diveSiteDtoMapper) {
        this.diveSiteService = diveSiteService;
        this.diveSiteDtoMapper = diveSiteDtoMapper;
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
          DiveSite createdSite = diveSiteService.createDiveSite(diveSite);
          return diveSiteDtoMapper.diveSiteToDto(createdSite);

         }

    @DeleteMapping("/{id}")
    public void deleteSite(@PathVariable int id) {
        try {
            diveSiteService.deleteDiveSite(id);
        } catch (com.exceptions.EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }

    }
}
