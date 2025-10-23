package com.interviewprep;

import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;

import java.io.File;

public class Main {
    public static void main(String[] args) throws Exception {
        // Use Render's dynamic PORT environment variable
        String port = System.getenv().getOrDefault("PORT", "8080");

        Tomcat tomcat = new Tomcat();
        tomcat.setPort(Integer.parseInt(port));
        tomcat.setHostname("0.0.0.0");

        String webappDirLocation = "src/main/webapp/";
        File webAppDir = new File(webappDirLocation);
        if (!webAppDir.exists()) {
            System.err.println("⚠️ Webapp directory not found: " + webAppDir.getAbsolutePath());
        }

        StandardContext ctx = (StandardContext) tomcat.addWebapp("", webAppDir.getAbsolutePath());
        File additionWebInfClasses = new File("target/classes");

        WebResourceRoot resources = new StandardRoot(ctx);
        resources.addPreResources(new DirResourceSet(resources, "/WEB-INF/classes", additionWebInfClasses.getAbsolutePath(), "/"));
        ctx.setResources(resources);

        System.out.println("🌐 Starting Tomcat server on host 0.0.0.0, port " + port + " ...");
        tomcat.start();
        System.out.println("✅ Server started successfully and is ready to accept requests!");

        tomcat.getServer().await(); // Keeps process alive
    }
}
