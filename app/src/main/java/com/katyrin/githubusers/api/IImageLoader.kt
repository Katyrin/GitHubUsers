package com.katyrin.githubusers.api

interface IImageLoader<T> {
    fun loadInto(url: String, container: T)
}
