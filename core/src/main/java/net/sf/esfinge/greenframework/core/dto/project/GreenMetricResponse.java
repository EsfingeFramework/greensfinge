package net.sf.esfinge.greenframework.core.dto.project;

import lombok.Getter;
import lombok.Setter;
import net.sf.esfinge.greenframework.core.util.GreenEnvironment;

import java.time.Duration;
import java.time.LocalDateTime;

import static net.sf.esfinge.greenframework.core.util.GreenConstant.CO2_PER_KWH_IN_KG;
import static net.sf.esfinge.greenframework.core.util.GreenConstant.JOULES_PER_KWH;

@Getter
@Setter
public class GreenMetricResponse {

    private String method;
    private Double metricSavedValue;
    private int countCalled = 0;
    private LocalDateTime beginMeasuredTime;
    private LocalDateTime endMeasuredTime;

    //Métricas de Energia
    public Double getTotalSavedKWh() {
        return getTotalSavedValue() / JOULES_PER_KWH;
    }

    public Double getEquivalentHoursLedLampOn() {
        Double powerLampLed = GreenEnvironment.getPowerLampLed();
        if(powerLampLed == 0.0) return 0.0;
        //Convert KWH em WH
        double totalWhSaved = getTotalSavedKWh() * 1000;
        return totalWhSaved / powerLampLed;
    }

    // Métricas Ambientais

    public Double getEstimatedCO2ReductionInKg() {
        return getTotalSavedKWh() * CO2_PER_KWH_IN_KG;
    }

    public Double getEquivalentKmNotDriven() {
        Double co2PerKMCar = GreenEnvironment.getCo2PerKMCar();
        if(co2PerKMCar == 0.0) return 0.0;
        return getEstimatedCO2ReductionInKg() / co2PerKMCar;
    }

    public Double getEquivalentTreesPlanted() {
        Double co2AbsorbedPerTree = GreenEnvironment.getCo2AbsorbedPerTree();
        if(co2AbsorbedPerTree == 0.0) return 0.0;
        return getEstimatedCO2ReductionInKg() / co2AbsorbedPerTree;
    }

    // Métricas Financeiras

    public Double getEstimatedCostSaving() {
        return getTotalSavedKWh() * GreenEnvironment.getPricePerKwh();
    }

    public Double getCostSavedPerSecond() {
        long totalSeconds = getTotalMeasuredSeconds();
        if (totalSeconds == 0) return 0.0;
        return getEstimatedCostSaving() / totalSeconds;
    }


    // Métricas de Uso

    public Double getTotalSavedValue() {
        return countCalled * metricSavedValue;
    }

    public long getTotalMeasuredSeconds() {
        if (beginMeasuredTime == null || endMeasuredTime == null || beginMeasuredTime.isAfter(endMeasuredTime)) {
            return 0;
        }
        return Duration.between(beginMeasuredTime, endMeasuredTime).toSeconds();
    }

    public Double getAverageSavedValuePerCall() {
        if (countCalled == 0) return 0.0;
        return getTotalSavedValue() / countCalled;
    }

    public Double getAverageSavedValuePerSecond() {
        long totalSeconds = getTotalMeasuredSeconds();
        if (totalSeconds == 0) return 0.0;
        return getTotalSavedValue() / totalSeconds;
    }

}