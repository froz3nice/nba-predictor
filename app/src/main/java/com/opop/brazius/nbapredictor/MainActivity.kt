package com.opop.brazius.nbapredictor

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds

class MainActivity : AppCompatActivity() {

    var mRecyclerView : RecyclerView? = null
    var mLayoutManager : LinearLayoutManager? = null
    var adapter : ListAdapter? = null
    var mAdView: AdView? = null
    lateinit var mInterstitialAd: InterstitialAd

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        MobileAds.initialize(this, getString(R.string.ad_id))
        MobileAds.initialize(this,getString(R.string.ad_interstitial))
        mAdView = findViewById<AdView>(R.id.adView)
        val adRequest = AdRequest.Builder()
                .build()

        mAdView?.loadAd(adRequest)
        mInterstitialAd = InterstitialAd(this)
        mInterstitialAd.adUnitId = getString(R.string.ad_interstitial)
        mInterstitialAd.loadAd(AdRequest.Builder().build())
        mRecyclerView = findViewById(R.id.recycler_view)
        mLayoutManager = LinearLayoutManager(this)

        adapter = ListAdapter(this)
        adapter?.setHasStableIds(true)
        mRecyclerView?.adapter = adapter
        mRecyclerView?.setHasFixedSize(true)
        mRecyclerView?.layoutManager = mLayoutManager
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (mInterstitialAd.isLoaded) {
            mInterstitialAd.show()
        } else {
            Log.d("TAG", "The interstitial wasn't loaded yet.")
        }
    }
}
