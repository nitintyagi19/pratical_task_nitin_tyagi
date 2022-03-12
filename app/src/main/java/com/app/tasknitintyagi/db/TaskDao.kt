package com.app.tasknitintyagi.db

import androidx.room.*
import com.app.tasknitintyagi.model.SignUpModel


@Dao
interface TaskDao {

    @Query("SELECT * FROM SignUpModel WHERE email=:email")
    fun getUserData(email: String): SignUpModel?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(model: SignUpModel)
}