package com.nhom8.foody_order_app.repository.user;

import android.content.Context;
import android.database.Cursor;
import com.nhom8.foody_order_app.repositoryInit.DatabaseHandler;
import com.nhom8.foody_order_app.model.*;
public class UserDAO {
    DatabaseHandler dbHelper;

    public UserDAO(Context context) {
        dbHelper = new DatabaseHandler(context);
    }

    public void addUser(User user) {
        String query = "INSERT INTO tblUser VALUES(null,'" +
                user.getName() + "', '" +
                user.getGender() + "', '" +
                user.getDateOfBirth() + "', '" +
                user.getPhone() + "', '" +
                user.getUsername() + "', '" +
                user.getPassword() + "')";
        dbHelper.queryData(query);
    }

    public void updateUser(User user) {
        String query = "UPDATE tblUser SET " +
                "name='" + user.getName() + "'," +
                "gender='" + user.getGender() + "'," +
                "date_of_birth='" + user.getDateOfBirth() + "'," +
                "phone='" + user.getPhone() + "'," +
                "password='" + user.getPassword() + "' " +
                "WHERE id=" + user.getId();
        dbHelper.queryData(query);
    }

    public int getNewestUserId() {
        Cursor cursor = dbHelper.getData("SELECT * FROM tblUser");
        if (cursor != null && cursor.moveToLast()) {
            return cursor.getInt(0);
        }
        return -1;
    }

    public boolean userExists(String username) {
        String query = "SELECT * FROM tblUser WHERE username='" + username + "'";
        Cursor cursor = dbHelper.getData(query);
        return cursor.getCount() > 0;
    }

    public boolean checkUsername(String username) {
        return userExists(username);
    }

    public boolean checkPasswordToCurrentUsername(String username, String password) {
        String query = "SELECT * FROM tblUser WHERE username='" + username + "' and password='" + password + "'";
        Cursor cursor = dbHelper.getDataRow(query);
        return cursor.getCount() > 0;
    }

    public User getUserByUsernameAndPassword(String username, String password) {
        String query = "SELECT * FROM tblUser WHERE username='" + username + "' and password='" + password + "'";
        Cursor cursor = dbHelper.getDataRow(query);

        if (cursor.getCount() > 0) {
            return new User(cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                    cursor.getString(3), cursor.getString(4), cursor.getString(5),
                    cursor.getString(6));
        }
        return null;
    }

    public boolean signIn(User user) {
        return getUserByUsernameAndPassword(user.getUsername(), user.getPassword()) != null;
    }
}

