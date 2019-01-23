package Raadi.framework.aspects;

import java.lang.reflect.Method;
import java.util.function.Function;

public class AroundInvocation
{
    private ExecutionContext executionContext;
    private Function<ExecutionContext, Object> function;

    public AroundInvocation(Method method, Function<ExecutionContext, Object> function) {
        this.executionContext = new ExecutionContext(method);
        this.function = function;
    }

    public void execute() {
        function.apply(executionContext);
    }
}
