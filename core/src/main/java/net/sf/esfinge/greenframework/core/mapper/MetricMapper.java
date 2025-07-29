package net.sf.esfinge.greenframework.core.mapper;

import net.sf.esfinge.greenframework.core.dto.project.GreenMetricResponse;
import net.sf.esfinge.greenframework.core.entity.GreenMetric;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface MetricMapper {

    MetricMapper INSTANCE = Mappers.getMapper(MetricMapper.class);

    GreenMetricResponse toResponse(GreenMetric config);

    List<GreenMetricResponse> toResponse(List<GreenMetric> config);

}
