package com.opop.brazius.nbapredictor

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import java.util.*
import com.google.gson.Gson




class ListAdapter(private val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val dataSet: ArrayList<Team> = ArrayList()

    inner class ViewHolder(itemView: View, var name: TextView, var logo: ImageView)
        : RecyclerView.ViewHolder(itemView) {
        val layout = itemView.findViewById<RelativeLayout>(R.id.relative_layout)
        var place = itemView.findViewById<TextView>(R.id.tv_place)
    }

    init {
        dataSet.add(Team())
        dataSet.add(Team("Atlanta Hawks", R.drawable.hawks,"Atl"))
        dataSet.add(Team("Boston Celtics", R.drawable.boston,"Bos"))
        dataSet.add(Team("Brooklyn Nets", R.drawable.brooklin,"Bkn"))
        dataSet.add(Team("Charlotte Hornets", R.drawable.hornets,"Cha"))
        dataSet.add(Team("Chicago Bulls", R.drawable.bulls,"Chi"))
        dataSet.add(Team("Cleveland Cavaliers", R.drawable.cavs,"Cle"))
        dataSet.add(Team("Detroit Pistons", R.drawable.pistons,"Det"))
        dataSet.add(Team("Indiana Pacers", R.drawable.pacers,"Ind"))
        dataSet.add(Team("Miami Heat", R.drawable.heat,"Mia"))
        dataSet.add(Team("Milwaukee Bucks", R.drawable.bucks,"Mil"))
        dataSet.add(Team("New York Knicks", R.drawable.knicks,"NY"))
        dataSet.add(Team("Orlando Magic", R.drawable.magic,"Orl"))
        dataSet.add(Team("Philadelphia 76ers", R.drawable.sevnty6ers,"Phi"))
        dataSet.add(Team("Toronto Raptors", R.drawable.raps,"Tor"))
        dataSet.add(Team("Washington Wizards", R.drawable.wizards,"Wsh"))
        dataSet.add(Team())
        dataSet.add(Team("Dallas Mavericks", R.drawable.mavs,"Dal"))
        dataSet.add(Team("Denver Nuggets", R.drawable.denver,"Den"))
        dataSet.add(Team("Golden State Warriors", R.drawable.warrior,"GS"))
        dataSet.add(Team("Houston Rockets", R.drawable.rockets,"Hou"))
        dataSet.add(Team("LA Clippers", R.drawable.clips,"LAC"))
        dataSet.add(Team("Los Angeles Lakers", R.drawable.lakers,"LAL"))
        dataSet.add(Team("Memphis Grizzlies", R.drawable.memphis,"Mem"))
        dataSet.add(Team("Minnesota Timberwolves", R.drawable.twolves,"Min"))
        dataSet.add(Team("New Orleans Pelicans", R.drawable.pelicans,"NOP"))
        dataSet.add(Team("Oklahoma City Thunder", R.drawable.okc,"OKC"))
        dataSet.add(Team("Phoenix Suns", R.drawable.suns,"Phx"))
        dataSet.add(Team("Portland Trail Blazers", R.drawable.blazers,"Por"))
        dataSet.add(Team("Sacramento Kings", R.drawable.kings,"Sac"))
        dataSet.add(Team("San Antonio Spurs", R.drawable.spurs,"SA"))
        dataSet.add(Team("Utah Jazz", R.drawable.utah,"Uth"))
    }

    private fun getPopulatedVH(v: View): ViewHolder {
        val name = v.findViewById<TextView>(R.id.tv_name)
        val logo = v.findViewById<ImageView>(R.id.iv_logo)

        return ViewHolder(v, name, logo)
    }

    private inner class HeaderViewHolder(headerView: View) : RecyclerView.ViewHolder(headerView) {
        val title: TextView = headerView.findViewById(R.id.textView) as TextView

        init {
            val typeFace = Typeface.createFromAsset(context.assets, "fonts/arcade.otf")
            this.title.typeface = typeFace
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): RecyclerView.ViewHolder {
        if (Util.isHeader(viewType)) {
            val v = LayoutInflater.from(parent.context)
                    .inflate(R.layout.section_header, parent, false)
            return HeaderViewHolder(v)
        } else if (Util.isEast(viewType)) {
            val v = LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_item_conferences, parent, false)
            return getPopulatedVH(v)
        } else {
            val v = LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_item_conferences, parent, false)
            return getPopulatedVH(v)
        }
    }

    var eastCounter = 0
    var westCounter = 0
    var westDone = false
    var eastDone = false
    override fun onBindViewHolder(holder1: RecyclerView.ViewHolder, position: Int) {
        val pos = holder1.adapterPosition
        val item = dataSet[pos]
        if (Util.isHeader(pos)) {
            val holder = holder1 as HeaderViewHolder
            when (pos) {
                0 -> holder.title.text = "Eastern Conference"
                16 -> holder.title.text = "Western Conference"
            }
        } else {
            val holder = holder1 as ViewHolder
            holder.logo.setImageResource(item.logo)
            holder.name.text = item.name
            if (item.place == 0 && item.isOut != "x") {
                holder.place.visibility = View.INVISIBLE
            }
            if (item.isOut == "x") {
                holder.place.setTextColor(Color.RED)
                holder.place.text = item.isOut
                holder.place.visibility = View.VISIBLE
            }else{
                holder.place.setTextColor(Color.BLACK)
            }
            if (item.place != 0) {
                holder.place.text = item.place.toString()
                holder.place.setTextColor(Color.BLACK)
            }

            holder.itemView.setOnClickListener { v ->
                if (pos in 1..15) {
                    changeEastPlaces(holder, pos)
                } else {
                    changeWestPlaces(holder, pos)
                }
                if (holder.place.visibility == View.VISIBLE && item.isOut != "x") {
                    holder.place.visibility = View.INVISIBLE
                } else {
                    holder.place.visibility = View.VISIBLE
                }
            }
        }
    }

    fun changeEastPlaces(holder: ViewHolder, pos: Int) {
        if (holder.place.visibility == View.VISIBLE) {
            if (dataSet[pos].isOut != "x") {
                eastCounter--
                for (i in 1..15) {
                    if (dataSet[i].place > dataSet[pos].place) {
                        dataSet[i].place--
                    }
                    if (dataSet[i].place == 0) {
                        dataSet[i].isOut = ""
                    }
                }
                eastDone = false
                dataSet[pos].place = 0
                notifyDataSetChanged()
            }
        } else {
            if (dataSet[pos].isOut != "x") {
                eastCounter++
                dataSet[pos].place = eastCounter
                holder.place.text = eastCounter.toString()
                if (eastCounter > 7) {
                    for (i in 1..15) {
                        if (dataSet[i].place < 1) {
                            dataSet[i].isOut = "x"
                            dataSet[i].place = 0
                        }
                    }
                    eastDone = true
                    if(westDone && eastDone){
                       startPlayoffActivity()
                    }
                    notifyDataSetChanged()
                }
            }
        }
    }

    fun startPlayoffActivity(){
        val intent = Intent(context, PlayoffActivity::class.java)
        intent.putParcelableArrayListExtra("data",dataSet)
        context.startActivity(intent)
    }

    fun changeWestPlaces(holder: ViewHolder, pos: Int) {
        if (holder.place.visibility == View.VISIBLE) {
            if (dataSet[pos].isOut != "x") {
                westCounter--
                for (i in 17..31) {
                    if (dataSet[i].place > dataSet[pos].place) {
                        dataSet[i].place--
                    }
                    if (dataSet[i].place == 0) {
                        dataSet[i].isOut = ""
                    }
                }
                westDone = false
                dataSet[pos].place = 0
                notifyDataSetChanged()
            }
        } else {
            if (dataSet[pos].isOut != "x") {
                westCounter++
                dataSet[pos].place = westCounter
                holder.place.text = westCounter.toString()
                if (westCounter > 7) {
                    for (i in 17..31 ) {
                        if (dataSet[i].place < 1) {
                            dataSet[i].isOut = "x"
                            dataSet[i].place = 0
                        }
                    }
                    westDone = true
                    if(westDone && eastDone){
                        startPlayoffActivity()
                    }
                    notifyDataSetChanged()
                }
            }
        }
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

}
