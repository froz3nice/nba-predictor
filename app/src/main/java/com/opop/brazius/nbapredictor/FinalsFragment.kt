package com.opop.brazius.nbapredictor

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.view.*
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.google.android.gms.ads.AdView


class FinalsFragment : Fragment() {

    interface ShareListener {
        fun share()
        fun showConfetti()
    }

    var nbaChampion = ""
    var eastDone = false
    var westDone = false
    fun changeEastLogo(id: Drawable, teamName: String) {
        iv1?.setImageDrawable(id)
        iv1?.tag = teamName
        iv1?.visibility = View.VISIBLE
        score1?.visibility = View.VISIBLE
        eastDone = true
    }

    fun changeWestLogo(id: Drawable, teamName: String) {
        iv2?.setImageDrawable(id)
        iv2?.tag = teamName
        iv2?.visibility = View.VISIBLE
        score2?.visibility = View.VISIBLE
        westDone = true
    }

    fun westWinnerDismissed() {
        iv2?.visibility = View.INVISIBLE
        score2?.visibility = View.INVISIBLE
        iv2?.tag = ""
        hideViews()
        nbaChampion = ""
        westDone = false
    }

    fun eastWinnerDismissed() {
        iv1?.visibility = View.INVISIBLE
        iv1?.tag = ""
        score1?.visibility = View.INVISIBLE
        hideViews()
        nbaChampion = ""
        eastDone = false
    }

    fun hideViews() {
        tvChamp?.visibility = View.INVISIBLE
        ivChamp?.visibility = View.INVISIBLE
        tvShare?.visibility = View.INVISIBLE
        ivShare?.visibility = View.INVISIBLE
    }

    fun showViews() {
        if (westDone && eastDone) {
            ivChamp?.visibility = View.VISIBLE
            tvChamp?.text = String.format("NBA Champion\n%s", nbaChampion)
            tvChamp?.visibility = View.VISIBLE
            Handler().postDelayed({
                tvRate?.visibility = View.INVISIBLE
                ivRate?.visibility = View.INVISIBLE
                val view = activity!!.findViewById<AdView>(R.id.adView)
                view.visibility = View.INVISIBLE
                screenshotListener?.sendFinalsScreenshot(view!!)
                view.visibility = View.VISIBLE
                tvRate?.visibility = View.VISIBLE
                ivRate?.visibility = View.VISIBLE
                listener?.showConfetti()
            }, 300)

            tvShare?.visibility = View.VISIBLE
            ivShare?.visibility = View.VISIBLE

        }
    }

    var data: ArrayList<Team>? = null

    var iv1: ImageView? = null
    var iv2: ImageView? = null
    var ivShare: ImageView? = null
    var tvShare: TextView? = null
    var ivRate: ImageView? = null
    var tvRate: TextView? = null
    var ivChamp: ImageView? = null
    var score1: TextView? = null
    var score2: TextView? = null
    var tvChamp: TextView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.finals_fragment, container, false)
        init(view)
        initImgClickListeners()
        return view
    }

    var v: View? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        v = view
    }


    fun init(view: View) {
        iv1 = view.findViewById(R.id.iv_left)
        iv2 = view.findViewById(R.id.iv_right)
        ivChamp = view.findViewById(R.id.iv_champ)
        tvChamp = view.findViewById(R.id.tv_champ)
        score1 = view.findViewById(R.id.tv_left)
        score2 = view.findViewById(R.id.tv_right)
        ivRate = view.findViewById(R.id.iv_rate)
        tvRate = view.findViewById(R.id.tv_rate)
        ivShare = view.findViewById(R.id.iv_share)
        tvShare = view.findViewById(R.id.tv_share)
        val typeFace = Typeface.createFromAsset(context!!.assets, "fonts/radioland.ttf")
        val typeFace2 = Typeface.createFromAsset(context!!.assets, "fonts/arcade.otf")
        score1?.typeface = typeFace
        score2?.typeface = typeFace
        tvChamp?.typeface = typeFace2
        ivShare?.setOnClickListener { _ ->
            listener?.share()
        }

        ivRate?.setOnClickListener { _ ->
            val appPackageName = "com.opop.brazius.nbapredictor"
            try {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$appPackageName")))
            } catch (anfe: android.content.ActivityNotFoundException) {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=$appPackageName")))
            }
        }
    }

    fun isBlurry(iv: ImageView?): Boolean {
        return iv?.alpha == 0.4f
    }

    fun makeImgBlurry(iv: ImageView?) {
        iv?.alpha = 0.4f
    }

    fun makeImgClear(iv: ImageView?) {
        iv?.alpha = 1f
    }

    fun initImgClickListeners() {
        iv1?.setOnClickListener { _ ->
            winnerLogic(iv2, iv1, score1, score2)
            if (isBlurry(iv1)) showDialog(iv1)
        }
        iv2?.setOnClickListener { _ ->
            winnerLogic(iv1, iv2, score2, score1)
            if (isBlurry(iv2)) showDialog(iv2)
        }
    }

    fun winnerLogic(iv1: ImageView?, iv2: ImageView?, score: TextView?, score2: TextView?) {
        if (!isBlurry(iv1)) {
            if (!isBlurry(iv2)) {
                nbaChampion = iv2?.tag as String
                ivChamp?.setImageDrawable(iv2.drawable)
                makeImgBlurry(iv1)
                score?.text = "4"
                score2?.text = "0"
                score?.setTextColor(Color.BLACK)
                showViews()
            }
        } else {
            score?.text = "0"
            score?.setTextColor(Color.parseColor("#808080"))
            if (!isBlurry(iv2)) {
                makeImgClear(iv1)
                nbaChampion = ""
                hideViews()
            }
        }
    }


    fun showDialog(iv: ImageView?) {
        val dialog = Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.dialog)
        dialog.window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)

        val b0 = dialog.findViewById<Button>(R.id.button)
        val b1 = dialog.findViewById<Button>(R.id.button2)
        val b2 = dialog.findViewById<Button>(R.id.button3)
        val b3 = dialog.findViewById<Button>(R.id.button4)
        val typeFace = Typeface.createFromAsset(context!!.assets, "fonts/radioland.ttf")

        b0.typeface = typeFace
        b1.typeface = typeFace
        b2.typeface = typeFace
        b3.typeface = typeFace
        b0.setOnClickListener { _ ->
            getScoreTextView(iv)?.text = "0"
            dialog.dismiss()
        }
        b1.setOnClickListener { _ ->
            getScoreTextView(iv)?.text = "1"
            dialog.dismiss()
        }
        b2.setOnClickListener { _ ->
            getScoreTextView(iv)?.text = "2"
            dialog.dismiss()
        }
        b3.setOnClickListener { _ ->
            getScoreTextView(iv)?.text = "3"
            dialog.dismiss()
        }

        dialog.show()

    }

    fun getScoreTextView(iv: ImageView?): TextView? {
        when (iv) {
            iv1 -> return score1
            iv2 -> return score2
        }
        return null
    }

    var listener: ShareListener? = null
    var screenshotListener: FragmentScreenshotListener? = null

    fun setListener() {
        listener = activity as ShareListener
    }

    fun setScreenshotListener() {
        screenshotListener = activity as FragmentScreenshotListener
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        setListener()
        setScreenshotListener()
    }

    companion object {
        fun getInstance(position: Int): Fragment {
            val bundle = Bundle()
            bundle.putInt("pos", position)
            val tabFragment = FinalsFragment()
            tabFragment.arguments = bundle
            return tabFragment
        }
    }
}