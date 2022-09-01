package com.arjunalabs.bikemap.feature.map

import android.content.ContentValues
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.arjunalabs.bikemap.feature.map.databinding.FragmentMapBinding
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapInitOptions
import com.mapbox.maps.Style
import com.zhuinden.simplestackextensions.fragments.DefaultFragmentKey
import com.zhuinden.simplestackextensions.fragments.KeyedFragment
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MapScreen(
    val latitude: Double,
    val longitude: Double,
    val pitch: Double,
    val zoom: Double,
    val bearing: Double
    ): DefaultFragmentKey() {

    companion object {

        fun parseContentValues(contentValues: ContentValues): MapScreen {
            val latitude = contentValues.getAsDouble("latitude")
            val longitude = contentValues.getAsDouble("longitude")
            val pitch = contentValues.getAsDouble("pitch")
            val zoom = contentValues.getAsDouble("zoom")
            val bearing = contentValues.getAsDouble("bearing")
            return MapScreen(
                latitude = latitude,
                longitude = longitude,
                pitch = pitch,
                zoom = zoom,
                bearing = bearing
            )
        }
    }
    override fun instantiateFragment(): Fragment = MapFragment()
}

class MapFragment : KeyedFragment() {

    private var _binding: FragmentMapBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        return binding.root

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.mapView.getMapboxMap().loadStyleUri(Style.MAPBOX_STREETS)

        val screen = getKey<MapScreen>()
        val initialCameraOptions = CameraOptions.Builder()
            .center(Point.fromLngLat(screen.longitude, screen.latitude))
            .pitch(screen.pitch)
            .zoom(screen.zoom)
            .bearing(screen.bearing)
            .build()

        binding.mapView.getMapboxMap().setCamera(initialCameraOptions)
    }

}