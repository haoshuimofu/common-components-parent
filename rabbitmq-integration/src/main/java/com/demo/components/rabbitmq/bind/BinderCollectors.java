package com.demo.components.rabbitmq.bind;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author wude
 * @Create 2019-06-10 16:12
 */
public class BinderCollectors {

    private final ConcurrentHashMap<Class, Binders> DECLARED_MESSAGE_BODY_BINDERS = new ConcurrentHashMap<>();

    public void putDeclaredBinders(Class messageBodyClass, Binders binders) {
        DECLARED_MESSAGE_BODY_BINDERS.put(messageBodyClass, binders);
    }

    public Binders getDeclaredBinders(Class messageBodyClass) {
        return DECLARED_MESSAGE_BODY_BINDERS.get(messageBodyClass);
    }

}