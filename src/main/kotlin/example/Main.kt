package example

import com.github.kylichist.tenminutewindow.Managers

fun main() {
    val commonManager = Managers.common {
        println("- ${it.text}\n- Ты опять выходишь на связь, мудила?")
    }.also { println(it.mailbox.address) }
}