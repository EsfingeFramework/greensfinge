package br.com.ita.greenframework.mock.service.stringtest;

import br.com.ita.greenframework.annotation.GreenConfigKey;
import br.com.ita.greenframework.annotation.GreenReturnWhenSwitchOff;
import br.com.ita.greenframework.annotation.GreenSwitch;
import br.com.ita.greenframework.mock.dao.UserDao;
import br.com.ita.greenframework.mock.entity.User;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserService {

    @GreenConfigKey("keyProfileService")
    @GreenSwitch
    private final ProfileService profileService = new ProfileService();

    @GreenConfigKey("keyUserDao")
    @GreenSwitch
    private final UserDao userDao = new UserDao();

    public String getUserProfile() {
        return profileService.findProfile();
    }

    public String getUserProfileEmptyAnnotation() {
        return profileService.findProfileWithEmptyAnnotation();
    }

    public String getUserProfileWithValueAnnotation() {
        return profileService.findProfileWithValueAnnotation();
    }

    public String getProfileWithRuntimeError() {
        return profileService.getProfileWithRuntimeError();
    }

    public String getProfileWithError() throws Exception {
        return profileService.getProfileWithError();
    }

    public User getUser() {
        return userDao.getUser();
    }

    public User getUserWithAnnotation() {
        return userDao.getUserWithAnnotation();
    }

    @GreenReturnWhenSwitchOff(strValue = "Method inside class return")
    public String geGreenValueInsideMethodClass() {
        return "";
    }

}
