package br.com.ita.greenframework.service.tests;

import br.com.ita.greenframework.annotation.GreenMetric;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GroupService {

    @GreenMetric(metricSavedValue = 8.2548)
    public String doSomething(String test) {
        String value = "GroupService - doSomething - " + test;
        log.info(value);
        return value;
    }

    @GreenMetric(metricSavedValue = 456.787)
    public void doSomething0() {
        String value = "GroupService - doSomething0 - ";
        log.info(value);
    }

    @GreenMetric(metricSavedValue = 2.788)
    public String doSomething2() {
        String value = "GroupService - doSomething2";
        log.info(value);
        return value;
    }

    public String doSomething3(Integer test) {
        String value = "GroupService - doSomething3 - "+test;
        log.info(value);
        return value;
    }

    public String doSomething4(Integer test) {
        String value = "GroupService - doSomething4 - "+test;
        log.info(value);
        return value;
    }

    public Integer doSomething5(Integer test) {
        String value = "GroupService - doSomething5 - "+test;
        log.info(value);
        return 10;
    }

}
