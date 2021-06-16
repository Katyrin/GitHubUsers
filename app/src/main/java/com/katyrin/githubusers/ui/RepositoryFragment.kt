package com.katyrin.githubusers.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.terrakok.cicerone.Router
import com.katyrin.githubusers.App
import com.katyrin.githubusers.R
import com.katyrin.githubusers.data.GitHubRepository
import com.katyrin.githubusers.databinding.FragmentRepositoryBinding
import com.katyrin.githubusers.presenter.BackButtonListener
import com.katyrin.githubusers.presenter.repository.RepositoryPresenter
import com.katyrin.githubusers.presenter.repository.RepositoryView
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import javax.inject.Inject

class RepositoryFragment : MvpAppCompatFragment(), RepositoryView, BackButtonListener {

    private var vb: FragmentRepositoryBinding? = null
    val presenter: RepositoryPresenter by moxyPresenter {
        val repository = arguments?.getParcelable<GitHubRepository>(REPOSITORY) as GitHubRepository
        RepositoryPresenter(repository).apply {
            App.instance.appComponent.inject(this)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        FragmentRepositoryBinding.inflate(inflater, container, false).also {
            vb = it
        }.root

    companion object {
        private const val REPOSITORY = "REPOSITORY"
        fun newInstance(repository: GitHubRepository) = RepositoryFragment().apply {
            arguments = Bundle().apply {
                putParcelable(REPOSITORY, repository)
            }
        }
    }

    override fun backPressed() = presenter.backPressed()

    override fun init() {

    }

    override fun setId(id: String) {
        val idText = "${getString(R.string.id)} $id"
        vb?.idTextView?.text = idText
    }

    override fun setTitle(title: String) {
        val nameText = "${getString(R.string.name)} $title"
        vb?.nameTextView?.text = nameText
    }

    override fun setForksCount(forksCount: String) {
        val forksText = "${getString(R.string.forks_count)} $forksCount"
        vb?.countForksTextView?.text = forksText
    }

    override fun onDestroyView() {
        vb = null
        super.onDestroyView()
    }
}