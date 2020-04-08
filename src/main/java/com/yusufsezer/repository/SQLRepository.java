package com.yusufsezer.repository;

import com.yusufsezer.contracts.IRepository;
import com.yusufsezer.model.Contact;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class SQLRepository implements IRepository<Contact, Integer> {

    protected Connection connection;
    protected Statement statement;
    protected ResultSet resultSet;

    @Override
    public Contact get(Integer index) {
        Contact contact = null;
        String query = String.format("SELECT * FROM contacts WHERE id = %d",
                index);
        try {
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                contact = new Contact();
                contact.setId(resultSet.getInt("id"));
                contact.setFirstName(resultSet.getString("firstName"));
                contact.setLastName(resultSet.getString("lastName"));
                contact.setEmail(resultSet.getString("email"));
                contact.setPhoneNumber(resultSet.getString("phoneNumber"));
                contact.setAddress(resultSet.getString("address"));
                contact.setWebAddress(resultSet.getString("webAddress"));
                contact.setNotes(resultSet.getString("webAddress"));
            }
        } catch (SQLException ex) {
            Logger
                    .getLogger(SQLRepository.class.getName())
                    .log(Level.SEVERE, null, ex);
            return contact;
        }
        return contact;
    }

    @Override
    public List<Contact> getAll() {
        List<Contact> contacts = new ArrayList<>();
        String query = "SELECT * FROM contacts";
        try {
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Contact contact = new Contact();
                contact.setId(resultSet.getInt("id"));
                contact.setFirstName(resultSet.getString("firstName"));
                contact.setLastName(resultSet.getString("lastName"));
                contact.setEmail(resultSet.getString("email"));
                contact.setPhoneNumber(resultSet.getString("phoneNumber"));
                contact.setAddress(resultSet.getString("address"));
                contact.setWebAddress(resultSet.getString("webAddress"));
                contact.setNotes(resultSet.getString("webAddress"));
                contacts.add(contact);
            }
        } catch (SQLException ex) {
            Logger
                    .getLogger(SQLRepository.class.getName())
                    .log(Level.SEVERE, null, ex);
            return contacts;
        }
        return contacts;
    }

    @Override
    public boolean add(Contact contact) {
        boolean result = false;
        String query = String.format("INSERT INTO contacts"
                + " VALUES(NULL, '%s', '%s', '%s', '%s', '%s', '%s', '%s')",
                contact.getFirstName(),
                contact.getLastName(),
                contact.getEmail(),
                contact.getPhoneNumber(),
                contact.getAddress(),
                contact.getWebAddress(),
                contact.getNotes());
        try {
            result = statement.execute(query);
        } catch (SQLException ex) {
            Logger
                    .getLogger(SQLRepository.class.getName())
                    .log(Level.SEVERE, null, ex);
            return result;
        }
        return result;
    }

    @Override
    public Contact update(Integer index, Contact contact) {
        Contact updatedItem = get(index);
        String query = String.format("UPDATE contacts SET "
                + "firstName = '%s', "
                + "lastName = '%s', "
                + "email = '%s', "
                + "phoneNumber = '%s', "
                + "address = '%s', "
                + "webAddress = '%s', "
                + "notes = '%s' "
                + "WHERE id = %d",
                contact.getFirstName(),
                contact.getLastName(),
                contact.getEmail(),
                contact.getPhoneNumber(),
                contact.getAddress(),
                contact.getWebAddress(),
                contact.getNotes(),
                index);

        try {
            updatedItem = statement.execute(query) ? updatedItem : contact;
        } catch (SQLException ex) {
            Logger
                    .getLogger(SQLRepository.class.getName())
                    .log(Level.SEVERE, null, ex);
            return updatedItem;
        }
        return updatedItem;
    }

    @Override
    public Contact remove(Integer index) {
        String query = String
                .format("DELETE FROM contacts WHERE id = %d", index);
        Contact deletedItem = get(index);
        try {
            statement.execute(query);
        } catch (SQLException ex) {
            Logger
                    .getLogger(SQLRepository.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        return deletedItem;
    }

}
