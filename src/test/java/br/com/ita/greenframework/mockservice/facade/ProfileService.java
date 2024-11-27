package br.com.ita.greenframework.mockservice.facade;

import br.com.ita.greenframework.annotation.GreenDefault;
import br.com.ita.greenframework.annotation.GreenNumber;
import br.com.ita.greenframework.annotation.GreenOptional;
import br.com.ita.greenframework.mockservice.GroupService;
import lombok.Getter;

@Getter
public class ProfileService {

    @GreenOptional(configurationKey = @GreenDefault(configurationKey = "keyGroupService"))
    private GroupService groupService;

    private String testWithoutGreen;

    @GreenNumber(configurationKey = @GreenDefault(configurationKey = "keyEndCountPrimes"))
    private Integer mockValue;
}
