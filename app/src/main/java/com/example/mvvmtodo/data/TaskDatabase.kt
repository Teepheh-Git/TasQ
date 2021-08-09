package com.example.mvvmtodo.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.mvvmtodo.di.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

@Database(entities = [Task::class], version = 1)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao

    class Callback @Inject constructor(
        private val database: Provider<TaskDatabase>,
        @ApplicationScope private val applicationScope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            val dao = database.get().taskDao()

            applicationScope.launch {

                dao.insert(Task("My TasQ", completed = false, important = false))
                dao.insert(Task("New TasQ", completed = false, important = true))
                dao.insert(Task("Add TasQ", completed = false, important = false))
                dao.insert(Task("Important TasQ", completed = true, important = true))
                dao.insert(Task("Swipe Left", completed = true, important = true))
                dao.insert(Task("Swipe Right", completed = true, important = true))

            }

        }

    }

}