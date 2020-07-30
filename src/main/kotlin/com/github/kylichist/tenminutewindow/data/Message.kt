package com.github.kylichist.tenminutewindow.data

data class Message(
    val from: String, val subject: String, val name: String,
    val date: String, val text: String, val mid: String,
    var attachments: MutableList<Attachment>? = null
)