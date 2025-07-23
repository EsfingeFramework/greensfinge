package net.sf.esfinge.greenframework.core.dto.project;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.sf.esfinge.greenframework.core.util.GreenEnvironment;

import java.time.Duration;
import java.time.LocalDateTime;

import static net.sf.esfinge.greenframework.core.util.GreenConstant.CO2_PER_KWH_IN_KG;
import static net.sf.esfinge.greenframework.core.util.GreenConstant.JOULES_PER_KWH;

@Getter
@Setter
@ToString
@Builder
public class GreenMetric {

    private String method;
    private Double metricSavedValue;
    private int countCalled = 0;
    private LocalDateTime beginMeasuredTime;
    private LocalDateTime endMeasuredTime;

    @ToString.Include
    public Double getTotalSavedValue() {
        return countCalled * metricSavedValue;
    }

    @ToString.Include
    public Double getAverageSavedValuePerCall() {
        if (countCalled == 0) return 0.0;
        return getTotalSavedValue() / countCalled;
    }

    @ToString.Include
    public long getTotalMeasuredSeconds() {
        if (beginMeasuredTime == null || endMeasuredTime == null || beginMeasuredTime.isAfter(endMeasuredTime)) {
            return 0;
        }
        return Duration.between(beginMeasuredTime, endMeasuredTime).toSeconds();
    }

    @ToString.Include
    public Double getAverageSavedValuePerSecond() {
        long totalSeconds = getTotalMeasuredSeconds();
        if (totalSeconds == 0) return 0.0;
        return getTotalSavedValue() / totalSeconds;
    }

    @ToString.Include
    public Double getTotalSavedKWh() {
        return getTotalSavedValue() / JOULES_PER_KWH;
    }

    @ToString.Include
    public Double getEstimatedCO2ReductionInKg() {
        return getTotalSavedKWh() * CO2_PER_KWH_IN_KG;
    }

    @ToString.Include
    public Double getEstimatedCostSaving() {
        return getTotalSavedKWh() * GreenEnvironment.getPricePerKwh();
    }

}
