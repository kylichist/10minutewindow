package com.github.kylichist.tenminutewindow

import com.github.kylichist.tenminutewindow.data.*
import com.github.kylichist.tenminutewindow.util.*
import org.jsoup.Jsoup

class Manager(
    var onNext: (Message) -> Unit,
    var onFirst: (Message) -> Unit,
    var autoRefresh: Boolean,
    var autoExtend: Boolean,
    var refreshPeriod: Long
) {
    private val cookies = initCookies()
    var mailbox = initMailbox()

    fun refreshMailboxState(): Mailbox {
        val currentMailbox = initMailbox()
        val messages = mailbox.messages
        val elements = Jsoup.connect(MESSAGES)
            .cookies(cookies)
            .get()
            .select("tbody")
            .select("tr:gt(1)")
        for (element in elements) {
            val mid = element.attr("onclick")
                .substringFrom("?mid=")
                .cut("'")
            val document = Jsoup.connect(MESSAGE_INFO + mid)
                .cookies(cookies)
                .get()
                .body()
            val mailHeader = document.select("div.mail_header")
            val subject = mailHeader.select("h2.emoji_parse")
                .text()
            val mailInfo = mailHeader.select("div.mail_headerinfo")
            val fromInfo = mailInfo.select("span.mail_from")
                .text()
            val name = fromInfo.substringTo(" (")
            val from = fromInfo.substringFrom(" (")
                .cut(")")
            val date = mailHeader.select("[title]")
                .attr("title")
            val text = document.select("div.mailinhtml")
                .html()
            //parse attachments
            val attachments = mailHeader.select("div.mail_att")
                .select("a")
            val message = Message(from, subject, name, date, text, mid)
            if (attachments.isNotEmpty()) {
                val attachmentsList = mutableListOf<Attachment>()
                for (attachment in attachments) {
                    val link = BASE + attachment.attr("href")
                    val title = attachment.attr("title")
                    attachmentsList.add(Attachment(link, title))
                }
                message.attachments = attachmentsList
            }
            if (message !in messages) {
                onNext(message)
                if (messages.isEmpty()) onFirst(message)
                currentMailbox.messages.add(message)
            }
        }
        return currentMailbox.also { mailbox = it }
    }

    private fun initMailbox(): Mailbox {
        val body = Jsoup.connect(MAILBOX_INFO)
            .cookies(cookies)
            .get()
            .body()
            .toString()
        val json = body.between(
            "var permalink = '",
            "';if(localStorgeSupportCheck()"
        )
            .decode()
            .replace()
            .toJSON()
        val time = body.between(
            "var today = new Date();var tt = ",
            ";var time = today.getTime();"
        ).toLong() * 1000
        val email = json.getString("mail")
        val host = json.getString("host")
        val link = json.getString("url")
        val key = json.getString("key")
        return Mailbox(email, time, host, link, key)
    }

    private fun initCookies():
            Map<String, String> = Jsoup.connect(MAILBOX_CREATE)
        .execute()
        .cookies()

    companion object {
        fun single(
            onFirst: (Message) -> Unit,
            autoRefresh: Boolean = DEFAULT_AUTO_REFRESH_VALUE,
            autoExtend: Boolean = DEFAULT_AUTO_EXTEND_VALUE,
            refreshPeriod: Long = DEFAULT_REFRESH_PERIOD_VALUE
        ):
                Manager = Manager(
            onNext = {}, onFirst = onFirst,
            autoRefresh = autoRefresh, autoExtend = autoExtend,
            refreshPeriod = refreshPeriod
        )

        fun common(
            onNext: (Message) -> Unit,
            autoRefresh: Boolean = DEFAULT_AUTO_REFRESH_VALUE,
            autoExtend: Boolean = DEFAULT_AUTO_EXTEND_VALUE,
            refreshPeriod: Long = DEFAULT_REFRESH_PERIOD_VALUE
        ):
                Manager = Manager(
            onNext = onNext, onFirst = {},
            autoRefresh = autoRefresh, autoExtend = autoExtend,
            refreshPeriod = refreshPeriod
        )
    }
}