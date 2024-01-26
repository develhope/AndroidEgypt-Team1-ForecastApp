package co.develhope.meteoapp.ui.search.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import co.develhope.meteoapp.databinding.SearchRecyclerviewItemBinding
import co.develhope.meteoapp.ui.search.adapter.DataSearches


class SearchViewHolder(
    private val binding: SearchRecyclerviewItemBinding,

    ) :
    RecyclerView.ViewHolder(binding.root) {

    fun onBind(model: DataSearches.ItemSearch, onClick: (DataSearches.ItemSearch) -> Unit) {
        binding.searchRecentCityItem.text = "${model.recentCitySearch}, ${model.admin1}"
        binding.root.setOnClickListener {
            onClick(model)
        }
    }
}