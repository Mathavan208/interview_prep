package com.interviewprep;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;

import java.io.File;

public class Main {
    public static void main(String[] args) throws Exception {
        String port = System.getenv().getOrDefault("PORT", "8080");
        
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(Integer.parseInt(port));
        
        String webappDirLocation = "src/main/webapp/";
        StandardContext ctx = (StandardContext) tomcat.addWebapp("", new File(webappDirLocation).getAbsolutePath());
        
        File additionWebInfClasses = new File("target/classes");
        WebResourceRoot resources = new StandardRoot(ctx);
        resources.addPreResources(new DirResourceSet(resources, "/WEB-INF/classes", additionWebInfClasses.getAbsolutePath(), "/"));
        ctx.setResources(resources);
        
        System.out.println("Starting Tomcat server...");
        
        tomcat.start();
        
        // Wait for the server to be fully started
        // This loop checks if the server state is STARTED
        while (tomcat.getServer().getState() != org.apache.catalina.LifecycleState.STARTED) {
            Thread.sleep(1000);
        }
        
        System.out.println("Server started on port " + port);
        System.out.println("Application is ready to accept requests.");
        
        tomcat.getServer().await();
    }
}