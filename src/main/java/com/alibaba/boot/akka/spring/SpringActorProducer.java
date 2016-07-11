package com.alibaba.boot.akka.spring;

import akka.actor.Actor;
import akka.actor.IndirectActorProducer;
import org.springframework.context.ApplicationContext;

public class SpringActorProducer implements IndirectActorProducer {

    final ApplicationContext applicationContext;
    final String             actorBeanName;
    final Class<?>           requiredType;
    final Object[]           args;

    public SpringActorProducer(ApplicationContext applicationContext, String actorBeanName){
        this.applicationContext = applicationContext;
        this.actorBeanName = actorBeanName;
        this.requiredType = null;
        this.args = null;
    }

    public SpringActorProducer(ApplicationContext applicationContext, String actorBeanName, Object[] args){
        this.applicationContext = applicationContext;
        this.actorBeanName = actorBeanName;
        this.requiredType = null;
        this.args = args;
    }

    public SpringActorProducer(ApplicationContext applicationContext, Class<?> requiredType){
        this.applicationContext = applicationContext;
        this.actorBeanName = null;
        this.requiredType = requiredType;
        this.args = null;
    }

    public SpringActorProducer(ApplicationContext applicationContext, Class<?> requiredType, Object[] args){
        this.applicationContext = applicationContext;
        this.actorBeanName = null;
        this.requiredType = requiredType;
        this.args = args;
    }

    public SpringActorProducer(ApplicationContext applicationContext, String actorBeanName, Class<?> requiredType){
        this.applicationContext = applicationContext;
        this.actorBeanName = actorBeanName;
        this.requiredType = requiredType;
        this.args = null;
    }

    @Override
    public Actor produce() {
        Actor result;
        if (actorBeanName != null && requiredType != null) {
            result = (Actor) applicationContext.getBean(actorBeanName, requiredType);
        } else if (requiredType != null) {
            if (args == null) {
                result = (Actor) applicationContext.getBean(requiredType);
            } else {
                result = (Actor) applicationContext.getBean(requiredType, args);
            }
        } else {
            if (args == null) {
                result = (Actor) applicationContext.getBean(actorBeanName);
            } else {
                result = (Actor) applicationContext.getBean(actorBeanName, args);
            }

        }
        return result;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Class<? extends Actor> actorClass() {
        return (Class<? extends Actor>) (requiredType != null ? requiredType : applicationContext.getType(actorBeanName));
    }
}
