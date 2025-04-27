package net.sf.esfinge.greenframework.mock.service.sameclasstest;

import net.sf.esfinge.greenframework.annotation.GreenConfigKey;
import net.sf.esfinge.greenframework.annotation.GreenReturnWhenSwitchOff;

public class RecommendationDAO {

    @GreenConfigKey("keyConfigProduct")
    @GreenReturnWhenSwitchOff
    public String findProduct() {
        System.out.println("Method findProduct executed");
        return "product";
    }

    @GreenConfigKey("keyConfigVisits")
    @GreenReturnWhenSwitchOff
    public Integer findVisits() {
        System.out.println("Method findVisits executed");
        return 0;
    }

    @GreenConfigKey("keyConfigOtherProduct")
    @GreenReturnWhenSwitchOff
    public String findOtherProduct() {
        System.out.println("Method findOtherProduct executed");
        return "otherProduct";
    }
}
