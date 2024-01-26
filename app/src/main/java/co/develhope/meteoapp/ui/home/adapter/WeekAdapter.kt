package co.develhope.meteoapp.ui.home.adapter


import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.recyclerview.widget.RecyclerView
import co.develhope.meteoapp.ui.home.adapter.WeekItems.Companion.DaysId
import co.develhope.meteoapp.ui.home.adapter.WeekItems.Companion.HomeSubtitleId
import co.develhope.meteoapp.ui.home.adapter.WeekItems.Companion.HomeTitleId
import co.develhope.meteoapp.ui.home.adapter.WeekItems.Companion.TodayId
import co.develhope.meteoapp.ui.home.adapter.WeekItems.Days
import co.develhope.meteoapp.ui.home.adapter.WeekItems.HomeSubtitle
import co.develhope.meteoapp.ui.home.adapter.WeekItems.HomeTitle
import co.develhope.meteoapp.ui.home.adapter.WeekItems.Today
import co.develhope.meteoapp.databinding.HomeSubtitleBinding
import co.develhope.meteoapp.databinding.HomeTitleBinding
import co.develhope.meteoapp.databinding.ListHomeScreenBinding


class WeekAdapter(private var items: List<WeekItems>, private val onClick: (WeekItems) -> Unit) :
    Adapter<ViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        val item = items[position]
        return item.id
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        Log.d("WeekAdapter", "viewType is $viewType")
        return when (viewType) {
            TodayId -> TodayViewHolder(
                ListHomeScreenBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            DaysId -> DaysViewHolder(
                ListHomeScreenBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            HomeTitleId -> TitleViewHolder(
                HomeTitleBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            HomeSubtitleId -> SubtitleViewHolder(
                HomeSubtitleBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
            )

            else -> throw Exception("Invalid ViewHolder Type")
        }
    }

    override fun getItemCount(): Int = items.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        Log.d("WeekAdapter", "holder is $holder")

        when (holder) {
            is TodayViewHolder -> holder.bind(item as Today, onClick)
            is DaysViewHolder -> holder.bind(item as Days, onClick)
            is TitleViewHolder -> holder.bind(item as HomeTitle, onClick)
            is SubtitleViewHolder -> holder.bind(item as HomeSubtitle)
            else -> throw Exception("Invalid ViewHolder Not Recognized")
        }
    }

    fun setNewList(newList: List<WeekItems>){
        items = newList
        notifyDataSetChanged()
    }
}


