package com.katyrin.githubusers.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.katyrin.githubusers.App
import com.katyrin.githubusers.api.ApiHolder
import com.katyrin.githubusers.data.GithubUser
import com.katyrin.githubusers.databinding.FragmentUserBinding
import com.katyrin.githubusers.presenter.BackButtonListener
import com.katyrin.githubusers.presenter.user.UserView
import com.katyrin.githubusers.presenter.user.UserPresenter
import com.katyrin.githubusers.repository.RetrofitGithubRepositoriesRepo
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter


class UserFragment : MvpAppCompatFragment(), UserView, BackButtonListener {

    private var vb: FragmentUserBinding? = null
    val presenter: UserPresenter by moxyPresenter {
        val user = arguments?.getParcelable<GithubUser>(USER) as GithubUser
        UserPresenter(
            App.instance.router,
            user,
            RetrofitGithubRepositoriesRepo(ApiHolder.api),
            AndroidSchedulers.mainThread()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentUserBinding.inflate(inflater, container, false).also {
        vb = it
    }.root

    override fun init() {
        TODO("Not yet implemented")
    }

    override fun setLogin(text: String) {
        vb?.loginTextView?.text = text
    }

    override fun updateList() {
        TODO("Not yet implemented")
    }

    override fun backPressed() = presenter.backPressed()

    companion object {
        private const val USER = "USER"
        fun newInstance(user: GithubUser) =
            UserFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(USER, user)
                }
            }
    }
}