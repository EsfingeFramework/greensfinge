package net.sf.esfinge.greenframework.core.mapper;

import net.sf.esfinge.greenframework.core.dto.project.GreenMetricResponse;
import net.sf.esfinge.greenframework.core.entity.GreenMetric;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface GreenMetricMapper {

    GreenMetricMapper INSTANCE = Mappers.getMapper(GreenMetricMapper.class);

    GreenMetricResponse toResponse(GreenMetric config);

    List<GreenMetricResponse> toResponse(List<GreenMetric> config);

    default GreenMetricResponse toTotal(List<GreenMetric> metrics) {
        List<GreenMetricResponse> responses = toResponse(metrics);

        int totalQtyMockCall = 0;
        int totalQtyRealCall = 0;
        double totalSavedValue = 0.0;
        int totalCountCalled = 0;
        LocalDateTime begin = null;
        LocalDateTime end = null;

        for (GreenMetricResponse response : responses) {
            totalCountCalled += response.getCountCalled();
            totalSavedValue  += response.getTotalSavedValue();
            totalQtyMockCall += response.getQtyMockCall();
            totalQtyRealCall += response.getQtyRealCall();

            if (begin == null || response.getBeginMeasuredTime().isBefore(begin)) {
                begin = response.getBeginMeasuredTime();
            }
            if (end == null || response.getEndMeasuredTime().isAfter(end)) {
                end = response.getEndMeasuredTime();
            }
        }

        GreenMetricResponse total = new GreenMetricResponse();
        total.setMethod("TOTAL");
        total.setCountCalled(totalCountCalled);
        total.setQtyMockCall(totalQtyMockCall);
        total.setQtyRealCall(totalQtyRealCall);
        total.setBeginMeasuredTime(begin);
        total.setEndMeasuredTime(end);
        total.setTotalSavedValue(totalSavedValue);
        total.setMethods(responses);

        return total;
    }
}
