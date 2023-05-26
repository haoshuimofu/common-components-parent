package com.demo.components.rabbitmq;

import com.demo.components.rabbitmq.bind.Binders;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author wude
 * @Create 2019-06-10 16:12
 */
public class MessageBinderMappingContainer {

    private final ConcurrentHashMap<Class, Binders> DECLARED_MESSAGE_BODY_BINDERS = new ConcurrentHashMap<>();

    public void putDeclaredBinders(Class messageClass, Binders binders) {
        DECLARED_MESSAGE_BODY_BINDERS.put(messageClass, binders);
    }

    public Binders getDeclaredBinders(Class messageClass) {
        return DECLARED_MESSAGE_BODY_BINDERS.get(messageClass);
    }

}