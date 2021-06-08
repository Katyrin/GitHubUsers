package com.katyrin.githubusers.ui

import com.github.terrakok.cicerone.Screen

interface IScreen {
    fun getFragment(): Screen
}