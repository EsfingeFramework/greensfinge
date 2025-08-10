package net.sf.esfinge.greenframework.core.integrationtest.voidtest.mock;

import net.sf.esfinge.greenframework.core.annotation.GreenConfigKey;
import net.sf.esfinge.greenframework.core.annotation.GreenSwitch;
import net.sf.esfinge.greenframework.core.annotation.GreenSwitchOff;

public class UserService {

    @GreenSwitch
    @GreenConfigKey("keyProfileService")
    private final ProfileService profileService = new ProfileService();

    public String findUser() {
        return" initial value " +
                profileService.findProfile() +
                " final value ";
    }

    @GreenSwitchOff
    @GreenConfigKey("methodKeyConfig")
    public void doSomethingWithHighConsumeEnergy(StringBuilder strParameter) {
        strParameter.append("something very high");
    }
}
