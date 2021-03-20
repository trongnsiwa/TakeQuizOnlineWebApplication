/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trongns.lsitener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * Web application lifecycle listener.
 *
 * @author TrongNS
 */
public class MyContextListener implements ServletContextListener {

    private final String MAPPING_FILE = "/WEB-INF/MappingPage.properties";
    static final Logger LOGGER = Logger.getLogger(MyContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Properties properties = new Properties();
        ServletContext context = sce.getServletContext();
        
        String log4jConfigFile  = context.getInitParameter("log4j-config-location");
        String fullPath = context.getRealPath("") + File.separator + log4jConfigFile;
        PropertyConfigurator.configure(fullPath);
        
        String realPath = context.getRealPath(MAPPING_FILE);

        InputStream inputStream = null;
        HashMap<String, String> map = new HashMap<>();

        try {
            inputStream = new FileInputStream(realPath);
            properties.load(inputStream);

            for (String name : properties.stringPropertyNames()) {
                map.put(name, properties.getProperty(name));
            }
        } catch (FileNotFoundException ex) {
            LOGGER.debug("MyContextListener _ FileNotFoundException : " + ex.getMessage());
        } catch (IOException ex) {
            LOGGER.debug("MyContextListener _ IOException : " + ex.getMessage());
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        context.setAttribute("MAPPING_LIST", map);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        if (context.getAttribute("MAPPING_LIST") != null) {
            context.removeAttribute("MAPPING_LIST");
        }
    }
}
