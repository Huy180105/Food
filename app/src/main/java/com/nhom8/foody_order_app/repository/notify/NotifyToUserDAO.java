package com.nhom8.foody_order_app.repository.notify;

import android.content.Context;
import android.database.Cursor;

import com.nhom8.foody_order_app.model.Notify;
import com.nhom8.foody_order_app.model.NotifyToUser;
import com.nhom8.foody_order_app.repositoryInit.DatabaseHandler;

import java.util.ArrayList;

public class NotifyToUserDAO {

    DatabaseHandler dbHelper;

    public NotifyToUserDAO(Context context) {
        dbHelper = new DatabaseHandler(context);
    }

    public void addNotifyToUser(NotifyToUser n) {
        String query = "INSERT INTO tblNotifyToUser VALUES(" +
                n.getNotifyId() + "," +
                n.getUserId() + ")";
        dbHelper.queryData(query);
    }

    public ArrayList<Notify> getUserNotify(int userId) {
        ArrayList<Notify> list = new ArrayList<>();

        String query = "SELECT tblNotify.* FROM tblNotify, tblNotifyToUser " +
                "WHERE tblNotify.id = tblNotifyToUser.notify_id " +
                "AND tblNotifyToUser.user_id=" + userId;

        Cursor cursor = dbHelper.getData(query);

        while (cursor.moveToNext()) {
            list.add(new Notify(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3)
            ));
        }

        return list;
    }
}
