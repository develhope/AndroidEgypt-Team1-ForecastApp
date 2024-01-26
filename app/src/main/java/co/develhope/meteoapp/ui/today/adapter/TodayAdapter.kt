package co.develhope.meteoapp.ui.today.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import co.develhope.meteoapp.databinding.ListTodayScreenBinding
import co.develhope.meteoapp.databinding.TitleTodayScreenBinding
import co.develhope.meteoapp.ui.today.adapter.HourlyForecastItems.Forecast
import co.develhope.meteoapp.ui.today.adapter.HourlyForecastItems.Title

class TodayAdapter(private var items: List<HourlyForecastItems>) : Adapter<ViewHolder>() {
    private val openedItems = mutableListOf<Int>()

    override fun getItemViewType(position: Int): Int {
        return items[position].type
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            HourlyForecastItems.hourlyForecast -> TodayViewHolder(
                ListTodayScreenBinding.inflate
                    (LayoutInflater.from(parent.context), parent, false)
            )

            HourlyForecastItems.title -> TodayTitleViewHolder(
                TitleTodayScreenBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )

            else -> throw Exception("Error: invalid ViewHolder")
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        when (holder) {
            is TodayViewHolder -> holder.onBind(
                item as Forecast,
                position,
                openedItems
            ) {
                notifyItemChanged(position)
            }

            is TodayTitleViewHolder -> holder.onBind(item as Title)
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