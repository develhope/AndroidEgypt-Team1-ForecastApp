package co.develhope.meteoapp.ui.search.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import co.develhope.meteoapp.databinding.RecentSearchTitleBinding
import co.develhope.meteoapp.databinding.SearchRecyclerviewItemBinding
import co.develhope.meteoapp.ui.search.adapter.DataSearches.ItemSearch
import co.develhope.meteoapp.ui.search.adapter.DataSearches.SearchTitle
import co.develhope.meteoapp.ui.search.adapter.viewholder.SearchTitleViewHolder
import co.develhope.meteoapp.ui.search.adapter.viewholder.SearchViewHolder


class DataSearchAdapter(
    private var searchList: List<DataSearches>,
    private val onClick: (ItemSearch) -> Unit
) : Adapter<ViewHolder>() {
    override fun getItemViewType(position: Int): Int {
        return searchList[position].type
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            DataSearches.itemSearchId -> SearchViewHolder(
                SearchRecyclerviewItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )

            DataSearches.searchTitleId -> SearchTitleViewHolder(
                RecentSearchTitleBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )

            else -> throw Exception("Error: Invalid view holder")
        }
    }

    override fun getItemCount(): Int {
        return searchList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val search = searchList[position]

        when (holder) {
            is SearchViewHolder -> holder.onBind(
                model = search as ItemSearch,
                onClick = onClick
            )

            is SearchTitleViewHolder -> holder.onBind(search as SearchTitle)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setNewList(newList: List<DataSearches>) {
        searchList = newList
        notifyDataSetChanged()
    }
}