package com.github.kylichist.tenminutewindow.data

import com.github.kylichist.tenminutewindow.util.AttachmentList
import com.github.kylichist.tenminutewindow.util.MessageList

data class Mailbox(
    val address: String, val millisecondsLeft: Long,
    val host: String, val link: String, val key: String,
    val messages: MessageList = mutableListOf()
) {
    fun isEmpty() = messages.isEmpty()
}

data class Message(
    val from: String, val subject: String, val name: String,
    val date: String, val text: String, val mid: String,
    var attachments: AttachmentList?
)

data class Attachment(val link: String, val title: String)