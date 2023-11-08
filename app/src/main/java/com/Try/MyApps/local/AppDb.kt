package com.Try.MyApps.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.Try.MyApps.model.ResponseUserGithub

@Database(entities = [ResponseUserGithub.Item::class], version = 1, exportSchema = false)
abstract class AppDb : RoomDatabase() {
    abstract fun userDao(): UserDao
}