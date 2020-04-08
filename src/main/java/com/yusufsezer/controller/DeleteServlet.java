package com.yusufsezer.controller;

import com.yusufsezer.contracts.IRepository;
import com.yusufsezer.helper.Helper;
import com.yusufsezer.model.Contact;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!request.getParameterMap().containsKey("id")) {
            response.sendRedirect(".");
        }

        Integer id = Integer.parseInt(request.getParameter("id"));
        IRepository<Contact, Integer> repository = Helper.getRepository(request);
        repository.remove(id);
        response.sendRedirect(".");

    }

}
