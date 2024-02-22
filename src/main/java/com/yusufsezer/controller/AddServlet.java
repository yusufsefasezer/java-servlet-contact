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

@WebServlet("/add")
public class AddServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        var writer = response.getWriter();

        String pageContent = """
                                     <form method="post">
                                         <div class="row justify-content-md-center">
                                             <div class="col-md-4">
                                                 <div class="mb-3">
                                                     <label class="form-label" for="firstName">First Name</label>
                                                     <input class="form-control" type="text" id="firstName" name="firstName" value="" />
                                                 </div>

                                                 <div class="mb-3">
                                                     <label class="form-label" for="lastName">Last Name</label>
                                                     <input class="form-control" type="text" id="lastName" name="lastName" value="" />
                                                 </div>

                                                 <div class="mb-3">
                                                     <label class="form-label" for="email">email</label>
                                                     <input class="form-control" type="email" id="email" name="email" value="" />
                                                 </div>

                                                 <div class="mb-3">
                                                     <label class="form-label" for="phoneNumber">Phone Number</label>
                                                     <input class="form-control" type="text" id="phoneNumber" name="phoneNumber" value="" />
                                                 </div>
                                             </div>

                                             <div class="col-md-4">
                                                 <div class="mb-3">
                                                     <label class="form-label" for="address">Address</label>
                                                     <textarea class="form-control" id="address" name="address" cols="30" rows="3"></textarea>
                                                 </div>

                                                 <div class="mb-3">
                                                     <label class="form-label" for="webAddress">Web Address</label>
                                                     <input class="form-control" type="url" id="webAddress" name="webAddress" value="" />
                                                 </div>

                                                 <div class="mb-3">
                                                     <label class="form-label" for="notes">notes</label>
                                                     <textarea class="form-control" id="notes" name="notes" cols="40" rows="3"></textarea>
                                                 </div>

                                                 <div class="d-grid gap-2">
                                                     <input type="submit" value="Add" class="btn btn-primary" />
                                                 </div>
                                             </div>
                                         </div>
                                     </form>

                                     <div><a href=".">Back to List</a></div>
                             """;
        String builtPage = ViewUtils.buildPage("Add", pageContent);

        writer.write(builtPage);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] requiredParameters = {
            Contact.FIRST_NAME, Contact.LAST_NAME, Contact.EMAIL,
            Contact.PHONE_NUMBER, Contact.ADDRESS, Contact.WEB_ADDRESS,
            Contact.NOTES
        };
        boolean isRequiredParametersFilled = WebUtils.checkParameters(requiredParameters, request.getParameterMap());

        if (isRequiredParametersFilled) {
            var repository = WebUtils.getRepository(getServletContext());
            String firstName = request.getParameter(Contact.FIRST_NAME);
            String lastName = request.getParameter(Contact.LAST_NAME);
            String email = request.getParameter(Contact.EMAIL);
            String phoneNumber = request.getParameter(Contact.PHONE_NUMBER);
            String address = request.getParameter(Contact.ADDRESS);
            String webAddress = request.getParameter(Contact.WEB_ADDRESS);
            String notes = request.getParameter(Contact.NOTES);

            Contact newContact = Contact.of(firstName, lastName, email, phoneNumber, address, webAddress, notes);
            newContact.setCreatedDate(LocalDateTime.now());
            newContact.setModifiedDate(LocalDateTime.now());

            repository.add(newContact);
            WebUtils.flashMessage(request, "Contact added successfully.");
        } else {
            WebUtils.flashMessage(request, "All fields must be filled.");
        }

        response.sendRedirect(".");
    }

}
