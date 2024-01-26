package co.develhope.meteoapp.ui.search.adapter


sealed class DataSearches(val type: Int) {

    data class ItemSearch(
        val recentCitySearch: String,
        val admin1: String?,
        val longitude: Double?,
        val latitude: Double?,
    ) : DataSearches(
        itemSearchId
    ) {
        override fun toString(): String {
            return "${this.recentCitySearch}, ${this.admin1}"
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is ItemSearch) return false

            if (recentCitySearch != other.recentCitySearch || admin1 != other.admin1) return false

            val threshold = 0.1 // Imposta la soglia desiderata

            val thisLat = latitude ?: 0.0
            val thisLon = longitude ?: 0.0
            val otherLat = other.latitude ?: 0.0
            val otherLon = other.longitude ?: 0.0

            val latDiff = Math.abs(thisLat - otherLat)
            val lonDiff = Math.abs(thisLon - otherLon)

            return latDiff < threshold && lonDiff < threshold
        }
    }

    data class SearchTitle(val title: String) : DataSearches(searchTitleId)

    companion object {
        const val itemSearchId = 1
        const val searchTitleId = 2
    }

}




