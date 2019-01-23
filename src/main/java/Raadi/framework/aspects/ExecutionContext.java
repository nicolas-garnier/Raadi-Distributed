package Raadi.framework.aspects;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ExecutionContext {
    public Method method;

    public ExecutionContext(Method method) {
        this.method = method;
    }

    public Object proceed() {
        try {
            return this.method.invoke(new Object());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
