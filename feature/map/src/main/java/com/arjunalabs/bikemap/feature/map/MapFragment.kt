package com.arjunalabs.bikemap.feature.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.arjunalabs.bikemap.feature.map.databinding.FragmentMapBinding
import com.mapbox.maps.Style
import com.zhuinden.simplestackextensions.fragments.DefaultFragmentKey
import com.zhuinden.simplestackextensions.fragments.KeyedFragment
import kotlinx.android.parcel.Parcelize

@Parcelize // typically data class
class MapScreen: DefaultFragmentKey() {
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
    }

}