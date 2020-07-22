package com.demo.components.rabbitmq.bind;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;

/**
 * 绑定对象组合
 *
 * @Author wude
 * @Create 2019-06-10 19:09
 */
public class Binders {

    private Exchange exchange;
    private Queue queue;
    private Binding binding;

    private DirectExchange dlxExchange;
    private Queue dlqQueue;
    private Binding dlxBinding;

    public Binders(Exchange exchange, Queue queue, Binding binding) {
        this.exchange = exchange;
        this.queue = queue;
        this.binding = binding;
    }

    public Exchange getExchange() {
        return exchange;
    }

    public void setExchange(Exchange exchange) {
        this.exchange = exchange;
    }

    public Queue getQueue() {
        return queue;
    }

    public void setQueue(Queue queue) {
        this.queue = queue;
    }

    public Binding getBinding() {
        return binding;
    }

    public void setBinding(Binding binding) {
        this.binding = binding;
    }

    public DirectExchange getDlxExchange() {
        return dlxExchange;
    }

    public void setDlxExchange(DirectExchange dlxExchange) {
        this.dlxExchange = dlxExchange;
    }

    public Queue getDlqQueue() {
        return dlqQueue;
    }

    public void setDlqQueue(Queue dlqQueue) {
        this.dlqQueue = dlqQueue;
    }

    public Binding getDlxBinding() {
        return dlxBinding;
    }

    public void setDlxBinding(Binding dlxBinding) {
        this.dlxBinding = dlxBinding;
    }
}