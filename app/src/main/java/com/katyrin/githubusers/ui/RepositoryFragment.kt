package com.katyrin.githubusers.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.katyrin.githubusers.App
import com.katyrin.githubusers.R
import com.katyrin.githubusers.data.GitHubRepository
import com.katyrin.githubusers.presenter.repository.RepositoryPresenter
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter

class RepositoryFragment : MvpAppCompatFragment() {

    val presenter: RepositoryPresenter by moxyPresenter {
        val repository = arguments?.getParcelable<GitHubRepository>(REPOSITORY) as GitHubRepository
        RepositoryPresenter(App.instance.router, repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_repository, container, false)
    }

    companion object {
        private const val REPOSITORY = "REPOSITORY"
        fun newInstance(repository: GitHubRepository) = RepositoryFragment().apply {
            arguments = Bundle().apply {
                putParcelable(REPOSITORY, repository)
            }
        }
    }
}