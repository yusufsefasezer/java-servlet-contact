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
import java.time.LocalDateTime;
import java.util.Optional;

@WebServlet("/edit")
public class EditServlet extends HttpServlet {

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
        } catch (NumberFormatException e) {
            WebUtils.flashMessage(request, e.getMessage());
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
                                     <form method="post">
                                         <div class="row">
                                             <div class="offset-md-2 col-md-4">
                                                <input type="hidden" id="id" name="id" value="%d" />
                                                 <div class="mb-3">
                                                     <label class="form-label" for="FirstName">First name</label>
                                                     <input class="form-control" type="text" id="firstName" name="firstName" value="%s" />
                                                 </div>

                                                 <div class="mb-3">
                                                     <label class="form-label" for="lastName">Last name</label>
                                                     <input class="form-control" type="text" id="lastName" name="lastName" value="%s" />
                                                 </div>

                                                 <div class="mb-3">
                                                     <label class="form-label" for="email">email</label>
                                                     <input class="form-control" type="email" id="email" name="email" value="%s" />
                                                 </div>

                                                 <div class="mb-3">
                                                     <label class="form-label" for="phoneNumber">Phone number</label>
                                                     <input class="form-control" type="text" id="phoneNumber" name="phoneNumber" value="%s" />
                                                 </div>
                                             </div>

                                             <div class="col-md-4">
                                                 <div class="mb-3">
                                                     <label class="form-label" for="address">Address</label>
                                                     <textarea class="form-control" id="address" name="address" cols="30" rows="3">%s</textarea>
                                                 </div>

                                                 <div class="mb-3">
                                                     <label class="form-label" for="webAddress">Web address</label>
                                                     <input class="form-control" type="url" id="webAddress" name="webAddress" value="%s" />
                                                 </div>

                                                 <div class="mb-3">
                                                     <label class="form-label" for="Notes">Notes</label>
                                                     <textarea class="form-control" id="notes" name="notes" cols="40" rows="3">%s</textarea>
                                                 </div>

                                                 <div class="d-grid gap-2">
                                                     <input type="submit" value="Update" class="btn btn-primary" />
                                                 </div>
                                             </div>
                                         </div>
                                     </form>
                             <div><a href=\".\">Back to List</a></div>
                        """.formatted(id, firstName, lastName, email, phoneNumber, address, webAddress, notes);
        String builtPage = ViewUtils.buildPage("Edit", pageContent);

        writer.write(builtPage);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] requiredParameters = {
            Contact.ID, Contact.FIRST_NAME, Contact.LAST_NAME, Contact.EMAIL,
            Contact.PHONE_NUMBER, Contact.ADDRESS, Contact.WEB_ADDRESS,
            Contact.NOTES
        };
        boolean isRequiredParametersFilled = WebUtils.checkParameters(requiredParameters, request.getParameterMap());
        String idParam = String.valueOf(request.getParameter("id"));
        boolean isNullOrNotANumber = "null".equals(idParam) || !idParam.matches("\\d+");
        Optional<Contact> foundContact;

        if (isRequiredParametersFilled) {
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

                Contact contact = foundContact.get();
                Object id = contact.getId();
                String firstName = request.getParameter(Contact.FIRST_NAME);
                String lastName = request.getParameter(Contact.LAST_NAME);
                String email = request.getParameter(Contact.EMAIL);
                String phoneNumber = request.getParameter(Contact.PHONE_NUMBER);
                String address = request.getParameter(Contact.ADDRESS);
                String webAddress = request.getParameter(Contact.WEB_ADDRESS);
                String notes = request.getParameter(Contact.NOTES);

                contact.setFirstName(firstName);
                contact.setLastName(lastName);
                contact.setEmail(email);
                contact.setPhoneNumber(phoneNumber);
                contact.setAddress(address);
                contact.setWebAddress(webAddress);
                contact.setNotes(notes);
                contact.setModifiedDate(LocalDateTime.now());

                repository.update(id, contact);
                WebUtils.flashMessage(request, "Contact updated successfully.");
            } catch (RuntimeException exception) {
                WebUtils.flashMessage(request, exception.getMessage());
            }
        } else {
            WebUtils.flashMessage(request, "All fields must be filled.");
        }

        response.sendRedirect(".");
    }

}
