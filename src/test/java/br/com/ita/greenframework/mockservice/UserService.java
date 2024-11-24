package br.com.ita.greenframework.mockservice;

import br.com.ita.greenframework.annotation.GreenDefault;
import br.com.ita.greenframework.annotation.GreenNumber;
import br.com.ita.greenframework.annotation.GreenOptional;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Getter
@Setter
public class UserService {

    @GreenOptional(configurationKey = @GreenDefault(configurationKey = "keyGroupService"))
    private GroupService groupService = new GroupService();

    @GreenOptional(configurationKey = @GreenDefault(configurationKey = "keyGroupServiceMock"), strDefaultValue = "Mock value from annotation", numberDefaultValue = 998756424)
    private GroupService groupServiceMock = new GroupService();

    @GreenOptional(configurationKey = @GreenDefault(configurationKey = "keyMathService"))
    private MathService mathService = new MathService();

    @GreenNumber(configurationKey = @GreenDefault(configurationKey = "keyBeginCountPrimes"))
    private Integer beginCountPrimes = 0;

    @GreenNumber(configurationKey = @GreenDefault(configurationKey = "keyEndCountPrimes"))
    private Integer endCountPrimes = 0;

    public Map<String, Object> testIgnoreAnnotation() {
        Map<String, Object> map = new HashMap<>();

        map.put("test1", groupService.doSomething("Test"));
        map.put("test2", beginCountPrimes);
        map.put("test3", endCountPrimes);
        map.put("test4", mathService.countPrimesInRange(beginCountPrimes, endCountPrimes));

        return map;
    }

    public List<String> testSameMethodCallWithGreen() {
        List<String> list = new ArrayList<>();

        for (int i = 0; i < beginCountPrimes; i++) {
            list.add(groupService.doSomething4(i));
        }

        return list;
    }

    public List<String> testSameMethodCall() {
        List<String> list = new ArrayList<>();

        for (int i = 0; i < beginCountPrimes; i++) {
            list.add(groupService.doSomething3(i));
        }

        return list;
    }

    public Map<String, Object> testMockValueAnnotation() {
        Map<String, Object> map = new HashMap<>();

        map.put("test1", groupServiceMock.doSomething("Test"));
        map.put("test2", groupServiceMock.doSomething5(8));

        return map;
    }
}
