package com.opop.brazius.nbapredictor

import android.content.Context
import android.preference.PreferenceManager

class Prefs {
    companion object {
        val WEST1 = "West1"
        val WEST2 = "West2"
        val WEST3 = "West3"
        val WEST4 = "West4"
        val WEST5 = "West5"
        val WEST6 = "West6"
        val WEST7 = "West7"
        val WEST8 = "West8"
        val EAST1 = "EAST1"
        val EAST2 = "EAST2"
        val EAST3 = "EAST3"
        val EAST4 = "EAST4"
        val EAST5 = "EAST5"
        val EAST6 = "EAST6"
        val EAST7 = "EAST7"
        val EAST8 = "EAST8"
        val WEST18 = "WEST18"
        val WEST27 = "WEST27"
        val WEST36 = "WEST36"
        val WEST45 = "WEST45"
        val WESTSEMI1 = "WESTSEMI1"
        val WESTSEMI2 = "WESTSEMI2"
        val WESTFINAL = "WESTFINAL"

        val EAST18 = "EAST18"
        val EAST27 = "EAST27"
        val EAST36 = "EAST36"
        val EAST45 = "EAST45"
        val EASTSEMI1 = "EASTSEMI1"
        val EASTSEMI2 = "EASTSEMI2"
        val EASTFINAL = "EASTFINAL"

        val NBAFINALS = "NBAFINALS"

        fun setWestPlaces(ctx: Context, a: String,b: String, c: String,d: String,e: String,f: String,g: String,h: String){
            PreferenceManager.getDefaultSharedPreferences(ctx).edit().putString(WEST1,a).apply()
            PreferenceManager.getDefaultSharedPreferences(ctx).edit().putString(WEST2,b).apply()
            PreferenceManager.getDefaultSharedPreferences(ctx).edit().putString(WEST3,c).apply()
            PreferenceManager.getDefaultSharedPreferences(ctx).edit().putString(WEST4,d).apply()
            PreferenceManager.getDefaultSharedPreferences(ctx).edit().putString(WEST5,e).apply()
            PreferenceManager.getDefaultSharedPreferences(ctx).edit().putString(WEST6,f).apply()
            PreferenceManager.getDefaultSharedPreferences(ctx).edit().putString(WEST7,g).apply()
            PreferenceManager.getDefaultSharedPreferences(ctx).edit().putString(WEST8,h).apply()
        }

        fun setEastPlaces(ctx: Context, a: String,b: String, c: String,d: String,e: String,f: String,g: String,h: String){
            PreferenceManager.getDefaultSharedPreferences(ctx).edit().putString(EAST1,a).apply()
            PreferenceManager.getDefaultSharedPreferences(ctx).edit().putString(EAST2,b).apply()
            PreferenceManager.getDefaultSharedPreferences(ctx).edit().putString(EAST3,c).apply()
            PreferenceManager.getDefaultSharedPreferences(ctx).edit().putString(EAST4,d).apply()
            PreferenceManager.getDefaultSharedPreferences(ctx).edit().putString(EAST5,e).apply()
            PreferenceManager.getDefaultSharedPreferences(ctx).edit().putString(EAST6,f).apply()
            PreferenceManager.getDefaultSharedPreferences(ctx).edit().putString(EAST7,g).apply()
            PreferenceManager.getDefaultSharedPreferences(ctx).edit().putString(EAST8,h).apply()
        }

        fun setWestRound1(ctx: Context,west18: String,west27: String,west36: String,west45: String,westsemi1: String,westsemi2: String,westfinal: String){
            PreferenceManager.getDefaultSharedPreferences(ctx).edit().putString(WEST18,west18).apply()
            PreferenceManager.getDefaultSharedPreferences(ctx).edit().putString(WEST27,west27).apply()
            PreferenceManager.getDefaultSharedPreferences(ctx).edit().putString(WEST36,west36).apply()
            PreferenceManager.getDefaultSharedPreferences(ctx).edit().putString(WEST45,west45).apply()
            PreferenceManager.getDefaultSharedPreferences(ctx).edit().putString(WESTSEMI1,westsemi1).apply()
            PreferenceManager.getDefaultSharedPreferences(ctx).edit().putString(WESTSEMI2,westsemi2).apply()
            PreferenceManager.getDefaultSharedPreferences(ctx).edit().putString(WESTFINAL,westfinal).apply()

        }

        fun setEastRound1(ctx: Context,west18: String,west27: String,west36: String,west45: String,westsemi1: String,westsemi2: String,westfinal: String){
            PreferenceManager.getDefaultSharedPreferences(ctx).edit().putString(EAST18,west18).apply()
            PreferenceManager.getDefaultSharedPreferences(ctx).edit().putString(EAST27,west27).apply()
            PreferenceManager.getDefaultSharedPreferences(ctx).edit().putString(EAST36,west36).apply()
            PreferenceManager.getDefaultSharedPreferences(ctx).edit().putString(EAST45,west45).apply()
            PreferenceManager.getDefaultSharedPreferences(ctx).edit().putString(EASTSEMI1,westsemi1).apply()
            PreferenceManager.getDefaultSharedPreferences(ctx).edit().putString(EASTSEMI2,westsemi2).apply()
            PreferenceManager.getDefaultSharedPreferences(ctx).edit().putString(EASTFINAL,westfinal).apply()
        }

        fun setNbaFinals(ctx: Context,finals: String){
            PreferenceManager.getDefaultSharedPreferences(ctx).edit().putString(NBAFINALS,finals).apply()
        }

        fun getWinners(ctx: PlayoffActivity?): String? {
            var str = StringBuilder()
            val prefs = PreferenceManager.getDefaultSharedPreferences(ctx)
            str.append("Eastern Conference \n \n")
            str.append("1 seed : "+prefs.getString(EAST1,"")+"\n")
            str.append("2 seed : "+prefs.getString(EAST2,"")+"\n")
            str.append("3 seed : "+prefs.getString(EAST3,"")+"\n")
            str.append("4 seed : "+prefs.getString(EAST4,"")+"\n")
            str.append("5 seed : "+prefs.getString(EAST5,"")+"\n")
            str.append("6 seed : "+prefs.getString(EAST6,"")+"\n")
            str.append("7 seed : "+prefs.getString(EAST7,"")+"\n")
            str.append("8 seed : "+prefs.getString(EAST8,"")+ "\n\n")
            str.append("Western Conference \n \n")
            str.append("1 seed : "+prefs.getString(WEST1,"")+"\n")
            str.append("2 seed : "+prefs.getString(WEST2,"")+"\n")
            str.append("3 seed : "+prefs.getString(WEST3,"")+"\n")
            str.append("4 seed : "+prefs.getString(WEST4,"")+"\n")
            str.append("5 seed : "+prefs.getString(WEST5,"")+"\n")
            str.append("6 seed : "+prefs.getString(WEST6,"")+"\n")
            str.append("7 seed : "+prefs.getString(WEST7,"")+"\n")
            str.append("8 seed : "+prefs.getString(WEST8,"")+"\n\n")
            str.append("Eastern Conference Playoffs \n \n")
            str.append("Round 1 \n\n"+prefs.getString(EAST18,"")+"\n")
            str.append(prefs.getString(EAST27,"")+"\n")
            str.append(prefs.getString(EAST36,"")+"\n")
            str.append(prefs.getString(EAST45,"")+"\n")
            str.append("Eastern Conference Semifinals \n \n")
            str.append(prefs.getString(EASTSEMI1,"")+"\n")
            str.append(prefs.getString(EASTSEMI2,"")+"\n")
            str.append("Eastern Conference Finals \n \n")
            str.append(prefs.getString(EASTFINAL,"")+"\n\n")
            str.append("Western Conference Playoffs \n \n")
            str.append("Round 1 \n\n"+prefs.getString(WEST18,"")+"\n")
            str.append(prefs.getString(WEST27,"")+"\n")
            str.append(prefs.getString(WEST36,"")+"\n")
            str.append(prefs.getString(WEST45,"")+"\n")
            str.append("Western Conference Semifinals \n \n")
            str.append(prefs.getString(WESTSEMI1,"")+"\n")
            str.append(prefs.getString(WESTSEMI2,"")+"\n")
            str.append("Western Conference Finals \n \n")
            str.append(prefs.getString(WESTFINAL,"")+"\n")
            str.append("NBA Finals \n \n")
            str.append(prefs.getString(NBAFINALS,"")+"\n")
            return str.toString()
        }
    }
}