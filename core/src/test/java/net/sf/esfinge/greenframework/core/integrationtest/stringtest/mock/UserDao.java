package net.sf.esfinge.greenframework.core.integrationtest.stringtest.mock;

import net.sf.esfinge.greenframework.core.annotation.GreenConfigKey;
import net.sf.esfinge.greenframework.core.annotation.GreenDefaultReturn;
import net.sf.esfinge.greenframework.core.annotation.GreenSwitchOff;

public class UserDao {

    @GreenConfigKey("UserDao#getUser")
    @GreenSwitchOff
    @GreenDefaultReturn
    public User getUser() {
        return new User();
    }

    @GreenConfigKey("UserDao#getUserWithAnnotation")
    @GreenSwitchOff
    @GreenDefaultReturn(strValue = "{\"name\":\"Mock Annotation\",\"countLogin\":2,\"profile\":\"Mock Profile Annotation\"}")
    public User getUserWithAnnotation() {
        return new User();
    }
}
