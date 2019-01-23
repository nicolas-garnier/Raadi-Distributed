package Raadi.framework.aspects;

import java.lang.reflect.Method;
import java.util.function.Consumer;

public class BeforeInvocation<T> {

    private Consumer<T> lambda;
    private ExecutionContext executionContext;

    public BeforeInvocation(Method method, Consumer<T> lambda) {
        this.executionContext = new ExecutionContext(method);
        this.lambda = lambda;
    }

    public void execute(T param) {
        this.lambda.accept(param);
        this.executionContext.proceed();
    }
}
