package net.sf.esfinge.greenframework.mock.service.voidtest;

import net.sf.esfinge.greenframework.annotation.GreenConfigKey;
import net.sf.esfinge.greenframework.annotation.GreenSwitch;
import net.sf.esfinge.greenframework.annotation.GreenSwitchOff;

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
