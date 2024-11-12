package br.com.ita.greenframework.aspect;

import br.com.ita.greenframework.annotation.GreenIgnore;
import br.com.ita.greenframework.util.Constants;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;

@Aspect
public class GreenInterceptorAspect {

    private String projectPackages;

    public GreenInterceptorAspect() {
        projectPackages = System.getenv("GREEN_PROJECT_PACKAGES");
        if (projectPackages == null || projectPackages.isEmpty()) {
            projectPackages = "br.com.ita.greenframework"; // Valor padrão
        }
    }

    @Around("execution(* *(..)) && !within(br.com.ita.greenframework.aspect.GreenInterceptorAspect)")
    public Object interceptAllMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        if (joinPoint.getSignature().getDeclaringType().getPackage().getName().startsWith(projectPackages)) {
            Method method = getMethodFromJoinPoint(joinPoint);
            if (method != null && method.isAnnotationPresent(GreenIgnore.class)) {
                GreenIgnore annotation = method.getAnnotation(GreenIgnore.class);

                if (annotation.ignore()) {
                    System.out.println("Execution skipped: " + method.getName());
                    return null;
                } else if(hasChangeValue(annotation)) {
                    Object[] newArgs = changeAttributeValue(method, joinPoint);
                    return joinPoint.proceed(newArgs);
                } else {
                    return joinPoint.proceed();
                }
            }

        }

        return joinPoint.proceed();
    }

    private boolean hasChangeValue(GreenIgnore annotation) {
        return !Constants.GREEN_IGNORE_DEFAULT_STR_VALUE.equals(annotation.strValue()) || Constants.GREEN_IGNORE_DEFAULT_INT_VALUE != annotation.numericValue();
    }

    private Object[] changeAttributeValue(Method method, ProceedingJoinPoint joinPoint) {
        GreenIgnore annotation = method.getAnnotation(GreenIgnore.class);

        Object newValue = null;
        if(!Constants.GREEN_IGNORE_DEFAULT_STR_VALUE.equals(annotation.strValue())) {
            newValue = annotation.strValue();
        } else if (Constants.GREEN_IGNORE_DEFAULT_INT_VALUE != annotation.numericValue()) {
            newValue = annotation.numericValue();
        }


        Object[] args = joinPoint.getArgs();
        if (args.length > 0 && newValue != null) {
            args[0] = newValue;
        }
        return args;
    }

    private Method getMethodFromJoinPoint(ProceedingJoinPoint joinPoint) {
        try {
            String methodName = joinPoint.getSignature().getName();
            Object objectClass = joinPoint.getTarget();
            if (Objects.nonNull(objectClass)) {
                return Arrays.stream(objectClass.getClass().getDeclaredMethods())
                        .filter(e -> e.getName().equals(methodName))
                        .findFirst()
                        .orElseGet(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
