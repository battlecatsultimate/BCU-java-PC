package main

import java.text.SimpleDateFormat
import java.util.*

object Printer {
    fun e(source: String?, line: Int, description: String?) {
        Printer.print(source, line, description)
    }

    fun p(source: String?, line: Int, description: String?) {
        if (MainBCU.WRITE) return
        Printer.print(source, line, description)
    }

    fun r(i: Int, string: String?) {
        Printer.e("Reader", i, string)
    }

    fun w(i: Int, string: String?) {
        Printer.e("Writer", i, string)
    }

    private fun print(source: String, line: Int, description: String) {
        val date = Date()
        val h: String = SimpleDateFormat("HH").format(date)
        val m: String = SimpleDateFormat("mm").format(date)
        val s: String = SimpleDateFormat("ss").format(date)
        println("[$source:#$line,$h:$m:$s]:$description")
    }
}
