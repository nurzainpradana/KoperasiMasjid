package com.nurzainpradana.koperasimasjid.fragment;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities = {FavoriteList.class}, version = 3, exportSchema = false)
public abstract class FavoriteDatabase extends RoomDatabase {

    private static FavoriteDatabase instance;

    public abstract FavoriteDao favoriteDao();

    public static FavoriteDatabase getFavorite(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, FavoriteDatabase.class, "Favorite")
                    .allowMainThreadQueries()
                    .build();
        }

        return instance;
    }
}
