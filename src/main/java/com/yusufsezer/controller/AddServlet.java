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

public class AddServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter printWriter = response.getWriter();

        String pageContent = "\n\n        <form method=\"post\">\n"
                + "\n"
                + "            <div class=\"row\">\n"
                + "\n"
                + "                <div class=\"offset-md-2 col-md-4\">\n"
                + "\n"
                + "                    <div class=\"form-group\">\n"
                + "                        <label class=\"control-label\" for=\"FirstName\">First Name</label>\n"
                + "                        <input class=\"form-control\" type=\"text\" id=\"FirstName\" name=\"FirstName\" value=\"\" />\n"
                + "                    </div>\n"
                + "\n"
                + "                    <div class=\"form-group\">\n"
                + "                        <label class=\"control-label\" for=\"LastName\">Last Name</label>\n"
                + "                        <input class=\"form-control\" type=\"text\" id=\"LastName\" name=\"LastName\" value=\"\" />\n"
                + "                    </div>\n"
                + "\n"
                + "                    <div class=\"form-group\">\n"
                + "                        <label class=\"control-label\" for=\"Email\">Email</label>\n"
                + "                        <input class=\"form-control\" type=\"email\" id=\"Email\" name=\"Email\" value=\"\" />\n"
                + "                    </div>\n"
                + "\n"
                + "                    <div class=\"form-group\">\n"
                + "                        <label class=\"control-label\" for=\"PhoneNumber\">Phone Number</label>\n"
                + "                        <input class=\"form-control\" type=\"text\" id=\"PhoneNumber\" name=\"PhoneNumber\" value=\"\" />\n"
                + "                    </div>\n"
                + "\n"
                + "\n"
                + "                </div>\n"
                + "\n"
                + "                <div class=\"col-md-4\">\n"
                + "\n"
                + "                    <div class=\"form-group\">\n"
                + "                        <label class=\"control-label\" for=\"Address\">Address</label>\n"
                + "                        <textarea class=\"form-control\" id=\"Address\" name=\"Address\" cols=\"30\" rows=\"3\"></textarea>\n"
                + "                    </div>\n"
                + "\n"
                + "                    <div class=\"form-group\">\n"
                + "                        <label class=\"control-label\" for=\"WebAddress\">Web Address</label>\n"
                + "                        <input class=\"form-control\" type=\"url\" id=\"WebAddress\" name=\"WebAddress\" value=\"\" />\n"
                + "                    </div>\n"
                + "\n"
                + "                    <div class=\"form-group\">\n"
                + "                        <label class=\"control-label\" for=\"Notes\">Notes</label>\n"
                + "                        <textarea class=\"form-control\" id=\"Notes\" name=\"Notes\" cols=\"40\" rows=\"3\"></textarea>\n"
                + "                    </div>\n"
                + "\n"
                + "                    <div class=\"form-group\">\n"
                + "                        <input type=\"submit\" value=\"Add\" class=\"btn btn-block btn-primary\" />\n"
                + "                    </div>\n"
                + "\n"
                + "                </div>\n"
                + "\n"
                + "            </div>\n"
                + "\n"
                + "        </form>\n"
                + "\n"
                + "        <div><a href=\".\">Back to List</a></div>";

        printWriter.write(Helper.generatePage("Add", pageContent));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String[] parameters = {"FirstName", "LastName", "Email",
            "PhoneNumber", "Address", "WebAddress", "Notes"};

        boolean parameterResult = Helper
                .checkParameters(parameters, request.getParameterMap());

        if (!parameterResult) {
            response.sendRedirect(".");
        }

        IRepository<Contact, Integer> repository = Helper.getRepository(request);
        Contact newContact = new Contact();
        newContact.setFirstName(request.getParameter("FirstName"));
        newContact.setLastName(request.getParameter("LastName"));
        newContact.setEmail(request.getParameter("Email"));
        newContact.setPhoneNumber(request.getParameter("PhoneNumber"));
        newContact.setAddress(request.getParameter("Address"));
        newContact.setWebAddress(request.getParameter("WebAddress"));
        newContact.setNotes(request.getParameter("Notes"));

        repository.add(newContact);
        response.sendRedirect(".");

    }

}
