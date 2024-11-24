# Green Esfinge Framework
The idea of this project is to transform the software into a sustainable tool using the concepts of Green in Software, in this way we are aligning technology and sustainability to optimize energy consumption in applications. The framework allows you to adjust resources in an efficient and personalized way, dynamically deactivating components that are not in use, without compromising existing behavior or business rules, integrating sustainability into the software in a simple and transparent way.

# How to evolve the framework ?

1. Create your annotation in the "br.com.ita.green framework.annotation" package

2. Create the class to execute the rules in the "br.com.ita.greenframework.configuration.interceptorprocessor" package

3. Add the class created in br.com.ita.greenframework.configuration.interceptorprocessor.GreenStrategyProcessor#populateData

4. Now just write down the annotation in the correct place and test.

5. For more information, use the annotation @GreenOptional

## Environments

Currently the project has the following variables

| Env                      | Tipo     | Description                               | Default Value             |
|--------------------------|----------|-------------------------------------------|---------------------------|
| `GREEN_PERSISTENCE_TYPE` | `string` | Memory only                               | Memory                    | 
| `GREEN_SCAN_PACKAGE`     | `string` | Application package that will be analyzed | default framework package |

# How to use the framework ?

The main idea of the framework is to change the behavior of the application at runtime without changing the existing code. And for this there are 2 annotations to carry out this work.

@GreenOptional - If you enable it, it will ignore the execution of the current method and return a default value as you specified.

@GreenNumber - If you use it, it will change the value of the attribute, (Integer or Long) during execution.

@GreenMetric - When the method is noted, the framework will check how much was saved, based on the number of times it was called.

<ins>**_It is important to emphasize that the value you will inform will be in the Joule magnitude measure, Because we use [JoularJx](https://www.noureddine.org/research/joular/joularjx) as a base_**</ins>


Below is an example of how to use it.


## Usage/Examples

First, we annotated the attributes in the class, below is an example of use.

```java
import br.com.ita.greenframework.annotation.GreenDefault;
import br.com.ita.greenframework.annotation.GreenNumber;
import br.com.ita.greenframework.annotation.GreenOptional;

public class UserService {
    @GreenOptional(configurationKey = @GreenDefault(configurationKey = "keyProfileService"))
    private ProfileService profileService = new ProfileService();

    @GreenOptional(configurationKey = @GreenDefault(configurationKey = "keyMathService"))
    private MathService mathService = new MathService();

    @GreenNumber(configurationKey = @GreenDefault(configurationKey = "keyBeginCountPrimes"))
    private Integer beginCountPrimes = 50;

    @GreenNumber(configurationKey = @GreenDefault(configurationKey = "keyEndCountPrimes"))
    private Integer endCountPrimes = 0;

    public void createUser() {
        groupService.doSomething2();
        groupService.doSomething2();
        groupService.doSomething2();
        String return1 = profileService.doSomething6("OtherTest");
        Long return2 = mathService.countPrimesInRange(beginCountPrimes, endCountPrimes);

        System.out.println("Call: groupService.doSomething0() - return: void");
        System.out.println("Call: profileService.doSomething6(\"OtherTest\") - return: " + return1);
        System.out.println("Call: mathService.countPrimesInRange(" + beginCountPrimes + ", " + endCountPrimes + ") - return: " + return2);
        System.out.println("Finish UserService - createUser\n\n");
    }
}

import br.com.ita.greenframework.annotation.GreenMetric;

public class ProfileService {

    @GreenMetric(metricSavedValue = 5.78)
    public String doSomething6(String otherTest) {
        String value = "ProfileService - doSomething6 - " + otherTest;
        System.out.println(value);
        return value;
    }
}

import br.com.ita.greenframework.annotation.GreenMetric;

public class GroupService {

    @GreenMetric(metricSavedValue = 2.788)
    public String doSomething2() {
        String value = "GroupService - doSomething2";
        log.info(value);
        return value;
    }

}

import br.com.ita.greenframework.annotation.GreenMetric;

public class MathService {

    @GreenMetric(metricSavedValue = 7.89)
    public Long countPrimesInRange(Integer beginCountPrimes, Integer endCountPrimes) {
        return LongStream.range(beginCountPrimes, endCountPrimes)
                .filter(MathService::isPrime)
                .count();
    }

    private static boolean isPrime(long number) {
        for (long factor = 2; factor * factor <= number; factor++) {
            if (number % factor == 0) {
                return false;
            }
        }
        return true;
    }
}

```
Then we create the configurations, it is important that the "configurationKey" information is the **same value as both the configuration and the annotation**, an example below

```java
import br.com.ita.greenframework.configuration.facade.GreenConfigurationFacade;

GreenConfigurationFacade facade = new GreenConfigurationFacade();

//@GreenOptional
        facade.setGeneralConfiguration(GreenOptionalConfiguration.builder()
        .ignore(true)
        .numberDefaultValue(5612)
        .configurationKey("keyGroupService")
        .build());

//@GreenNumber
        facade.setGeneralConfiguration(GreenNumberConfiguration.builder()
        .configurationKey("keyBeginCountPrimes")
        .value(2)
        .build());
```
And finally, create the object instance using 'GreenFactory.greenify' and call the method.

```java
import UserService;
import br.com.ita.greenframework.configuration.GreenFactory;
import br.com.ita.greenframework.configuration.facade.GreenMetricFacade;

UserService service = GreenFactory.greenify(UserService.class);
        service.createUser();

        GreenMetricFacade metricFacade = new GreenMetricFacade();
        metricFacade.getSavedEnergy().forEach(metric -> System.out.println(metric.toString()));
```
The output should be something similar to

GreenMetric(method=MathService#countPrimesInRange, containerField=ContainerField(attributeName=mathService, hasGreenAnnotation=true, annotationField=GreenOptional, annotationValue={configurationKey=keyMathService, strDefaultValue=greenDefaultValue, numberDefaultValue=999999999}), greenMetricAnnotation=@br.com.ita.greenframework.annotation.GreenMetric(metricSavedValue=7.89), countCalled=1, getSavedValue=7.89)


GreenMetric(method=GroupService#doSomething2, containerField=ContainerField(attributeName=groupService, hasGreenAnnotation=true, annotationField=GreenOptional, annotationValue={configurationKey=keyGroupService, strDefaultValue=greenDefaultValue, numberDefaultValue=999999999}), greenMetricAnnotation=@br.com.ita.greenframework.annotation.GreenMetric(metricSavedValue=2.788), countCalled=3, getSavedValue=8.363999999999999)


GreenMetric(method=ProfileService#doSomething6, containerField=ContainerField(attributeName=profileService, hasGreenAnnotation=true, annotationField=GreenOptional, annotationValue={configurationKey=keyProfileService, numberDefaultValue=999999999}), greenMetricAnnotation=@br.com.ita.greenframework.annotation.GreenMetric(metricSavedValue=5.78), countCalled=1, getSavedValue=5.78)

## Referência

- [Awesome Readme Templates](https://awesomeopensource.com/project/elangosundar/awesome-README-templates)
- [Awesome README](https://github.com/matiassingers/awesome-readme)
- [How to write a Good readme](https://bulldogjob.com/news/449-how-to-write-a-good-readme-for-your-github-project)

