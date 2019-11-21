package com.yusufsezer.helper;

import com.yusufsezer.contracts.IRepository;
import com.yusufsezer.model.Contact;
import com.yusufsezer.repository.MySQLRepository;
import java.util.Map;
import javax.servlet.ServletRequest;

public class Helper {

    public final static String PROJECT_NAME = "Java Servlet Contact";
    public final static String REPOSITORY_NAME = "sqlite";
    public static String DB_URL = "";

    public static String header(String title) {
        return "<head>\n"
                + "    <meta charset=\"UTF-8\" />\n"
                + "    <title>" + title + " - " + PROJECT_NAME + "</title>\n"
                + "    <link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css\" />\n"
                + "</head>\n";
    }

    public static String generatePage(String pageTitle, String pageContent) {

        String generatedContent = "<!DOCTYPE html>\n"
                + "<html lang=\"en\">";

        generatedContent += header(pageTitle);

        generatedContent += "\n    <main role=\"main\" class=\" container pb-3\">"
                + "\n\n"
                + "        <h1>" + pageTitle + "</h1>\n"
                + "\n"
                + "        <h4>" + PROJECT_NAME + "</h4>\n"
                + "\n"
                + "        <hr />";

        generatedContent += pageContent;

        generatedContent += "\n\n    </main>\n"
                + "\n"
                + "</body>\n"
                + "\n"
                + "</html>";

        return generatedContent;
    }

    public static IRepository<Contact, Integer> createRepository() {
        return new MySQLRepository<>(DB_URL);
    }

    public static IRepository<Contact, Integer> getRepository(ServletRequest request) {
        return (IRepository<Contact, Integer>) request.getAttribute(REPOSITORY_NAME);
    }

    public static boolean checkParameters(String[] parameters, Map<String, String[]> parameterMap) {
        for (String parameter : parameters) {
            if (!parameterMap.containsKey(parameter)) {
                return false;
            }
        }
        return true;
    }

}
