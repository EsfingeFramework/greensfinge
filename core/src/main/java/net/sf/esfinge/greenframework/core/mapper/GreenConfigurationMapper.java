package net.sf.esfinge.greenframework.core.mapper;

import net.sf.esfinge.greenframework.core.dto.annotation.GreenDefaultConfiguration;
import net.sf.esfinge.greenframework.core.dto.project.ConfigurationResponse;
import net.sf.esfinge.greenframework.core.mapper.helper.GreenConfigurationMapperHelper;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper (uses = GreenConfigurationMapperHelper.class)
public interface GreenConfigurationMapper {

    GreenConfigurationMapper INSTANCE = Mappers.getMapper(GreenConfigurationMapper.class);

    @IterableMapping(qualifiedByName = "customConfigurationMapper")
    List<ConfigurationResponse> toResponse(List<GreenDefaultConfiguration> config);
}
