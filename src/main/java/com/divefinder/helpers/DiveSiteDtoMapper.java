package com.divefinder.helpers;

import com.divefinder.models.DiveSite;
import com.divefinder.models.DiveSiteDto;
import org.springframework.stereotype.Component;

@Component
public class DiveSiteDtoMapper {
    public DiveSiteDtoMapper() {
    }

    public DiveSite diveSiteDtoToDiveSite(DiveSiteDto dto){
        DiveSite site = new DiveSite();
        site.setDescription(dto.getDescription());
        site.setLatitude(dto.getLatitude());
        site.setLongitude(dto.getLongitude());
        site.setSiteName(dto.getSiteName());
        site.setMaxDepth(dto.getMaxDepth());
        site.setApproved(dto.isApproved());

        return site;
    }
    public DiveSiteDto diveSiteToDto(DiveSite site){
        DiveSiteDto dto = new DiveSiteDto();
        dto.setDescription(site.getDescription());
        dto.setLatitude(site.getLatitude());
        dto.setLongitude(site.getLongitude());
        dto.setSiteName(site.getSiteName());
        dto.setMaxDepth(site.getMaxDepth());
        dto.setApproved(site.isApproved());
        dto.setId(site.getId());
        return dto;

    }
}
