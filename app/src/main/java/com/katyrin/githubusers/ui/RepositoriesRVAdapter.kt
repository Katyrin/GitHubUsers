package com.katyrin.githubusers.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.katyrin.githubusers.databinding.ItemRepositoryBinding
import com.katyrin.githubusers.presenter.user.IRepositoryListPresenter
import com.katyrin.githubusers.presenter.user.RepositoryItemView

class RepositoriesRVAdapter(
    val presenter: IRepositoryListPresenter
) : RecyclerView.Adapter<RepositoriesRVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            ItemRepositoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        ).apply {
            itemView.setOnClickListener {
                presenter.itemClickListener?.invoke(this)
            }
        }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        presenter.bindView(holder.apply { pos = position })

    override fun getItemCount() = presenter.getCount()


    inner class ViewHolder(private val vb: ItemRepositoryBinding) :
        RecyclerView.ViewHolder(vb.root), RepositoryItemView {
        override var pos: Int = -1

        override fun setName(name: String) = with(vb) {
            tvName.text = name
        }
    }
}