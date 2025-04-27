package net.sf.esfinge.greenframework.mock.service.voidtest;

import net.sf.esfinge.greenframework.annotation.GreenConfigKey;
import net.sf.esfinge.greenframework.annotation.GreenReturnWhenSwitchOff;
import net.sf.esfinge.greenframework.annotation.GreenSwitch;

public class UserService {

    @GreenSwitch
    @GreenConfigKey("keyProfileService")
    private final ProfileService profileService = new ProfileService();

    public String findUser() {
        return" initial value " +
                profileService.findProfile() +
                " final value ";
    }

    @GreenConfigKey("methodKeyConfig")
    @GreenReturnWhenSwitchOff
    public void doSomethingWithHighConsumeEnergy(StringBuilder strParameter) {
        strParameter.append("something very high");
    }
}
