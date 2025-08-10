package net.sf.esfinge.greenframework.core.integrationtest.sameclass.mock;

import net.sf.esfinge.greenframework.core.annotation.GreenConfigKey;
import net.sf.esfinge.greenframework.core.annotation.GreenDefaultReturn;
import net.sf.esfinge.greenframework.core.annotation.GreenSwitchOff;

public class RecommendationDAO {

    @GreenConfigKey("keyConfigProduct")
    @GreenSwitchOff
    @GreenDefaultReturn
    public String findProduct() {
        System.out.println("Method findProduct executed");
        return "product";
    }

    @GreenConfigKey("keyConfigVisits")
    @GreenSwitchOff
    @GreenDefaultReturn
    public Integer findVisits() {
        System.out.println("Method findVisits executed");
        return 0;
    }

    @GreenConfigKey("keyConfigOtherProduct")
    @GreenSwitchOff
    @GreenDefaultReturn
    public String findOtherProduct() {
        System.out.println("Method findOtherProduct executed");
        return "otherProduct";
    }
}
