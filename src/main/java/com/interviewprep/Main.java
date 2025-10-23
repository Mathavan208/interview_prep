package com.interviewprep;

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
        
        // THE CRITICAL FIX: Set the address to bind to all interfaces
        tomcat.setHostname("0.0.0.0");
        
        String webappDirLocation = "src/main/webapp/";
        StandardContext ctx = (StandardContext) tomcat.addWebapp("", new File(webappDirLocation).getAbsolutePath());
        
        File additionWebInfClasses = new File("target/classes");
        WebResourceRoot resources = new StandardRoot(ctx);
        resources.addPreResources(new DirResourceSet(resources, "/WEB-INF/classes", additionWebInfClasses.getAbsolutePath(), "/"));
        ctx.setResources(resources);
        
        System.out.println("Starting Tomcat server on host 0.0.0.0, port " + port + "...");
        
        tomcat.start();
        
        // Wait for the server to be fully started
        while (tomcat.getServer().getState() != org.apache.catalina.LifecycleState.STARTED) {
            Thread.sleep(1000);
        }
        
        System.out.println("Server started and is ready to accept requests.");
        
        tomcat.getServer().await();
    }
}