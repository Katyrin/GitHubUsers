package com.katyrin.githubusers.presenter.users

import com.github.terrakok.cicerone.Router
import com.katyrin.githubusers.data.GithubUser
import com.katyrin.githubusers.repository.IGithubUsersRepo
import com.katyrin.githubusers.ui.AndroidScreens
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.CompositeDisposable
import moxy.MvpPresenter
import javax.inject.Inject

class UsersPresenter : MvpPresenter<UsersView>() {

    @Inject
    lateinit var usersRepo: IGithubUsersRepo
    @Inject
    lateinit var router: Router
    @Inject
    lateinit var uiScheduler: Scheduler

    class UsersListPresenter : IUserListPresenter {
        val users = mutableListOf<GithubUser>()
        override var itemClickListener: ((UserItemView) -> Unit)? = null

        override fun getCount() = users.size

        override fun bindView(view: UserItemView) {
            val user = users[view.pos]
            user.login?.let { view.setLogin(it) }
            user.avatarUrl?.let { view.loadAvatar(it) }
        }
    }

    val usersListPresenter = UsersListPresenter()
    private var disposable: CompositeDisposable? = CompositeDisposable()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
        loadData()

        usersListPresenter.itemClickListener = { itemView ->
            val user = usersListPresenter.users[itemView.pos]
            router.navigateTo(AndroidScreens.UserScreen(user).getFragment())
        }
    }

    fun loadData() {
        disposable?.add(
            usersRepo.getUsers()
                .observeOn(uiScheduler)
                .subscribe(::updateUsers) { it.printStackTrace() }
        )
    }

    private fun updateUsers(users: List<GithubUser>) {
        usersListPresenter.users.clear()
        usersListPresenter.users.addAll(users)
        viewState.updateList()
    }

    fun backPressed(): Boolean {
        router.exit()
        return true
    }

    override fun onDestroy() {
        disposable?.clear()
        disposable = null
        super.onDestroy()
    }
}