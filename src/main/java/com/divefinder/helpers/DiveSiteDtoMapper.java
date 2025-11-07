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
        site.setLongtitude(dto.getLongitude());
        site.setSiteName(dto.getSiteName());
        site.setApproved(dto.isApproved());

        return site;
    }
    public DiveSiteDto diveSiteToDto(DiveSite site){
        DiveSiteDto dto = new DiveSiteDto();
        dto.setDescription(site.getDescription());
        dto.setLatitude(site.getLatitude());
        dto.setLongitude(site.getLongtitude());
        dto.setSiteName(site.getSiteName());
        dto.setApproved(site.isApproved());
        return dto;

    }
}
