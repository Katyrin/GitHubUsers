package com.katyrin.githubusers.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.katyrin.githubusers.App
import com.katyrin.githubusers.api.ApiHolder
import com.katyrin.githubusers.api.GlideImageLoader
import com.katyrin.githubusers.databinding.FragmentUsersBinding
import com.katyrin.githubusers.presenter.BackButtonListener
import com.katyrin.githubusers.presenter.UsersPresenter
import com.katyrin.githubusers.presenter.UsersView
import com.katyrin.githubusers.repository.RetrofitGithubUsersRepo
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter


class UsersFragment : MvpAppCompatFragment(), UsersView, BackButtonListener {
    companion object {
        fun newInstance() = UsersFragment()
    }

    val presenter: UsersPresenter by moxyPresenter {
        UsersPresenter(
            RetrofitGithubUsersRepo(ApiHolder.api),
            App.instance.router,
            AndroidSchedulers.mainThread()
        )
    }
    var adapter: UsersRVAdapter? = null
    private var vb: FragmentUsersBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentUsersBinding.inflate(inflater, container, false).also {
        vb = it
    }.root

    override fun onDestroyView() {
        vb = null
        super.onDestroyView()
    }

    override fun init() {
        adapter = UsersRVAdapter(presenter.usersListPresenter, GlideImageLoader())
        vb?.rvUsers?.adapter = adapter
    }

    override fun updateList() {
        adapter?.notifyDataSetChanged()
    }

    override fun backPressed() = presenter.backPressed()
}