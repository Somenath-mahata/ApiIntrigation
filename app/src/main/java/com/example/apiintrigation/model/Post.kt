package com.example.apiintrigation.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
@Entity(tableName = "posts")
data class Post (
    val userId: Int = 0,
    @PrimaryKey
    val id: Int = 0,
    val title: String? = null,

    @SerializedName("body")
    val text: String? = null
)
