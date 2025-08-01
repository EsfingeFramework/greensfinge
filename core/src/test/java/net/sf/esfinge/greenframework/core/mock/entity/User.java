package net.sf.esfinge.greenframework.core.mock.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {

    private String name;
    private Integer countLogin;
    private transient String profile;
}
