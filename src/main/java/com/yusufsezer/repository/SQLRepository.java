package com.yusufsezer.repository;

import com.yusufsezer.contract.IRepository;
import com.yusufsezer.model.Contact;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class SQLRepository implements IRepository<Contact, Integer> {

    protected final Connection connection;

    public SQLRepository(Connection connection) {
        this.connection = connection;
        createTableIfNotExists();
    }

    @Override
    public Optional<Contact> get(Integer index) {
        Optional<Contact> foundContact = Contact.EMPTY;

        try (PreparedStatement prepareStatement = connection.prepareStatement("SELECT * FROM contacts WHERE id = ?")) {
            prepareStatement.setInt(1, index);
            ResultSet resultSet = prepareStatement.executeQuery();
            if (resultSet.next()) {
                foundContact = Optional.of(mapResultToContact(resultSet));
            }
        } catch (SQLException exception) {
            logException(exception);
        }

        return foundContact;
    }

    @Override
    public List<Contact> getAll() {
        List<Contact> contacts = new ArrayList<>();

        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM contacts");
            while (resultSet.next()) {
                contacts.add(mapResultToContact(resultSet));
            }
        } catch (Exception exception) {
            logException(exception);
        }

        return contacts;
    }

    @Override
    public boolean add(Contact contact) {
        boolean result = false;

        String contactInsertQuery = "INSERT INTO contacts VALUES(NULL, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(contactInsertQuery)) {
            preparedStatement.setString(1, contact.getFirstName());
            preparedStatement.setString(2, contact.getLastName());
            preparedStatement.setString(3, contact.getEmail());
            preparedStatement.setString(4, contact.getPhoneNumber());
            preparedStatement.setString(5, contact.getAddress());
            preparedStatement.setString(6, contact.getWebAddress());
            preparedStatement.setString(7, contact.getNotes());
            result = preparedStatement.executeUpdate() > 0;
        } catch (SQLException exception) {
            logException(exception);
        }

        return result;
    }

    @Override
    public Optional<Contact> update(Integer index, Contact contact) {
        Optional<Contact> foundContact = Contact.EMPTY;

        String contactUpdateQuery = "UPDATE contacts SET "
                + Contact.FIRST_NAME + " = ?, "
                + Contact.LAST_NAME + " = ?, "
                + Contact.EMAIL + " = ?, "
                + Contact.PHONE_NUMBER + " = ?, "
                + Contact.ADDRESS + " = ?, "
                + Contact.WEB_ADDRESS + " = ?, "
                + Contact.NOTES + " = ? "
                + "WHERE " + Contact.ID + " = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(contactUpdateQuery)) {
            preparedStatement.setString(1, contact.getFirstName());
            preparedStatement.setString(2, contact.getLastName());
            preparedStatement.setString(3, contact.getEmail());
            preparedStatement.setString(4, contact.getPhoneNumber());
            preparedStatement.setString(5, contact.getAddress());
            preparedStatement.setString(6, contact.getWebAddress());
            preparedStatement.setString(7, contact.getNotes());
            preparedStatement.setInt(8, index);
            boolean updated = preparedStatement.executeUpdate() > 0;
            if (updated) {
                foundContact = get(index);
            }
        } catch (SQLException exception) {
            logException(exception);
        }

        return foundContact;
    }

    @Override
    public Optional<Contact> remove(Integer index) {
        Optional<Contact> contactToRemove = get(index);

        if (contactToRemove.isPresent()) {
            String contactDeleteQuery = "DELETE FROM contacts WHERE " + Contact.ID + "= ?";
            try (PreparedStatement prepareStatement = connection.prepareStatement(contactDeleteQuery)) {
                prepareStatement.setLong(1, index);
                prepareStatement.executeUpdate();
            } catch (SQLException exception) {
                logException(exception);
            }
        }

        return contactToRemove;
    }

    private void createTableIfNotExists() {
        try (Statement statement = connection.createStatement()) {
            statement.execute("SELECT * FROM contacts");
        } catch (SQLException ex) {
            logException(ex);
            createTable();
        }
    }

    protected void createTable() {
        String sql = "CREATE TABLE contacts ("
                + Contact.ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Contact.FIRST_NAME + " VARCHAR,"
                + Contact.LAST_NAME + " VARCHAR,"
                + Contact.EMAIL + " VARCHAR,"
                + Contact.PHONE_NUMBER + " VARCHAR,"
                + Contact.ADDRESS + " TEXT,"
                + Contact.WEB_ADDRESS + " VARCHAR,"
                + Contact.NOTES + "  TEXT)";
        try (Statement statement = connection.createStatement()) {
            statement.execute(sql);
        } catch (SQLException exception) {
            logException(exception);
            throw new RuntimeException("Failed to create table.");
        }
    }

    private Contact mapResultToContact(ResultSet resultSet) throws SQLException {
        Contact foundContact = Contact.of(
                resultSet.getString(Contact.FIRST_NAME),
                resultSet.getString(Contact.LAST_NAME),
                resultSet.getString(Contact.EMAIL),
                resultSet.getString(Contact.PHONE_NUMBER),
                resultSet.getString(Contact.ADDRESS),
                resultSet.getString(Contact.WEB_ADDRESS),
                resultSet.getString(Contact.NOTES)
        );
        foundContact.setId(resultSet.getInt(Contact.ID));

        return foundContact;
    }

    protected void logException(Exception ex) {
        Logger.getLogger(SQLRepository.class.getName())
                .log(Level.SEVERE, null, ex);
    }

}
