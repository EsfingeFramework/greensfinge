package net.sf.esfinge.greenframework.mock.service.stringtest;

import net.sf.esfinge.greenframework.annotation.EnergySavingFixedEstimation;
import net.sf.esfinge.greenframework.annotation.GreenDefaultReturn;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProfileService {

    public String findProfile() {
        String value = "ProfileService - findProfile";
        log.info(value);
        return value;
    }

    @GreenDefaultReturn
    @EnergySavingFixedEstimation(energySavedValue = 3.2)
    public String findProfileWithEmptyAnnotation() {
        String value = "ProfileService - findProfile";
        log.info(value);
        return value;
    }

    @GreenDefaultReturn(strValue = "Mock value in annotation")
    public String findProfileWithValueAnnotation() {
        String value = "ProfileService - findProfile";
        log.info(value);
        return value;
    }

    public String getProfileWithRuntimeError() {
        throw new IllegalArgumentException("Should not invoke this runtime error method");
    }

    public String getProfileWithError() throws Exception {
        throw new Exception("Should not invoke this exception method");
    }
}
