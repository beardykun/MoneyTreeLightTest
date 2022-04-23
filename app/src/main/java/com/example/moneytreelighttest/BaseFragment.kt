package com.example.moneytreelighttest

import androidx.fragment.app.Fragment

abstract class BaseFragment() : Fragment() {

    fun showProgressView() {
        activity?.let {
            (it as? MainActivity)?.showProgressView()
        }
    }

    fun hideProgressView() {
        activity?.let {
            (it as? MainActivity)?.hideProgressView()
        }
    }
}