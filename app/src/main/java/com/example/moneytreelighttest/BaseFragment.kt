package com.example.moneytreelighttest

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
abstract class BaseFragment : Fragment() {

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

    //for inflating JetpackCompose UI
    fun Fragment.contentView(
        compositionStrategy: ViewCompositionStrategy = ViewCompositionStrategy.DisposeOnDetachedFromWindow,
        context: Context? = getContext(),
        content: @Composable () -> Unit
    ): ComposeView? {
        context ?: return null
        val view = ComposeView(context)
        view.setViewCompositionStrategy(compositionStrategy)
        view.setContent(content)
        return view
    }
}