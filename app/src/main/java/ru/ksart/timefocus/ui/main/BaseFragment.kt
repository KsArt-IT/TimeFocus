package ru.ksart.timefocus.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

abstract class BaseFragment<T : ViewBinding>(private val inflate: Inflate<T>) : Fragment() {

    protected var _binding: T? = null
    protected val binding get() = checkNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflate.invoke(inflater, container, false).also { _binding = it }.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
