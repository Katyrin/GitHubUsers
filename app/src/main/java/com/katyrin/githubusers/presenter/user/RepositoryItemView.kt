package com.katyrin.githubusers.presenter.user

import com.katyrin.githubusers.presenter.IItemView

interface RepositoryItemView : IItemView {
    fun setName(name: String)
}