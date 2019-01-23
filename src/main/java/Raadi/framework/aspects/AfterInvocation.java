package Raadi.framework.aspects;

import java.lang.reflect.Method;
import java.util.function.Consumer;

public class AfterInvocation<T> {

    private Consumer<T> lambda;
    private ExecutionContext executionContext;


    public AfterInvocation(Method method, Consumer<T> lambda)
    {
        this.executionContext = new ExecutionContext(method);
        this.lambda = lambda;
    }

    public void execute(T param) {
        this.executionContext.proceed();
        this.lambda.accept(param);
    }

}
