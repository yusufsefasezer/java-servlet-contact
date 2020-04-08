package com.yusufsezer.controller;

import com.yusufsezer.contracts.IRepository;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.yusufsezer.helper.Helper;
import com.yusufsezer.model.Contact;
import java.util.List;

public class IndexServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter printWriter = response.getWriter();
        IRepository<Contact, Integer> repository = Helper.getRepository(request);
        String pageContent = "<p><a href=\"add\">Add New Contact</a></p>\n"
                + "\n"
                + "<table class=\"table\">\n"
                + "    <thead>\n"
                + "        <tr>\n"
                + "            <th>First Name</th>\n"
                + "            <th>Last Name</th>\n"
                + "            <th>Email</th>\n"
                + "            <th>Phone Number</th>\n"
                + "            <th>Address</th>\n"
                + "            <th>Web Address</th>\n"
                + "            <th>Notes</th>\n"
                + "            <th></th>\n"
                + "        </tr>\n"
                + "    </thead>\n"
                + "\n"
                + "    <tbody>\n"
                + getContactList(repository.getAll())
                + "\n"
                + "    </tbody>\n"
                + "</table>"
                + "\n";

        printWriter.write(Helper.generatePage("Contact list", pageContent));
    }

    protected String getContactList(List<Contact> contacts) {
        String row = "";
        for (Contact contact : contacts) {
            row += "        <tr>\n"
                    + "            <td>" + contact.getFirstName() + "</td>\n"
                    + "            <td>" + contact.getLastName() + "</td>\n"
                    + "            <td>" + contact.getEmail() + "</td>\n"
                    + "            <td>" + contact.getPhoneNumber() + "</td>\n"
                    + "            <td>" + contact.getAddress() + "</td>\n"
                    + "            <td>" + contact.getWebAddress() + "</td>\n"
                    + "            <td>" + contact.getNotes() + "</td>\n"
                    + "            <td>\n"
                    + "                <a href=\"edit?id=" + contact.getId() + "\">Edit</a> |\n"
                    + "                <a href=\"details?id=" + contact.getId() + "\">Details</a> |\n"
                    + "                <a href=\"delete?id=" + contact.getId() + "\" onclick=\"return confirm('Are you sure you want to delete this?')\">Delete</a>\n"
                    + "            </td>\n"
                    + "        </tr>";
        }
        return row;
    }

}
