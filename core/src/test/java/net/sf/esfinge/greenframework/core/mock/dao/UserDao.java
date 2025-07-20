package net.sf.esfinge.greenframework.core.mock.dao;

import net.sf.esfinge.greenframework.core.annotation.GreenDefaultReturn;
import net.sf.esfinge.greenframework.core.mock.entity.User;

public class UserDao {

    @GreenDefaultReturn
    public User getUser() {
        return new User();
    }

    @GreenDefaultReturn(strValue = "{\"name\":\"Mock Annotation\",\"countLogin\":2,\"profile\":\"Mock Profile Annotation\"}")
    public User getUserWithAnnotation() {
        return new User();
    }
}
