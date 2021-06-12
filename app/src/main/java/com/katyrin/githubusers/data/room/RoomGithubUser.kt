package com.katyrin.githubusers.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class RoomGithubUser(
    @PrimaryKey var id: String,
    var login: String,
    var avatarUrl: String,
    var reposUrl: String
)
