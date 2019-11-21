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

public class EditServlet extends HttpServlet {

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

        String pageContent = "\n\n        <form method=\"post\">\n"
                + "\n"
                + "            <div class=\"row\">\n"
                + "\n"
                + "                <div class=\"offset-md-2 col-md-4\">\n"
                + "\n"
                + "                    <div class=\"form-group\">\n"
                + "                        <label class=\"control-label\" for=\"FirstName\">First name</label>\n"
                + "                        <input class=\"form-control\" type=\"text\" id=\"FirstName\" name=\"FirstName\" value=\"" + currentContact.getFirstName() + "\" />\n"
                + "                    </div>\n"
                + "\n"
                + "                    <div class=\"form-group\">\n"
                + "                        <label class=\"control-label\" for=\"LastName\">Last name</label>\n"
                + "                        <input class=\"form-control\" type=\"text\" id=\"LastName\" name=\"LastName\" value=\"" + currentContact.getLastName() + "\" />\n"
                + "                    </div>\n"
                + "\n"
                + "                    <div class=\"form-group\">\n"
                + "                        <label class=\"control-label\" for=\"Email\">Email</label>\n"
                + "                        <input class=\"form-control\" type=\"email\" id=\"Email\" name=\"Email\" value=\"" + currentContact.getEmail() + "\" />\n"
                + "                    </div>\n"
                + "\n"
                + "                    <div class=\"form-group\">\n"
                + "                        <label class=\"control-label\" for=\"PhoneNumber\">Phone number</label>\n"
                + "                        <input class=\"form-control\" type=\"text\" id=\"PhoneNumber\" name=\"PhoneNumber\" value=\"" + currentContact.getPhoneNumber() + "\" />\n"
                + "                    </div>\n"
                + "\n"
                + "                </div>\n"
                + "\n"
                + "                <div class=\"col-md-4\">\n"
                + "\n"
                + "                    <div class=\"form-group\">\n"
                + "                        <label class=\"control-label\" for=\"Address\">Address</label>\n"
                + "                        <textarea class=\"form-control\" id=\"Address\" name=\"Address\" cols=\"30\" rows=\"3\">" + currentContact.getAddress() + "</textarea>\n"
                + "                    </div>\n"
                + "\n"
                + "                    <div class=\"form-group\">\n"
                + "                        <label class=\"control-label\" for=\"WebAddress\">Web address</label>\n"
                + "                        <input class=\"form-control\" type=\"url\" id=\"WebAddress\" name=\"WebAddress\" value=\"" + currentContact.getWebAddress() + "\" />\n"
                + "                    </div>\n"
                + "\n"
                + "                    <div class=\"form-group\">\n"
                + "                        <label class=\"control-label\" for=\"Notes\">Notes</label>\n"
                + "                        <textarea class=\"form-control\" id=\"Notes\" name=\"Notes\" cols=\"40\" rows=\"3\">" + currentContact.getNotes() + "</textarea>\n"
                + "                    </div>\n"
                + "\n"
                + "                    <div class=\"form-group\">\n"
                + "                        <input type=\"submit\" value=\"Update\" class=\"btn btn-block btn-primary\" />\n"
                + "                    </div>\n"
                + "\n"
                + "                </div>\n"
                + "\n"
                + "            </div>\n"
                + "\n"
                + "        </form>\n"
                + "\n"
                + "        <div><a href=\".\">Back to List</a></div>";

        PrintWriter printWriter = response.getWriter();
        printWriter.write(Helper.generatePage("Edit", pageContent));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String[] parameters = {"id", "FirstName", "LastName", "Email",
            "PhoneNumber", "Address", "WebAddress", "Notes"};
        
        boolean parameterResult = Helper
                .checkParameters(parameters, request.getParameterMap());

        if (!parameterResult) {
            response.sendRedirect(".");
        }

        IRepository<Contact, Integer> repository = Helper.getRepository(request);
        Integer id = Integer.parseInt(request.getParameter("id"));
        Contact currentContact = repository.get(id);

        if (currentContact == null) {
            response.sendRedirect(".");
        }

        currentContact.setFirstName(request.getParameter("FirstName"));
        currentContact.setLastName(request.getParameter("LastName"));
        currentContact.setEmail(request.getParameter("Email"));
        currentContact.setPhoneNumber(request.getParameter("PhoneNumber"));
        currentContact.setAddress(request.getParameter("Address"));
        currentContact.setWebAddress(request.getParameter("WebAddress"));
        currentContact.setNotes(request.getParameter("Notes"));

        repository.update(id, currentContact);
        response.sendRedirect(".");

    }

}
