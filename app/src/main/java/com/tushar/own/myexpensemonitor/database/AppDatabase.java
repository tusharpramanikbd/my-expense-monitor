package com.tushar.own.myexpensemonitor.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.tushar.own.myexpensemonitor.daos.ExpenseDao;
import com.tushar.own.myexpensemonitor.models.ExpenseModel;

@Database(entities = ExpenseModel.class, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase INSTANCE;

    public abstract ExpenseDao expenseDao();

    public static synchronized void AppDatabaseInitializer(Context context) {
        if(INSTANCE == null) {
            INSTANCE = create(context);
        }
    }

    public static synchronized AppDatabase getInstance() {
        return INSTANCE;
    }

    private static AppDatabase create(final Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, "expense_db")
                .fallbackToDestructiveMigration()
                .build();
    }

}
