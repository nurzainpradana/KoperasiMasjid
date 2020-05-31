package com.nurzainpradana.koperasimasjid.fragment;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;


@Dao
public interface FavoriteDao {

    //Add to Favorite
    @Insert
    public void addData(com.nurzainpradana.koperasimasjid.fragment.FavoriteList favoriteList);

    @Query("SELECT * FROM favoritelist")
    public List<com.nurzainpradana.koperasimasjid.fragment.FavoriteList> getFavoriteData();

    @Query("SELECT EXISTS (SELECT 1 FROM favoritelist WHERE id=:id)")
    public int isFavorite(int id);

    @Delete
    public void delete(com.nurzainpradana.koperasimasjid.fragment.FavoriteList favoriteList);


}
