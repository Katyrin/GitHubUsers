package com.katyrin.githubusers.presenter

interface UserItemView : IItemView {
    fun setLogin(text: String)
    fun loadAvatar(url: String)
}