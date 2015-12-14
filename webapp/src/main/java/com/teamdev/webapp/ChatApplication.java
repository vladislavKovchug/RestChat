package com.teamdev.webapp;


import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

public class ChatApplication extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<Class<?>>();
        classes.add(RESTController.class);

        return classes;
    }
}