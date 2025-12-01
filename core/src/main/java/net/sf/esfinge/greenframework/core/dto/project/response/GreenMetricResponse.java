package net.sf.esfinge.greenframework.core.dto.project.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import net.sf.esfinge.greenframework.core.util.GreenEnvironment;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static net.sf.esfinge.greenframework.core.util.GreenConstant.CO2_PER_KWH_IN_KG;
import static net.sf.esfinge.greenframework.core.util.GreenConstant.JOULES_PER_KWH;

@Getter
@Setter
@JsonInclude(NON_NULL)
public class GreenMetricResponse {

    private String method;
    private Integer countCalled;
    private Integer qtyRealCall;
    private Integer qtyMockCall;
    private Double totalSavedValue;
    private LocalDateTime beginMeasuredTime;
    private LocalDateTime endMeasuredTime;
    private List<GreenMetricResponseTrace> traces;
    private List<GreenMetricResponse> methods;

    //Energy Metrics
    public Double getTotalSavedKWh() {
        return getTotalSavedValue() / JOULES_PER_KWH;
    }

    public Double getEstimatedEquivalentHoursLedLampOn() {
        Double powerLampLed = GreenEnvironment.getPowerLampLed();
        if(powerLampLed.equals(0.0)) return 0.0;
        //Convert KWH em WH
        double totalWhSaved = getTotalSavedKWh() * 1000;
        return totalWhSaved / powerLampLed;
    }

    // Environmental Metrics
    public Double getEstimatedCO2ReductionInKg() {
        return getTotalSavedKWh() * CO2_PER_KWH_IN_KG;
    }

    public Double getEstimatedEquivalentKmNotDriven() {
        Double co2PerKMCar = GreenEnvironment.getCo2PerKMCar();
        if(co2PerKMCar.equals(0.0)) return 0.0;
        return getEstimatedCO2ReductionInKg() / co2PerKMCar;
    }

    public Double getEstimatedCo2AbsorbedPerTree() {
        Double co2AbsorbedPerTree = GreenEnvironment.getCo2AbsorbedPerTree();
        if(co2AbsorbedPerTree.equals(0.0)) return 0.0;
        return getEstimatedCO2ReductionInKg() / co2AbsorbedPerTree;
    }

    // Financial Metrics
    public Double getEstimatedCostSaving() {
        return getTotalSavedKWh() * GreenEnvironment.getPricePerKwh();
    }

    // Usage Metrics
    public long getTotalMeasuredSeconds() {
        if (Objects.isNull(beginMeasuredTime) || Objects.isNull(endMeasuredTime) || beginMeasuredTime.isAfter(endMeasuredTime)) {
            return 0;
        }
        return Duration.between(beginMeasuredTime, endMeasuredTime).toSeconds();
    }

}