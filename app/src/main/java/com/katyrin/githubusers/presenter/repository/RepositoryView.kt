package com.katyrin.githubusers.presenter.repository

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface RepositoryView : MvpView {
    fun init()
    fun setId(id: String)
    fun setTitle(title: String)
    fun setForksCount(forksCount: String)
}