package net.sf.esfinge.greenframework.core.helper;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.jeasy.random.EasyRandom;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class EasyRandomHelperTest {

    private static final EasyRandom easyRandom = new EasyRandom();

    public static <T> T nextObject(Class<T> clazz) {
        return easyRandom.nextObject(clazz);
    }

}
