package com.example.apiintrigation.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.apiintrigation.model.Post


@Dao
interface PostDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(posts : List<Post>)

    @Query("select * from posts")
    suspend fun getAllPosts() : List<Post>
}