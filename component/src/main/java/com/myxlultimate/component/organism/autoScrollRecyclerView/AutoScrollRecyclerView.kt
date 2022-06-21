package com.myxlultimate.component.organism.autoScrollRecyclerView

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class AutoScrollRecyclerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {
    private val TAG = AutoScrollRecyclerView::class.java.simpleName

    var scrollJob : Job? = null
    var lifecycleScopeOwner : LifecycleOwner? = null
    fun autoScroll(lifecycleScopeOwner: LifecycleOwner){
        this.lifecycleScopeOwner = lifecycleScopeOwner
        scrollJob?.cancel("")
        scrollJob = lifecycleScopeOwner.lifecycleScope.launch {
            delay(5000)
            val smoothScroller = LinearSmoothScroller(context)
            smoothScroller.targetPosition = ((layoutManager as LinearLayoutManager).findFirstVisibleItemPosition() + 1) % (adapter?.itemCount?:1)
            layoutManager?.startSmoothScroll(smoothScroller)
            autoScroll(lifecycleScopeOwner)
        }
    }

    override fun onScrolled(dx: Int, dy: Int) {
        super.onScrolled(dx, dy)
        lifecycleScopeOwner?.let {
            autoScroll(it)
        }
    }

    override fun setOnClickListener(l: OnClickListener?) {
        super.setOnClickListener(l)
        lifecycleScopeOwner?.let {
            it.lifecycleScope.launch {
                delay(3000)
            }
            autoScroll(it)
        }
    }

}