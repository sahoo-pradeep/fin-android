package sahoo.projects.fin.util

import java.time.Month

object Formatter {
    fun Month.twoDigits(): String {
        return String.format("%02d", this.value)
    }
}