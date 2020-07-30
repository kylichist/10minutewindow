package example

import com.github.kylichist.tenminutewindow.Manager

fun main() {
    val commonManager = Manager.common(onNext = {
        println("- ${it.text}\n- Ты опять выходишь на связь, мудила?")
    })
    println(commonManager.mailbox.address)
}