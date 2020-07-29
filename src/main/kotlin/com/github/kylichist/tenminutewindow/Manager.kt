package com.github.kylichist.tenminutewindow

import com.github.kylichist.tenminutewindow.data.*
import com.github.kylichist.tenminutewindow.util.*
import org.jsoup.Jsoup

class Manager(
    onNew: (Message) -> Unit,
    var autoRefresh: Boolean = true,
    var autoExtend: Boolean = true,
    var refreshPeriod: Long = 15000
) {
    private val cookies = initCookies()
    var mailbox = initMailbox()

    fun refreshMailboxState(): Mailbox {
        val STARTPOINTTEST = 0
        val currentMailbox = initMailbox()
        val messages = currentMailbox.messages
        val elements = Jsoup.connect(MESSAGES)
            .cookies(cookies)
            .get()
            .select("tbody")
            .select("tr:gt($STARTPOINTTEST)")
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
            val message = document.select("div.mailinhtml")
                .html()

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
}