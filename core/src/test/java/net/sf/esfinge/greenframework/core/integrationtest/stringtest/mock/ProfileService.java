package net.sf.esfinge.greenframework.core.integrationtest.stringtest.mock;

import net.sf.esfinge.greenframework.core.annotation.EnergySavingFixedEstimation;
import net.sf.esfinge.greenframework.core.annotation.GreenConfigKey;
import net.sf.esfinge.greenframework.core.annotation.GreenDefaultReturn;
import lombok.extern.slf4j.Slf4j;
import net.sf.esfinge.greenframework.core.annotation.GreenSwitchOff;

@Slf4j
public class ProfileService {

    @GreenSwitchOff
    @GreenConfigKey("ProfileService#findProfile")
    public String findProfile() {
        String value = "ProfileService - findProfile";
        log.info(value);
        return value;
    }

    @GreenConfigKey("ProfileService#findProfileWithEmptyAnnotation")
    @GreenSwitchOff
    @GreenDefaultReturn
    @EnergySavingFixedEstimation(energySavedValue = 3.2)
    public String findProfileWithEmptyAnnotation() {
        String value = "ProfileService - findProfile";
        log.info(value);
        return value;
    }

    @GreenConfigKey("ProfileService#findProfileWithValueAnnotation")
    @GreenSwitchOff
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
