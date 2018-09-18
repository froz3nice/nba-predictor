package com.opop.brazius.nbapredictor

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.os.Handler
import android.preference.PreferenceManager
import android.support.v4.app.Fragment
import android.view.*
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.google.android.gms.ads.AdView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class EastFragment : Fragment() {
    var data: ArrayList<Team>? = null

    var iv1: ImageView? = null
    var iv2: ImageView? = null
    var iv3: ImageView? = null
    var iv4: ImageView? = null
    var iv5: ImageView? = null
    var iv6: ImageView? = null
    var iv7: ImageView? = null
    var iv8: ImageView? = null
    var iv9: ImageView? = null
    var iv10: ImageView? = null
    var iv11: ImageView? = null
    var iv12: ImageView? = null
    var iv13: ImageView? = null
    var iv14: ImageView? = null
    var score1: TextView? = null
    var score2: TextView? = null
    var score3: TextView? = null
    var score4: TextView? = null
    var score5: TextView? = null
    var score6: TextView? = null
    var score7: TextView? = null
    var score8: TextView? = null
    var score9: TextView? = null
    var score10: TextView? = null
    var score11: TextView? = null
    var score12: TextView? = null
    var score13: TextView? = null
    var score14: TextView? = null
    var tv1: TextView? = null
    var tv2: TextView? = null
    var tv3: TextView? = null
    var tv4: TextView? = null
    var tv5: TextView? = null
    var tv6: TextView? = null
    var tv7: TextView? = null
    var tv8: TextView? = null
    var tv9: TextView? = null
    var tv10: TextView? = null
    var tv11: TextView? = null
    var tv12: TextView? = null
    var tv13: TextView? = null
    var tv14: TextView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.playoff_fragment, container, false)
        init(view)
        initImgClickListeners()
        setLogos()
        return view
    }

    fun init(view: View) {
        iv1 = view.findViewById(R.id.iv1)
        iv2 = view.findViewById(R.id.iv2)
        iv3 = view.findViewById(R.id.iv3)
        iv4 = view.findViewById(R.id.iv4)
        iv5 = view.findViewById(R.id.iv5)
        iv6 = view.findViewById(R.id.iv6)
        iv7 = view.findViewById(R.id.iv7)
        iv8 = view.findViewById(R.id.iv8)
        iv9 = view.findViewById(R.id.iv9)
        iv10 = view.findViewById(R.id.iv10)
        iv11 = view.findViewById(R.id.iv11)
        iv12 = view.findViewById(R.id.iv12)
        iv13 = view.findViewById(R.id.iv13)
        iv14 = view.findViewById(R.id.iv14)
        score1 = view.findViewById(R.id.score1)
        score2 = view.findViewById(R.id.score2)
        score3 = view.findViewById(R.id.score3)
        score4 = view.findViewById(R.id.score4)
        score5 = view.findViewById(R.id.score5)
        score6 = view.findViewById(R.id.score6)
        score7 = view.findViewById(R.id.score7)
        score8 = view.findViewById(R.id.score8)
        score9 = view.findViewById(R.id.score9)
        score10 = view.findViewById(R.id.score10)
        score11 = view.findViewById(R.id.score11)
        score12 = view.findViewById(R.id.score12)
        score13 = view.findViewById(R.id.score13)
        score14 = view.findViewById(R.id.score14)
        tv1 = view.findViewById(R.id.tv1)
        tv2 = view.findViewById(R.id.tv2)
        tv3 = view.findViewById(R.id.tv3)
        tv4 = view.findViewById(R.id.tv4)
        tv5 = view.findViewById(R.id.tv5)
        tv6 = view.findViewById(R.id.tv6)
        tv7 = view.findViewById(R.id.tv7)
        tv8 = view.findViewById(R.id.tv8)
        tv9 = view.findViewById(R.id.tv9)
        tv10 = view.findViewById(R.id.tv10)
        tv11 = view.findViewById(R.id.tv11)
        tv12 = view.findViewById(R.id.tv12)
        tv13 = view.findViewById(R.id.tv13)
        tv14 = view.findViewById(R.id.tv14)
    }

    fun setLogos() {
        val gson = Gson()
        val response = PreferenceManager.getDefaultSharedPreferences(context).getString("east", "")
        data = gson.fromJson<ArrayList<Team>>(response,
                object : TypeToken<List<Team>>() {}.type)

        iv1?.setImageResource(data!![0].logo)
        iv2?.setImageResource(data!![7].logo)
        iv3?.setImageResource(data!![3].logo)
        iv4?.setImageResource(data!![4].logo)
        iv5?.setImageResource(data!![2].logo)
        iv6?.setImageResource(data!![5].logo)
        iv7?.setImageResource(data!![1].logo)
        iv8?.setImageResource(data!![6].logo)
        iv1?.tag = data!![0].name
        iv2?.tag = data!![7].name
        iv3?.tag = data!![3].name
        iv4?.tag = data!![4].name
        iv5?.tag = data!![2].name
        iv6?.tag = data!![5].name
        iv7?.tag = data!![1].name
        iv8?.tag = data!![6].name
        tv1?.text = data!![0].abbr
        tv2?.text = data!![7].abbr
        tv3?.text = data!![3].abbr
        tv4?.text = data!![4].abbr
        tv5?.text = data!![2].abbr
        tv6?.text = data!![5].abbr
        tv7?.text = data!![1].abbr
        tv8?.text = data!![6].abbr
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
            winnerLogic(iv2, iv1, score1, score2, tv1, tv2, iv9, score9, score13, tv9, iv13, tv13)
            if (isBlurry(iv1)) showDialog(iv1)
        }
        iv2?.setOnClickListener { _ ->
            winnerLogic(iv1, iv2, score2, score1, tv2, tv1, iv9, score9, score13, tv9, iv13, tv13)
            if (isBlurry(iv2)) showDialog(iv2)
        }
        iv3?.setOnClickListener { _ ->
            winnerLogic(iv4, iv3, score3, score4, tv3, tv4, iv10, score10, score13, tv10, iv13, tv13)
            if (isBlurry(iv3)) showDialog(iv3)
        }
        iv4?.setOnClickListener { _ ->
            winnerLogic(iv3, iv4, score4, score3, tv4, tv3, iv10, score10, score13, tv10, iv13, tv13)
            if (isBlurry(iv4)) showDialog(iv4)
        }
        iv5?.setOnClickListener { _ ->
            winnerLogic(iv6, iv5, score5, score6, tv5, tv6, iv11, score11, score14, tv11, iv14, tv14)
            if (isBlurry(iv5)) showDialog(iv5)
        }
        iv6?.setOnClickListener { _ ->
            winnerLogic(iv5, iv6, score6, score5, tv6, tv5, iv11, score11, score14, tv11, iv14, tv14)
            if (isBlurry(iv6)) showDialog(iv6)
        }
        iv7?.setOnClickListener { _ ->
            winnerLogic(iv8, iv7, score7, score8, tv7, tv8, iv12, score12, score14, tv12, iv14, tv14)
            if (isBlurry(iv7)) showDialog(iv7)
        }
        iv8?.setOnClickListener { _ ->
            winnerLogic(iv7, iv8, score8, score7, tv8, tv7, iv12, score12, score14, tv12, iv14, tv14)
            if (isBlurry(iv8)) showDialog(iv8)
        }
        iv9?.setOnClickListener { _ ->
            winnerLogic(iv10, iv9, score9, score10, tv9, tv10, iv13, score13, null, tv13, null, null)
            if (isBlurry(iv9)) showDialog(iv9)
        }
        iv10?.setOnClickListener { _ ->
            winnerLogic(iv9, iv10, score10, score9, tv10, tv9, iv13, score13, null, tv13, null, null)
            if (isBlurry(iv10)) showDialog(iv10)
        }
        iv11?.setOnClickListener { _ ->
            winnerLogic(iv12, iv11, score11, score12, tv11, tv12, iv14, score14, null, tv14, null, null)
            if (isBlurry(iv11)) showDialog(iv11)
        }
        iv12?.setOnClickListener { _ ->
            winnerLogic(iv11, iv12, score12, score11, tv12, tv11, iv14, score14, null, tv14, null, null)
            if (isBlurry(iv12)) showDialog(iv12)
        }
        iv13?.setOnClickListener { _ ->
            winnerLogic(iv14, iv13, score13, score14, tv13, tv14, null, null, null, null, null, null)
            if (isBlurry(iv13)) showDialog(iv13)
        }
        iv14?.setOnClickListener { _ ->
            winnerLogic(iv13, iv14, score14, score13, tv14, tv13, null, null, null, null, null, null)
            if (isBlurry(iv14)) showDialog(iv14)
        }
    }

    fun winnerLogic(iv1: ImageView?, iv2: ImageView?,
                    score: TextView?, score2: TextView?, abbr: TextView?, abbr2: TextView?,
                    ivNext: ImageView?, nextScore: TextView?, scoreFinal: TextView?, nextAbbr: TextView?, ivFinal: ImageView?, abbrFinal: TextView?) {
        if (!isBlurry(iv1)) {
            if (!isBlurry(iv2)) {
                score?.text = "4"
                score?.setTextColor(Color.BLACK)
                makeImgBlurry(iv1)
                score2?.text = "0"
                score2?.setTextColor(Color.parseColor("#808080"))
                abbr2?.setTextColor(Color.parseColor("#808080"))
                abbr?.setTextColor(Color.BLACK)
                if (ivNext != null) {
                    ivNext.visibility = View.VISIBLE
                    ivNext.setImageDrawable(iv2?.drawable)
                    ivNext.alpha = 1f
                    ivNext.tag = iv2?.tag
                    nextScore?.visibility = View.VISIBLE
                    nextAbbr?.text = abbr?.text
                    nextAbbr?.visibility = View.VISIBLE
                } else {
                    score?.visibility = View.VISIBLE
                    shareListener?.sendEastLogo(iv2!!.drawable,iv2.tag as String)
                    Handler().postDelayed( {
                        val view = activity!!.findViewById<AdView>(R.id.adView)
                        view.visibility = View.INVISIBLE
                        screenshotListener?.sendEastScreenshot(view!!)
                        view.visibility = View.VISIBLE
                    }, 300)
                }
            }
        } else {
            score?.text = "0"
            score?.setTextColor(Color.parseColor("#808080"))
            abbr?.setTextColor(Color.parseColor("#808080"))
            if (!isBlurry(iv2)) makeImgClear(iv1)
            if (ivNext != null) {
                ivNext.visibility = View.INVISIBLE
                ivNext.setImageDrawable(iv2?.drawable)
                nextScore?.visibility = View.INVISIBLE
                nextScore?.text = "0"
                ivNext.tag = ""
                ivFinal?.tag = ""
                scoreFinal?.visibility = View.INVISIBLE
                nextAbbr?.visibility = View.INVISIBLE
                ivFinal?.visibility = View.INVISIBLE
                abbrFinal?.visibility = View.INVISIBLE
            } else {
                shareListener?.dismissEastLogo()
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
            iv3 -> return score3
            iv4 -> return score4
            iv5 -> return score5
            iv6 -> return score6
            iv7 -> return score7
            iv8 -> return score8
            iv9 -> return score9
            iv10 -> return score10
            iv11 -> return score11
            iv12 -> return score12
            iv13 -> return score13
            iv14 -> return score14
        }
        return null
    }

    var shareListener: WinnerListener? = null
    var screenshotListener: FragmentScreenshotListener? = null
    fun setScreenshotListener() {
        screenshotListener = activity as FragmentScreenshotListener
    }

    fun setListener() {
        shareListener = activity as WinnerListener
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
            val tabFragment = EastFragment()
            tabFragment.arguments = bundle
            return tabFragment
        }
    }
}