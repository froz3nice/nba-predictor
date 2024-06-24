package com.opop.brazius.nbapredictor

import android.content.Intent
import android.graphics.*
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.preference.PreferenceManager
import androidx.annotation.RequiresApi
import com.google.android.material.tabs.TabLayout
import androidx.core.content.FileProvider
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.PixelCopy
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.github.jinatonic.confetti.CommonConfetti
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.gson.Gson
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


class PlayoffActivity : AppCompatActivity(), WinnerListener, FinalsFragment.ShareListener, FragmentScreenshotListener {
    var b1: Bitmap? = null
    var b2: Bitmap? = null
    var b3: Bitmap? = null
    var mAdView: AdView? = null

    override fun share() {
        val bitmapArray = ArrayList<Bitmap>()
        bitmapArray.add(b1!!)
        bitmapArray.add(b2!!)
        bitmapArray.add(b3!!)

        shareImageUri(saveImage(mergeMultiple(bitmapArray)))
    }

    private fun mergeMultiple(parts: ArrayList<Bitmap>): Bitmap {
        val result = Bitmap.createBitmap(parts[0].width * 2, parts[0].height * 2, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(result)
        val paint = Paint()
        for (i in 0..2) {
            val f1 = parts[i].width * (i % 2)
            val f2 = parts[i].height * (i / 2)
            canvas.drawBitmap(parts[i], f1.toFloat(), f2.toFloat(), paint)
        }
        return result
    }

    private fun shareImageUri(uri: Uri?) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.putExtra(Intent.EXTRA_STREAM, uri)
        intent.putExtra(Intent.EXTRA_TEXT,Prefs.getWinners(this));
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        intent.type = "image/png"
        startActivity(intent)
    }

    fun saveImage(image: Bitmap): Uri? {
        //TODO - Should be processed in another thread
        val imagesFolder = File(this.cacheDir, "images")
        var uri: Uri? = null
        try {
            imagesFolder.mkdirs()
            val file = File(imagesFolder, "saved_img.png")
            val stream = FileOutputStream(file)
            image.compress(Bitmap.CompressFormat.PNG, 92, stream)
            stream.flush()
            stream.close()
            uri = FileProvider.getUriForFile(this, "com.opop.brazius", file)

        } catch (e: IOException) {
            Log.d("tg", "IOException while trying to write file for sharing: " + e.message)
        }

        return uri
    }

    fun getScreenShot(view: View): Bitmap {
        val screenView = view.rootView
        screenView.isDrawingCacheEnabled = true
        val bitmap = Bitmap.createBitmap(screenView.drawingCache)
        screenView.isDrawingCacheEnabled = false
        return bitmap
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun saveScreenshot(view: View, whichView: Int) {
        val window = (view.context as PlayoffActivity).window
        if (window != null) {
            val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
            val locationOfViewInWindow = IntArray(2)
            view.getLocationInWindow(locationOfViewInWindow)
            try {
                PixelCopy.request(window, Rect(locationOfViewInWindow[0], locationOfViewInWindow[1], locationOfViewInWindow[0] + view.width, locationOfViewInWindow[1] + view.height), bitmap, { copyResult ->
                    if (copyResult == PixelCopy.SUCCESS) {
                        when (whichView) {
                            1 -> b1 = bitmap
                            2 -> b2 = bitmap
                            else -> b3 = bitmap
                        }
                    }
                    // possible to handle other result codes ...
                }, Handler())
            } catch (e: IllegalArgumentException) {
                // PixelCopy may throw IllegalArgumentException, make sure to handle it
            }
        }
    }

    override fun sendWestLogo(id: Drawable, teamName: String) {
        val tag = "android:switcher:" + R.id.viewpager + ":" + 2
        val f = supportFragmentManager.findFragmentByTag(tag) as FinalsFragment
        f.changeWestLogo(id, teamName)
    }

    override fun dismissWestLogo() {
        val tag = "android:switcher:" + R.id.viewpager + ":" + 2
        val f = supportFragmentManager.findFragmentByTag(tag) as FinalsFragment
        f.westWinnerDismissed()
    }

    override fun dismissEastLogo() {
        val tag = "android:switcher:" + R.id.viewpager + ":" + 2
        val f = supportFragmentManager.findFragmentByTag(tag) as FinalsFragment
        f.eastWinnerDismissed()
    }

    override fun sendEastLogo(id: Drawable, teamName: String) {
        val tag = "android:switcher:" + R.id.viewpager + ":" + 2
        val f = supportFragmentManager.findFragmentByTag(tag) as FinalsFragment
        f.changeEastLogo(id, teamName)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_playoff)
        MobileAds.initialize(this, getString(R.string.appId))
        mAdView = findViewById<AdView>(R.id.adView)
        val adRequest = AdRequest.Builder()
                .build()
        mAdView?.loadAd(adRequest)
        val data = intent?.extras?.getParcelableArrayList<Team>("data")

        val eastTeams = ArrayList<Team>()
        val westTeams = ArrayList<Team>()
        for (i in 1..15) {
            if (data!![i].place != 0) {
                eastTeams.add(data[i])
            }
        }
        for (i in 17..31) {
            if (data!![i].place != 0) {
                westTeams.add(data[i])
            }
        }

        val viewPager = findViewById<View>(R.id.viewpager) as ViewPager
        viewPager.offscreenPageLimit = 2
        val adapter = ViewPagerAdapter(supportFragmentManager)
        viewPager.adapter = adapter

        val tabLayout = findViewById<TabLayout>(R.id.tabs)
        tabLayout.setupWithViewPager(viewPager)
        val gson = Gson()
        westTeams.sortBy { it.place }
        eastTeams.sortBy { it.place }
        Prefs.setEastPlaces(this,eastTeams[0].name,eastTeams[1].name,eastTeams[2].name,eastTeams[3].name,eastTeams[4].name,eastTeams[5].name,eastTeams[6].name,eastTeams[7].name)
        Prefs.setWestPlaces(this,westTeams[0].name,westTeams[1].name,westTeams[2].name,westTeams[3].name,westTeams[4].name,westTeams[5].name,westTeams[6].name,westTeams[7].name)


        val json = gson.toJson(eastTeams)
        val json2 = gson.toJson(westTeams)

        PreferenceManager.getDefaultSharedPreferences(this).edit().putString("east", json).apply()
        PreferenceManager.getDefaultSharedPreferences(this).edit().putString("west", json2).apply()

        Toast.makeText(this, "Press on a team logo to select winner", Toast.LENGTH_LONG).show()
    }

    override fun showConfetti() {
        val rootView = (findViewById<View>(android.R.id.content) as ViewGroup).getChildAt(0) as ViewGroup
        CommonConfetti.rainingConfetti(rootView, intArrayOf(Color.RED, Color.BLACK, Color.BLUE, Color.WHITE, Color.CYAN, Color.GREEN, Color.MAGENTA, Color.YELLOW))
                .oneShot()
    }

    override fun sendFinalsScreenshot(view: View) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O){
            saveScreenshot(view,1)
        }else {
            b1 = getScreenShot(view)
        }
    }

    override fun sendEastScreenshot(view: View) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O){
            saveScreenshot(view,2)
        }else {
            b2 = getScreenShot(view)
        }
    }

    override fun sendWestScreenshot(view: View) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O){
            saveScreenshot(view,3)
        }else {
            b3 = getScreenShot(view)
        }
    }

    public override fun onPause() {
        if (mAdView != null) {
            mAdView?.pause()
        }
        super.onPause()
    }

    public override fun onResume() {
        super.onResume()
        if (mAdView != null) {
            mAdView?.resume()
        }
    }

}
