package com.katyrin.githubusers.di

import com.katyrin.githubusers.di.modules.AppModule
import com.katyrin.githubusers.di.modules.network.NetworkStateModule
import com.katyrin.githubusers.di.modules.user.UserModule
import com.katyrin.githubusers.presenter.main.MainPresenter
import com.katyrin.githubusers.presenter.repository.RepositoryPresenter
import com.katyrin.githubusers.presenter.user.UserPresenter
import com.katyrin.githubusers.presenter.users.UsersPresenter
import com.katyrin.githubusers.ui.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        NetworkStateModule::class,
        UserModule::class
    ]
)
interface AppComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(mainPresenter: MainPresenter)
    fun inject(usersPresenter: UsersPresenter)
    fun inject(userPresenter: UserPresenter)
    fun inject(repositoryPresenter: RepositoryPresenter)
}