package net.sf.esfinge.greenframework.mock.service.sameclasstest;

import net.sf.esfinge.greenframework.annotation.GreenConfigKey;
import net.sf.esfinge.greenframework.annotation.GreenDefaultReturn;

public class RecommendationDAO {

    @GreenConfigKey("keyConfigProduct")
    @GreenDefaultReturn
    public String findProduct() {
        System.out.println("Method findProduct executed");
        return "product";
    }

    @GreenConfigKey("keyConfigVisits")
    @GreenDefaultReturn
    public Integer findVisits() {
        System.out.println("Method findVisits executed");
        return 0;
    }

    @GreenConfigKey("keyConfigOtherProduct")
    @GreenDefaultReturn
    public String findOtherProduct() {
        System.out.println("Method findOtherProduct executed");
        return "otherProduct";
    }
}
