package com.katyrin.githubusers.presenter

import com.github.terrakok.cicerone.Router
import com.katyrin.githubusers.data.GithubUser
import com.katyrin.githubusers.repository.IGithubUsersRepo
import com.katyrin.githubusers.ui.AndroidScreens
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.CompositeDisposable
import moxy.MvpPresenter

class UsersPresenter(
    private val usersRepo: IGithubUsersRepo,
    private val router: Router,
    private val uiScheduler: Scheduler
) : MvpPresenter<UsersView>() {

    class UsersListPresenter : IUserListPresenter {
        val users = mutableListOf<GithubUser>()
        override var itemClickListener: ((UserItemView) -> Unit)? = null

        override fun bindView(view: UserItemView) {
            val user = users[view.pos]
            user.login?.let { view.setLogin(it) }
            user.avatarUrl?.let { view.loadAvatar(it) }
        }

        override fun getCount() = users.size
    }

    val usersListPresenter = UsersListPresenter()
    private var disposable: CompositeDisposable = CompositeDisposable()

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
        disposable.add(
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
        disposable.dispose()
        super.onDestroy()
    }
}