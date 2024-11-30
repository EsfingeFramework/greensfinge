package br.com.ita.greenframework.mockservice;

import br.com.ita.greenframework.annotation.GreenDefault;
import br.com.ita.greenframework.annotation.GreenOptional;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
public class UserService {

    @GreenOptional(value = @GreenDefault(configurationKey = "keyProfileService"))
    private ProfileService profileService = new ProfileService();

    public String getUserProfile() {
        return profileService.findProfile();
    }

    public String getUserProfileEmptyAnnotation() {
        return profileService.findProfileWithEmptyAnnotation();
    }

    public String getUserProfileWithValueAnnotation() {
        return profileService.findProfileWithValueAnnotation();
    }

    public String getProfileWithRuntimeError() {
        return profileService.getProfileWithRuntimeError();
    }

    public String getProfileWithError() throws Exception {
        return profileService.getProfileWithError();
    }

}
