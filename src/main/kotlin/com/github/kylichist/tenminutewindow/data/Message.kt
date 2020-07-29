package com.github.kylichist.tenminutewindow.data

data class Message(val from: String, val subject: String, val name: String,
                   val date: String, val message: String, val mid: String,
                   var attachments: List<Attachment>? = null)