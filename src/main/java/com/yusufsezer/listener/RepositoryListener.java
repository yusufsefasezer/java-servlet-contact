package com.yusufsezer.listener;

import com.yusufsezer.contract.IRepository;
import com.yusufsezer.repository.MySQLRepository;
import com.yusufsezer.repository.ObjectRepository;
import com.yusufsezer.repository.SQLiteRepository;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;

@WebListener
public class RepositoryListener implements ServletContextListener {

    private Connection connection;
    private IRepository repository;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        var servletContext = servletContextEvent.getServletContext();
        String repositoryType = getParameter(servletContext, "repository.type", "mysql");
        String databaseClass = getParameter(servletContext, "database.class", "com.mysql.cj.jdbc.Driver");
        String databaseUrl = getParameter(servletContext, "database.url", "jdbc:mysql://localhost:3306/contact?useSSL=false&amp;serverTimezone=UTC&amp;");
        String databaseUser = getParameter(servletContext, "database.user", "root");
        String databasePassword = getParameter(servletContext, "database.password", "root");

        try {
            Class.forName(databaseClass);
            connection = DriverManager.getConnection(databaseUrl, databaseUser, databasePassword);
        } catch (ClassNotFoundException | SQLException ex) {
            repositoryType = "object"; // default repository
            servletContext.log(RepositoryListener.class.getName(), ex);
        }

        repository = createRepository(repositoryType, connection);
        servletContext.setAttribute("repository", repository);
    }

    private static String getParameter(ServletContext servletContext, String key, String defaultValue) {
        String value = String.valueOf(servletContext.getInitParameter(key));
        return "null".equals(value) ? defaultValue : value;
    }

    private IRepository createRepository(String repositoryType, Connection connection) {
        return switch (repositoryType.toLowerCase()) {
            case "mysql" ->
                new MySQLRepository(connection);
            case "sqlite" ->
                new SQLiteRepository(connection);
            default ->
                new ObjectRepository(new ConcurrentHashMap());
        };
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        try {
            if (null != connection) {
                connection.close();
            }
            repository = null;
        } catch (SQLException ex) {
            servletContextEvent.getServletContext()
                    .log(RepositoryListener.class.getName(), ex);
        }
    }

}
