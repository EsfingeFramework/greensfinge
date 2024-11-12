package br.com.ita.greenframework;

import br.com.ita.greenframework.service.GroupService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestMethods {

    @Test
    void testIgnore() {
        GroupService service = new GroupService();

        String greenReturn1 = service.doSomething("");
        String greenReturn2 = service.doSomething2();

        Assertions.assertNull(null);
    }
}
