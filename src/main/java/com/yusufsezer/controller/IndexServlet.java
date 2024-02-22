package com.yusufsezer.controller;

import com.yusufsezer.model.Contact;
import com.yusufsezer.util.ViewUtils;
import com.yusufsezer.util.WebUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@WebServlet("/")
public class IndexServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        var writer = response.getWriter();
        var repository = WebUtils.getRepository(getServletContext());
        Collection<Contact> contacts = repository.getAll();

        String contactsHTML = contactsToHTMLRow(contacts);
        String flashMessage = WebUtils.flashMessage(request, null);
        String flashMessageContent = "null".equals(flashMessage) ? "" : """
                                     <div class="alert alert-info" role="alert">
                                       %s
                                     </div>
                                     """.formatted(flashMessage);
        String pageContent = """
        <p><a href="add">Add New Contact</a></p>

                             %s

        <table class="table">
          <thead>
            <tr>
              <th>First Name</th>
              <th>Last Name</th>
              <th>Email</th>
              <th>Phone Number</th>
              <th>Address</th>
              <th>Web Address</th>
              <th>Notes</th>
              <th></th>
            </tr>
          </thead>
          <tbody>%s</tbody>
        </table>""".formatted(flashMessageContent, contactsHTML);
        String builtPage = ViewUtils.buildPage("Contact list", pageContent);

        writer.write(builtPage);
    }

    private String contactsToHTMLRow(Collection<Contact> contacts) {
        StringBuilder stringBuilder = new StringBuilder();

        for (Contact contact : contacts) {
            Object id = contact.getId();
            String firstName = contact.getFirstName();
            String lastName = contact.getLastName();
            String email = contact.getEmail();
            String phoneNumber = contact.getPhoneNumber();
            String address = contact.getAddress();
            String webAddress = contact.getWebAddress();
            String notes = contact.getNotes();

            String row = """
                   <tr>
                   <td>%s</td>
                   <td>%s</td>
                   <td>%s</td>
                   <td>%s</td>
                   <td>%s</td>
                   <td>%s</td>
                   <td>%s</td>
                   <td>
                   <a href="edit?id=%d">Edit</a> |
                   <a href="details?id=%d">Details</a> |
                   <a href="delete?id=%d" onclick="return confirm('Are you sure you want to delete this?')">Delete</a>
                   </td>
                   </tr>
                   """.formatted(firstName, lastName, email, phoneNumber, address, webAddress, notes, id, id, id);

            stringBuilder.append(row);
        }

        return stringBuilder.toString();
    }

}
