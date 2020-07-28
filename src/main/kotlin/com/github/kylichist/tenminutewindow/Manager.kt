package com.github.kylichist.tenminutewindow

import com.github.kylichist.tenminutewindow.data.*
import com.github.kylichist.tenminutewindow.util.*
import org.jsoup.Jsoup

class Manager(var autoRefresh: Boolean = true,
              var autoExtend: Boolean = true,
              var refreshPeriod: Long = 15000) {

    private val cookies = initCookies()
    var mailbox = refreshMailboxInfo()

    fun refreshMailboxInfo(): Mailbox {
        val body = Jsoup.connect(MAILBOX_INFO)
            .cookies(cookies)
            .get()
            .body()
            .toString()
        val time = body.between("var today = new Date();var tt = ",
            ";var time = today.getTime();").toLong() * 1000
        val json = body.between("var permalink = '",
            "';if(localStorgeSupportCheck()")
            .decode()
            .replace()
            .toJSON()
        val email = json.getString("mail")
        val host = json.getString("host")
        val link = json.getString("url")
        val key = json.getString("key")
        return Mailbox(email, time, host, link, key).also { mailbox = it }
    }
    private fun initCookies():
            Map<String, String> = Jsoup.connect(MAILBOX_CREATE)
        .execute()
        .cookies()
}