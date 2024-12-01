package br.com.ita.greenframework.mock.dao;

import br.com.ita.greenframework.annotation.GreenDefaultReturn;
import br.com.ita.greenframework.mock.entity.User;

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
