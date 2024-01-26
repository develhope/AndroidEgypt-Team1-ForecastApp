package co.develhope.meteoapp.ui.home.adapter

import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import co.develhope.meteoapp.R
import co.develhope.meteoapp.data.Data
import co.develhope.meteoapp.databinding.HomeTitleBinding
import co.develhope.meteoapp.ui.home.adapter.WeekItems.HomeTitle

class TitleViewHolder(private val binding: HomeTitleBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: HomeTitle, onClick: (WeekItems) -> Unit) {
        binding.homeTitle.text = Data.getCityLocation(binding.root.context)
        binding.root.setOnClickListener {
            binding.root.findNavController().navigate(R.id.search_screen)
            onClick(item)
        }
    }
}