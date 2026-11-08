package com.se.demorestdb;

import jakarta.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("/api")
public class RestApplicationConfig extends ResourceConfig {
    public RestApplicationConfig() {

        packages("com.se.demorestdb.resource");

//        register(new AbstractBinder() {
//            @Override
//            protected void configure() {
//                bind(EmployeeRepoImpl.class).to(EmployeeRepo.class);
//                bind(EmployeeService.class).to(EmployeeService.class);
//            }
//        });
    }
}

