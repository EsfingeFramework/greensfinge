package net.sf.esfinge.greenframework.core.mock.service.stringtest;

import net.sf.esfinge.greenframework.core.annotation.GreenConfigKey;
import net.sf.esfinge.greenframework.core.annotation.GreenDefaultReturn;
import net.sf.esfinge.greenframework.core.annotation.GreenSwitch;
import net.sf.esfinge.greenframework.core.mock.dao.UserDao;
import net.sf.esfinge.greenframework.core.mock.entity.User;
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

    @GreenConfigKey("keyMethodConfig")
    @GreenDefaultReturn(strValue = "Method inside class return")
    public String geGreenValueInsideMethodClass() {
        return "";
    }

}
