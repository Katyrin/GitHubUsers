package com.katyrin.githubusers.presenter

import com.github.terrakok.cicerone.Screen

interface IScreen {
    fun getFragment(): Screen
}