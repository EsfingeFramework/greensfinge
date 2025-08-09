package net.sf.esfinge.greenframework.core.util;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class GreenReflectionUtilTest {

    static class DummyBean {
        private String value;
    }

    static class ProxyBean$$Mock {
        private String someField;
        private DummyBean originalBean;
    }

    static class ParentBean {
        private DummyBean dummyBean = new DummyBean();
    }

    @Test
    void testInjectValue() throws Exception {
        DummyBean bean = new DummyBean();
        Field field = DummyBean.class.getDeclaredField("value");

        GreenReflectionUtil.injectValue(field, bean, "test");
        assertEquals("test", GreenReflectionUtil.getFieldValue(field, bean));
    }

    @Test
    void testGetFieldValue() throws Exception {
        DummyBean bean = new DummyBean();
        Field field = DummyBean.class.getDeclaredField("value");

        field.setAccessible(true);
        field.set(bean, "hello");

        Object result = GreenReflectionUtil.getFieldValue(field, bean);
        assertEquals("hello", result);
    }

    @Test
    void testInjectOriginalBeanIntoProxy_Success() throws Exception {
        ProxyBean$$Mock proxy = new ProxyBean$$Mock();
        ParentBean parent = new ParentBean();
        Field proxyField = ParentBean.class.getDeclaredField("dummyBean");

        GreenReflectionUtil.injectOriginalBeanIntoProxy(proxy, proxyField, parent);

        Field originalBeanField = ProxyBean$$Mock.class.getDeclaredField(GreenConstant.FIELD_ORIGINAL_BEAN);
        originalBeanField.setAccessible(true);

        Object injected = originalBeanField.get(proxy);
        assertNotNull(injected);
        assertEquals(parent.dummyBean, injected);
    }

    @Test
    void testInjectOriginalBeanIntoProxy_FieldNotFound_ShouldThrow() throws NoSuchFieldException {
        class CustomType {} // type that will not be found in the parent

        class ProxyWithField {
            private Object originalBean;
        }

        class ParentWithoutMatchingField {
            private String notAMatch;
        }

        ProxyWithField proxy = new ProxyWithField();

        // Create a dummy Field with CustomType type to simulate the proxyField
        Field dummyField = CustomFieldProvider.class.getDeclaredField("customTypeField");

        IllegalStateException ex = assertThrows(IllegalStateException.class, () ->
                GreenReflectionUtil.injectOriginalBeanIntoProxy(proxy, dummyField, new ParentWithoutMatchingField())
        );

        assertTrue(ex.getMessage().contains("Nenhum campo do tipo"));
    }

    static class CustomFieldProvider {
        private static final CustomType customTypeField = new CustomType();
        static class CustomType {}
    }

    @Test
    void testResolveInjectionTarget_WithNonProxy() {
        DummyBean bean = new DummyBean();
        Object result = GreenReflectionUtil.resolveInjectionTarget(bean);
        assertSame(bean, result);
    }

    @Test
    void testResolveInjectionTarget_WithProxy() throws Exception {
        ProxyBean$$Mock proxy = new ProxyBean$$Mock();
        DummyBean original = new DummyBean();

        Field field = ProxyBean$$Mock.class.getDeclaredField(GreenConstant.FIELD_ORIGINAL_BEAN);
        field.setAccessible(true);
        field.set(proxy, original);

        Object result = GreenReflectionUtil.resolveInjectionTarget(proxy);
        assertSame(original, result);
    }
}
