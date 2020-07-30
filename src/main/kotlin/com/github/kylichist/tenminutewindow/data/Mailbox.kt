package com.github.kylichist.tenminutewindow.data

data class Mailbox(
    val address: String, val millisecondsLeft: Long,
    val host: String, val link: String, val key: String,
    val messages: MutableList<Message> = mutableListOf()
)