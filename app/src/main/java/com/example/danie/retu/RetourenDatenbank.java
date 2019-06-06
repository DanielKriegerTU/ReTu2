package com.example.danie.retu;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {RetourenEntity.class}, version = 1, exportSchema = false)
public abstract class RetourenDatenbank extends RoomDatabase {

    public abstract RetourenDAO getRetourenDAO();

    }


