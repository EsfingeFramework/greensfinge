package net.sf.esfinge.greenframework.core.integrationtest;

import net.sf.esfinge.greenframework.core.configuration.facade.GreenConfigurationFacade;
import net.sf.esfinge.greenframework.core.dto.annotation.GreenSwitchConfiguration;
import net.sf.esfinge.greenframework.core.mock.service.sameclasstest.RecommendationService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IntegrationSameClassTest {

    @Test
    void testShouldFindRecommendationWithoutGreenConfiguration() {
        RecommendationService recommendationService = new RecommendationService();

        StringBuilder sb = new StringBuilder();
        recommendationService.findRecommendation(sb);

        assertEquals("null received null visits this month.\n Also check out null!", sb.toString());
    }

    @Test
    void testShouldFindRecommendationWithGreenAndNotIgnoreConfiguration() {
        RecommendationService recommendationService = new RecommendationService();

        GreenConfigurationFacade facade = new GreenConfigurationFacade();
        facade.setGeneralConfiguration(GreenSwitchConfiguration.builder()
                .ignore(false)
                .configurationKey("keyConfigProduct")
                .build());

        facade.setGeneralConfiguration(GreenSwitchConfiguration.builder()
                .ignore(false)
                .configurationKey("keyConfigVisits")
                .build());

        facade.setGeneralConfiguration(GreenSwitchConfiguration.builder()
                .ignore(false)
                .configurationKey("keyConfigOtherProduct")
                .build());
        StringBuilder sb = new StringBuilder();
        recommendationService.findRecommendation(sb);

        assertEquals("product received 0 visits this month.\n Also check out otherProduct!", sb.toString());
    }

    @Test
    void testShouldFindRecommendationWithGreenFrameworkDefaultValue() {
        RecommendationService recommendationService = new RecommendationService();

        GreenConfigurationFacade facade = new GreenConfigurationFacade();
        facade.setGeneralConfiguration(GreenSwitchConfiguration.builder()
                .ignore(true)
                .configurationKey("keyConfigProduct")
                .build());

        facade.setGeneralConfiguration(GreenSwitchConfiguration.builder()
                .ignore(true)
                .configurationKey("keyConfigVisits")
                .build());

        facade.setGeneralConfiguration(GreenSwitchConfiguration.builder()
                .ignore(true)
                .configurationKey("keyConfigOtherProduct")
                .build());

        StringBuilder sb = new StringBuilder();
        recommendationService.findRecommendation(sb);

        assertEquals("null received null visits this month.\n Also check out null!", sb.toString());
    }

    @Test
    void testShouldFindRecommendationWithGreenFrameworkChangeDefaultValue() {
        RecommendationService recommendationService = new RecommendationService();

        GreenConfigurationFacade facade = new GreenConfigurationFacade();
        facade.setGeneralConfiguration(GreenSwitchConfiguration.builder()
                .ignore(true)
                .configurationKey("keyConfigProduct")
                .strDefaultValue("configurationFromProduct")
                .build());

        facade.setGeneralConfiguration(GreenSwitchConfiguration.builder()
                .ignore(true)
                .configurationKey("keyConfigVisits")
                .numberDefaultValue(26101990D)
                .build());

        facade.setGeneralConfiguration(GreenSwitchConfiguration.builder()
                .ignore(true)
                .configurationKey("keyConfigOtherProduct")
                .strDefaultValue("configurationFromOtherProduct")
                .build());

        StringBuilder sb = new StringBuilder();
        recommendationService.findRecommendation(sb);

        assertEquals("configurationFromProduct received 26101990 visits this month.\n Also check out configurationFromOtherProduct!", sb.toString());
    }
}
