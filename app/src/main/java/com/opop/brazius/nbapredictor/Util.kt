package com.opop.brazius.nbapredictor

 class Util {
     companion object {
         fun isHeader(pos: Int): Boolean {
             return pos == 0 || pos == 16
         }

         fun isEast(pos: Int): Boolean {
             return pos in 1..15
         }


         fun isWest(pos: Int): Boolean {
             return pos in 17..31
         }
     }
}
