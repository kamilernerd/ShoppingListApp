package com.kamil.piano

data class PianoSheet(val note:String, val startTimeOfNote:Long, val noteTimeDuration:Long){
    override fun toString(): String {
        return "Note: $note , Started playing at: $startTimeOfNote , Lasted for: $noteTimeDuration"
    }
}
