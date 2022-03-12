package com.app.tasknitintyagi.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.app.tasknitintyagi.model.SignUpModel


@Database(
    entities = [SignUpModel::class],
    version = 1,
    exportSchema = false
)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao

    companion object {
        private var instance: TaskDatabase? = null

        @Synchronized
        fun getInstance(ctx: Context): TaskDatabase {
            if (instance == null)
                instance = Room.databaseBuilder(
                    ctx.applicationContext, TaskDatabase::class.java,
                    "task_database"
                )
                    .allowMainThreadQueries()
                    .build()
            return instance!!

        }
    }

}