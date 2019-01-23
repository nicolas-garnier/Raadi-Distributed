package Raadi.framework;

import Raadi.framework.provider.Provider;
import Raadi.framework.provider.Singleton;
import Raadi.framework.scope.AnyScope;

import java.util.HashMap;
import java.util.Stack;
import java.util.function.Consumer;

public class RaadiFW<T> {

    Stack<AnyScope> stackScope;

    public RaadiFW() {
        stackScope = new Stack<>();
        stackScope.push(new AnyScope<T>());
    }

    public void closeScope() {
        stackScope.pop();
    }

    public void provider(Class<T> tClass, Provider<T> provider) {
        AnyScope<T> curr = stackScope.peek();

        HashMap<Class<T>, Provider<T>> hm = curr.getHashMapProviders();
        hm.put(tClass, provider);
        curr.setHashMapProviders(hm);
    }

    public void bean(Class<T> tClass, T tObject) {
        this.provider(tClass, new Singleton<>(tObject));
    }


    public void scope(AnyScope<T> scope, Consumer<AnyScope<T>> consumer) {
        consumer.accept(scope);
        stackScope.push(scope);
    }

    public T instanceOf(Class<T> tClass) {
        for (int i = stackScope.size() - 1; i >= 0; i--) {
            HashMap<Class<T>, Provider<T>> hm = stackScope.elementAt(i).getHashMapProviders();
            if (hm.containsKey(tClass)) {
                T res = hm.get(tClass).getBean();
                return res;
            }
        }
        return null;
    }
}
