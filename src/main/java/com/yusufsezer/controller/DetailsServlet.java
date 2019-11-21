package com.yusufsezer.controller;

import com.yusufsezer.contracts.IRepository;
import com.yusufsezer.helper.Helper;
import com.yusufsezer.model.Contact;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DetailsServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!request.getParameterMap().containsKey("id")) {
            response.sendRedirect(".");
        }

        IRepository<Contact, Integer> repository = Helper.getRepository(request);
        Integer id = Integer.parseInt(request.getParameter("id"));
        Contact currentContact = repository.get(id);

        if (currentContact == null) {
            response.sendRedirect(".");
        }

        String pageContent = "        <div>\n"
                + "\n"
                + "            <dl class=\"row\">\n"
                + "                <dt class=\"col-sm-2\">First Name</dt>\n"
                + "                <dd class=\"col-sm-10\">" + currentContact.getFirstName() + "</dd>\n"
                + "                <dt class=\"col-sm-2\">Last Name</dt>\n"
                + "                <dd class=\"col-sm-10\">" + currentContact.getLastName() + "</dd>\n"
                + "                <dt class=\"col-sm-2\">Email</dt>\n"
                + "                <dd class=\"col-sm-10\">" + currentContact.getEmail() + "</dd>\n"
                + "                <dt class=\"col-sm-2\">Phone Number</dt>\n"
                + "                <dd class=\"col-sm-10\">" + currentContact.getPhoneNumber() + "</dd>\n"
                + "                <dt class=\"col-sm-2\">Address</dt>\n"
                + "                <dd class=\"col-sm-10\">" + currentContact.getAddress() + "</dd>\n"
                + "                <dt class=\"col-sm-2\">Web Address</dt>\n"
                + "                <dd class=\"col-sm-10\">" + currentContact.getWebAddress() + "</dd>\n"
                + "                <dt class=\"col-sm-2\">Notes</dt>\n"
                + "                <dd class=\"col-sm-10\">" + currentContact.getNotes() + "</dd>\n"
                + "            </dl>\n"
                + "        </div>\n"
                + "        <div>\n"
                + "            <a href=\"edit?id=" + currentContact.getId() + "\">Edit</a> |\n"
                + "            <a href=\".\">Back to List</a>\n"
                + "        </div>";

        PrintWriter printWriter = response.getWriter();
        printWriter.write(Helper.generatePage("Details", pageContent));
    }

}
