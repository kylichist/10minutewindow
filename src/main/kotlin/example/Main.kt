package example

import com.github.kylichist.tenminutewindow.Manager

fun main() {
    val singleManager = Manager.single(onFirst = {
        println("Got it !!1! - ${it.message}")
    })
    val commonManager = Manager.common(onNext = {
        println("- ${it.message}\n- Ты опять выходишь на связь, мудила?")
    })
}