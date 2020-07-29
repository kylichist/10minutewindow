package example

import com.github.kylichist.tenminutewindow.Manager

fun main() {
    val manager = Manager(
        onNew = {
            println(it.toString())
        }
    )
    manager.refreshMailboxState()
}