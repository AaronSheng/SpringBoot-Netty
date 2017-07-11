package com.aaron.handler;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

@Component
public class SocketHandlerFactory implements FactoryBean<SocketHandler> {
    @Override
    public SocketHandler getObject() throws Exception {
        return new SocketHandler();
    }

    @Override
    public Class<?> getObjectType() {
        return SocketHandler.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }
}
