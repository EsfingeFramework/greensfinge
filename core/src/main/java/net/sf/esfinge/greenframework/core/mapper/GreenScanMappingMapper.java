package net.sf.esfinge.greenframework.core.mapper;

import net.sf.esfinge.greenframework.core.dto.project.response.GreenScanMappingResponse;
import net.sf.esfinge.greenframework.core.entity.GreenScanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface GreenScanMappingMapper {

    GreenScanMappingMapper INSTANCE = Mappers.getMapper(GreenScanMappingMapper.class);

    GreenScanMappingResponse toResponse(GreenScanMapping config);

    List<GreenScanMappingResponse> toResponse(List<GreenScanMapping> config);

}
