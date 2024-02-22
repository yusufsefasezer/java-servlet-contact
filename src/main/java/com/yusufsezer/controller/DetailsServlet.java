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
import java.util.Optional;

@WebServlet("/details")
public class DetailsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        var writer = response.getWriter();
        String idParam = String.valueOf(request.getParameter("id"));
        boolean isNullOrNotANumber = "null".equals(idParam) || !idParam.matches("\\d+");
        Optional<Contact> foundContact;

        try {
            if (isNullOrNotANumber) {
                throw new RuntimeException("Invalid or missing contact ID.");
            }
            var contactId = Integer.valueOf(idParam);
            var repository = WebUtils.getRepository(getServletContext());
            foundContact = repository.get(contactId);
            if (foundContact.isEmpty()) {
                throw new RuntimeException("No record found.");
            }
        } catch (RuntimeException exception) {
            WebUtils.flashMessage(request, exception.getMessage());
            response.sendRedirect(".");
            return;
        }

        Contact contact = foundContact.get();
        Object id = contact.getId();
        String firstName = contact.getFirstName();
        String lastName = contact.getLastName();
        String email = contact.getEmail();
        String phoneNumber = contact.getPhoneNumber();
        String address = contact.getAddress();
        String webAddress = contact.getWebAddress();
        String notes = contact.getNotes();

        String pageContent = """
    <div>
      <dl class="row">
        <dt class="col-sm-2">First Name</dt>
        <dd class="col-sm-10">%s</dd>
        <dt class="col-sm-2">Last Name</dt>
        <dd class="col-sm-10">%s</dd>
        <dt class="col-sm-2">Email</dt>
        <dd class="col-sm-10">%s</dd>
        <dt class="col-sm-2">Phone Number</dt>
        <dd class="col-sm-10">%s</dd>
        <dt class="col-sm-2">Address</dt>
        <dd class="col-sm-10">%s</dd>
        <dt class="col-sm-2">Web Address</dt>
        <dd class="col-sm-10">%s</dd>
        <dt class="col-sm-2">Notes</dt>
        <dd class="col-sm-10">%s</dd>
      </dl>
    </div>

    <div>
      <a href="edit?id=%d">Edit</a> |
      <a href=".">Back to List</a>
    </div>""".formatted(firstName, lastName, email, phoneNumber, address, webAddress, notes, id);
        String builtPage = ViewUtils.buildPage("Details", pageContent);

        writer.write(builtPage);
    }

}
