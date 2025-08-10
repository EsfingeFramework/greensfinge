package net.sf.esfinge.greenframework.core.integrationtest.voidtest.mock;

import net.sf.esfinge.greenframework.core.annotation.GreenConfigKey;
import net.sf.esfinge.greenframework.core.annotation.GreenSwitchOff;

public class ProfileService {

    @GreenConfigKey("ProfileService#findProfile")
    @GreenSwitchOff
    public String findProfile() {
        return "Some profile";
    }

}
