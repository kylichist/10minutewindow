package example

import com.github.kylichist.tenminutewindow.Manager

fun main() {
    val manager = Manager(
        autoExtend = true,
        autoRefresh = true,
        refreshPeriod = 10000
    )
    println(manager.mailbox.address)
    println(manager.mailbox.millisecondsLeft)
}