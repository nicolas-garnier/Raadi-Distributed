package Raadi.framework.provider;

import Raadi.framework.aspects.AfterInvocation;
import Raadi.framework.aspects.AroundInvocation;
import Raadi.framework.aspects.BeforeInvocation;

import java.util.function.Supplier;

public class Prototype<T> extends AbstractProvider<T> {
    public final Supplier<T> supplier;

    public Prototype(Supplier<T> supplier) {
        super();
        this.supplier = supplier;
    }

    public Prototype(Supplier<T> supplier, BeforeInvocation<T> beforeInvocation, AfterInvocation<T> afterInvocation, AroundInvocation aroundInvocation) {
        super(beforeInvocation, afterInvocation, aroundInvocation);
        this.supplier = supplier;
    }


    @Override
    public T getBean() {
        return supplier.get();
    }
}
