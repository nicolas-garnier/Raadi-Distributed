package Raadi.framework.scope;

import Raadi.framework.aspects.AfterInvocation;
import Raadi.framework.aspects.AroundInvocation;
import Raadi.framework.aspects.BeforeInvocation;
import Raadi.framework.provider.Provider;
import Raadi.framework.provider.Singleton;

import java.util.HashMap;

public class AnyScope<T> {

    protected HashMap<Class<T>, Provider<T>> hashMapProviders;

    public AnyScope() {
        hashMapProviders = new HashMap<>();
    }

    /**
     *      Getter / Setter
     * */
    public HashMap<Class<T>, Provider<T>> getHashMapProviders() {
        return hashMapProviders;
    }

    public void setHashMapProviders(HashMap<Class<T>, Provider<T>> hashMapProviders) {
        this.hashMapProviders = hashMapProviders;
    }

    /**
     *
     * @param tClass
     * @param object
     * @param beforeInvocation
     * @param afterInvocation
     * @param aroundInvocation
     */
    public void bean(Class<T> tClass, T object, BeforeInvocation beforeInvocation, AroundInvocation aroundInvocation, AfterInvocation afterInvocation) {
        Singleton<T> singleton = new Singleton<>(object, beforeInvocation, afterInvocation, aroundInvocation);
        hashMapProviders.put(tClass, singleton);
    }
}
