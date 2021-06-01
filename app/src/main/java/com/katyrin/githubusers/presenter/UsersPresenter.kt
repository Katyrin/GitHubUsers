package com.katyrin.githubusers.presenter

import com.github.terrakok.cicerone.Router
import com.katyrin.githubusers.data.GithubUser
import com.katyrin.githubusers.data.GithubUsersRepo
import com.katyrin.githubusers.ui.AndroidScreens
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.CompositeDisposable
import moxy.MvpPresenter

class UsersPresenter(
    private val usersRepo: GithubUsersRepo,
    private val router: Router,
    private val uiScheduler: Scheduler
) : MvpPresenter<UsersView>() {

    class UsersListPresenter : IUserListPresenter {
        val users = mutableListOf<GithubUser>()
        override var itemClickListener: ((UserItemView) -> Unit)? = null

        override fun bindView(view: UserItemView) {
            val user = users[view.pos]
            view.setLogin(user.login)
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
            router.navigateTo(AndroidScreens.user(user))
        }
    }

    fun loadData() {
        disposable.add(
            usersRepo.getUsers()
                .observeOn(uiScheduler)
                .subscribe({ users ->
                    usersListPresenter.users.clear()
                    usersListPresenter.users.addAll(users)
                    viewState.updateList()
                }, {
                    it.printStackTrace()
                })
        )
    }

    fun backPressed(): Boolean {
        router.exit()
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }
}