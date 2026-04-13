package com.nhom8.foody_order_app.repository.notify;

import android.content.Context;
import android.database.Cursor;

import com.nhom8.foody_order_app.model.Notify;
import com.nhom8.foody_order_app.repositoryInit.DatabaseHandler;

import java.util.ArrayList;

public class NotifyDAO {

    DatabaseHandler dbHelper;

    public NotifyDAO(Context context) {
        dbHelper = new DatabaseHandler(context);
    }

    public void addNotify(Notify n) {
        String query = "INSERT INTO tblNotify VALUES(null,'" +
                n.getTitle() + "', '" +
                n.getContent() + "', '" +
                n.getDateMake() + "')";
        dbHelper.queryData(query);
    }

    public int getNewestNotifyId() {
        String query = "SELECT * FROM tblNotify";
        Cursor cursor = dbHelper.getData(query);

        if (cursor != null && cursor.moveToLast()) {
            return cursor.getInt(0);
        }
        return -1;
    }

    public ArrayList<Notify> getSystemNotify() {
        ArrayList<Notify> list = new ArrayList<>();

        String query = "SELECT * FROM tblNotify WHERE id NOT IN (SELECT notify_id FROM tblNotifyToUser)";
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
