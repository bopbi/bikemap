package com.arjunalabs.bikemap.feature.home

import android.content.ContentValues
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.arjunalabs.bikemap.feature.home.databinding.FragmentHomeBinding
import com.arjunalabs.bikemap.utility.navigation.ScreenNavigator
import com.zhuinden.simplestackextensions.fragments.DefaultFragmentKey
import com.zhuinden.simplestackextensions.fragments.KeyedFragment
import kotlinx.android.parcel.Parcelize
import timber.log.Timber

@Parcelize
class HomeScreen: DefaultFragmentKey() {
    override fun instantiateFragment(): Fragment = HomeFragment()
}

class HomeFragment : KeyedFragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonMap.setOnClickListener {
            (activity as? ScreenNavigator)?.redirectTo(
                uri = Uri.parse("content://bikemap/map"),
                contentValues = ContentValues().apply {
                    put("latitude", 40.7135)
                    put("longitude", -74.0066)
                    put("pitch", 45.0)
                    put("zoom", 15.5)
                    put("bearing", -17.6)
                }
            )
        }
    }
}