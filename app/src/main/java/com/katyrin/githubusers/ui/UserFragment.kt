package com.katyrin.githubusers.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.katyrin.githubusers.App
import com.katyrin.githubusers.data.GithubUser
import com.katyrin.githubusers.databinding.FragmentUserBinding
import com.katyrin.githubusers.presenter.BackButtonListener
import com.katyrin.githubusers.presenter.user.UserPresenter
import com.katyrin.githubusers.presenter.user.UserView
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter


class UserFragment : MvpAppCompatFragment(), UserView, BackButtonListener {

    private var adapter: RepositoriesRVAdapter? = null
    private var vb: FragmentUserBinding? = null

    val presenter: UserPresenter by moxyPresenter {
        UserPresenter(arguments?.getParcelable<GithubUser>(USER) as GithubUser).apply {
            App.instance.appComponent.inject(this)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentUserBinding.inflate(inflater, container, false).also {
        vb = it
    }.root

    override fun init() {
        adapter = RepositoriesRVAdapter(presenter.repositoriesListPresenter)
        vb?.rvRepositories?.adapter = adapter
    }

    override fun setLogin(text: String) {
        vb?.loginTextView?.text = text
    }

    override fun updateList() {
        adapter?.notifyDataSetChanged()
    }

    override fun backPressed() = presenter.backPressed()

    override fun onDestroyView() {
        vb = null
        adapter = null
        super.onDestroyView()
    }

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