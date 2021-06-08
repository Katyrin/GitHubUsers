package com.katyrin.githubusers.presenter.users

import com.katyrin.githubusers.presenter.IItemView

interface UserItemView : IItemView {
    fun setLogin(text: String)
    fun loadAvatar(url: String)
}