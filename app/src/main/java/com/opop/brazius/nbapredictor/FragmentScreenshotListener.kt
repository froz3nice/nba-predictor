package com.opop.brazius.nbapredictor

import android.view.View

interface FragmentScreenshotListener {
    fun sendEastScreenshot(view: View)
    fun sendWestScreenshot(view: View)
    fun sendFinalsScreenshot(view: View)
}