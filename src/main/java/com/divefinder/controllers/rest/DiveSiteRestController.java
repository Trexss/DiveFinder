package com.divefinder.controllers.rest;

import com.divefinder.helpers.DiveSiteHelper;
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
    private final DiveSiteHelper diveSiteHelper;

    @Autowired
    public DiveSiteRestController(DiveSiteService diveSiteService, DiveSiteHelper diveSiteHelper) {
        this.diveSiteService = diveSiteService;
        this.diveSiteHelper = diveSiteHelper;
    }

    @GetMapping
    public List<DiveSiteDto> getApprovedSites() {
        return diveSiteService.getAllApprovedSites()
                .stream()
                .map(diveSiteHelper::diveSiteToDto)
                .toList();
    }

    @GetMapping("/{id}")
    public DiveSiteDto getSiteById(@PathVariable int id) {
        try {
            return diveSiteHelper.diveSiteToDto(diveSiteService.getSiteById(id));

        } catch (com.exceptions.EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.toString());
        }
    }

    @PostMapping
    public DiveSiteDto createSite(@Valid @RequestBody DiveSiteDto dto) {
        DiveSite diveSite = diveSiteHelper.diveSiteDtoToDiveSite(dto);
          DiveSite createdSite = diveSiteService.createDiveSite(diveSite);
          return diveSiteHelper.diveSiteToDto(createdSite);

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
