package com.opop.brazius.nbapredictor

import android.graphics.drawable.Drawable

interface WinnerListener {
    fun sendEastLogo(id: Drawable,teamName: String)
    fun sendWestLogo(id: Drawable,teamName: String)
    fun dismissWestLogo()
    fun dismissEastLogo()
}