package com.yusufsezer.util;

import com.yusufsezer.contract.IRepository;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.Map;

public class WebUtils {

    private static final String FLASH_MESSAGE_ATTRIBUTE = "message";

    public static boolean checkParameters(String[] requiredParameters, Map<String, String[]> parameterMap) {
        for (String parameter : requiredParameters) {
            if (!parameterMap.containsKey(parameter)) {
                return false;
            }
        }
        return true;
    }

    public static IRepository getRepository(ServletContext servletContext) {
        return (IRepository) servletContext.getAttribute("repository");
    }

    public static String flashMessage(HttpServletRequest request, String message) {
        HttpSession session = request.getSession();
        String flashMessage = String.valueOf(session.getAttribute(FLASH_MESSAGE_ATTRIBUTE));

        if (!"null".equals(message)) {
            session.setAttribute(FLASH_MESSAGE_ATTRIBUTE, message);
        } else {
            flashMessage = String.valueOf(session.getAttribute(FLASH_MESSAGE_ATTRIBUTE));
            session.removeAttribute(FLASH_MESSAGE_ATTRIBUTE);
        }

        return flashMessage;
    }

}
