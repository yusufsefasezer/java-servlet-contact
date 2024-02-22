package com.yusufsezer.controller;

import com.yusufsezer.util.WebUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/delete")
public class DeleteServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = String.valueOf(request.getParameter("id"));
        boolean isNullOrNotANumber = "null".equals(idParam) || !idParam.matches("\\d+");

        try {
            if (isNullOrNotANumber) {
                throw new RuntimeException("Invalid or missing contact ID.");
            }
            var contactId = Integer.valueOf(idParam);
            var repository = WebUtils.getRepository(getServletContext());
            repository.remove(contactId);
            WebUtils.flashMessage(request, "Contact deleted successfully.");
        } catch (RuntimeException exception) {
            WebUtils.flashMessage(request, exception.getMessage());
        }

        response.sendRedirect(".");
    }

}
