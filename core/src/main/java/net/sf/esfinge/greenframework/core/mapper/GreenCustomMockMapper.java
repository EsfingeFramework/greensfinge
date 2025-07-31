package net.sf.esfinge.greenframework.core.mapper;

import net.sf.esfinge.greenframework.core.dto.annotation.GreenCustomMockConfiguration;
import net.sf.esfinge.greenframework.core.dto.project.CustomMockResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface GreenCustomMockMapper {

    GreenCustomMockMapper INSTANCE = Mappers.getMapper(GreenCustomMockMapper.class);

    CustomMockResponse toResponse(GreenCustomMockConfiguration config);

    List<CustomMockResponse> toResponse(List<GreenCustomMockConfiguration> config);
}
