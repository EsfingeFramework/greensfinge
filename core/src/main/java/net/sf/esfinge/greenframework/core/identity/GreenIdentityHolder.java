package net.sf.esfinge.greenframework.core.identity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GreenIdentityHolder {

    private static final ThreadLocal<GreenIdentityProvider> CONTEXT =
            ThreadLocal.withInitial(() -> () -> "default");

    public static void set(GreenIdentityProvider provider) {
        CONTEXT.set(provider);
    }

    public static GreenIdentityProvider get() {
        return CONTEXT.get();
    }

    public static void clear() {
        CONTEXT.remove();
    }
}
