package net.sf.esfinge.greenframework.core.dao.contract;

import net.sf.esfinge.greenframework.core.dto.annotation.GreenDefaultConfiguration;
import net.sf.esfinge.greenframework.core.dto.project.GreenConfiguration;

public interface GreenConfigurationDao {

    void setGeneralConfiguration(String configurationKey, GreenDefaultConfiguration config);

    GreenConfiguration getConfiguration(String value);
}
