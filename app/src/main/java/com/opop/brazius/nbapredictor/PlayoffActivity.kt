package com.opop.brazius.nbapredictor

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.design.widget.TabLayout
import android.support.v4.content.FileProvider
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.util.Log
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
            image.compress(Bitmap.CompressFormat.PNG, 95, stream)
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
        MobileAds.initialize(this, getString(R.string.ad_id))
        mAdView = findViewById<AdView>(R.id.adView)
        val adRequest = AdRequest.Builder()
                .build()
        mAdView?.loadAd(adRequest)
        val data = intent.extras.getParcelableArrayList<Team>("data")

        val eastTeams = ArrayList<Team>()
        val westTeams = ArrayList<Team>()
        for (i in 1..15) {
            if (data[i].place != 0) {
                eastTeams.add(data[i])
            }
        }
        for (i in 17..31) {
            if (data[i].place != 0) {
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
        b1 = getScreenShot(view)
    }

    override fun sendEastScreenshot(view: View) {
        b2 = getScreenShot(view)
    }

    override fun sendWestScreenshot(view: View) {
        b3 = getScreenShot(view)
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
