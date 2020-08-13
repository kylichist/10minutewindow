@file:Suppress("SpellCheckingInspection")

package com.github.kylichist.tenminutewindow.util

import com.github.kylichist.tenminutewindow.data.Attachment
import com.github.kylichist.tenminutewindow.data.Message

const val MAILBOX_INFO = "https://10minutemail.net/"
const val MAILBOX_CREATE = "https://10minutemail.net/new.html"
const val MAILBOX_EXTEND = "https://10minutemail.net/more100.html"
const val MESSAGES = "https://10minutemail.net/mailbox.ajax.php?"
const val MESSAGE_INFO = "https://10minutemail.net/readmail.html?mid="
const val BASE = "https://10minutemail.net/"

const val DEFAULT_AUTO_REFRESH_VALUE = true
const val MINIMUM_AUTO_REFRESH_PERIOD_VALUE: Long = 10000

internal const val MESSAGES_TABLE = "tbody"
internal const val MID_ATTR = "onclick"
internal const val MID_START = "?mid="
internal const val TEXT_QUERY = "div.mailinhtml"
internal const val MAIL_HEADER = "div.mail_header"
internal const val SUBJECT_QUERY = "h2.emoji_parse"
internal const val AUTHOR_INFO_QUERY = "div.mail_headerinfo"
internal const val AUTHOR_INFO_START = "span.mail_from"
internal const val AUTHOR_INFO_DELIMITER = " ("
internal const val DATE_QUERY = "[title]"
internal const val DATE_ATTR = "title"
internal const val ATTACHMENTS_QUERY = "div.mail_att"
internal const val ATTACHMENTS_ATTR = "a"
internal const val ATT_LINK_ATTR = "href"
internal const val ATT_TITLE_ATTR = "title"
internal const val MAIL_KEY = "mail"
internal const val HOST_KEY = "host"
internal const val URL_KEY = "url"
internal const val KEY_KEY = "key" //lol

internal const val TIME_START = "var today = new Date();var tt = "
internal const val TIME_END = ";var time = today.getTime();"
internal const val JSON_START = "var permalink = '"
internal const val JSON_END = "';if(localStorgeSupportCheck()"

typealias AttachmentList = MutableList<Attachment>
typealias MessageList = MutableList<Message>