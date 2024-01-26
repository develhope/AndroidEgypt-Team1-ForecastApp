package co.develhope.meteoapp.ui.tomorrow.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.develhope.meteoapp.databinding.ListTodayScreenBinding
import co.develhope.meteoapp.databinding.TitleTodayScreenBinding
import co.develhope.meteoapp.ui.today.adapter.HourlyForecastItems

class TomorrowAdapter(private var items: List<HourlyForecastItems>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val openedItems = mutableListOf<Int>()

    override fun getItemViewType(position: Int): Int {
        return items[position].type
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            HourlyForecastItems.hourlyForecast -> TomorrowViewHolder(
                ListTodayScreenBinding.inflate
                    (LayoutInflater.from(parent.context), parent, false)
            )

            HourlyForecastItems.title -> TomorrowTitleViewHolder(
                TitleTodayScreenBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )

            else -> throw Exception("Error: invalid ViewHolder")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        when (holder) {
            is TomorrowViewHolder -> holder.onBind(
                item as HourlyForecastItems.Forecast,
                position,
                openedItems
            ) {
                notifyItemChanged(position)
            }

            is TomorrowTitleViewHolder -> holder.onBind(item as HourlyForecastItems.Title)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setNewList(newList: List<HourlyForecastItems>){
        items = newList
        notifyDataSetChanged()
    }


}