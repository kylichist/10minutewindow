package com.github.kylichist.tenminutewindow

import com.github.kylichist.tenminutewindow.data.*
import com.github.kylichist.tenminutewindow.util.*
import org.jsoup.Jsoup
import java.util.concurrent.Executors

class Manager private constructor(
    autoRefresh: Boolean,
    refreshPeriod: Long,
    val onFirstUpdate: (Message) -> Unit,
    val onUpdate: (Message) -> Unit
) {
    private val cookies = Jsoup.connect(MAILBOX_CREATE)
        .execute()
        .cookies()

    @Suppress("MemberVisibilityCanBePrivate")
    var mailbox = initMailbox()

    init {
        if (autoRefresh) Executors.newSingleThreadScheduledExecutor()
            .scheduleEvery(refreshPeriod.atLeast()) { refreshMailboxState() }
    }

    @Suppress("MemberVisibilityCanBePrivate")
    fun refreshMailboxState(): Mailbox {
        println("refresh")
        val currentMailbox = initMailbox()
        val elements = Jsoup.connect(MESSAGES)
            .cookies(cookies)
            .get()
            .selectMessages()
        //println(elements.toString())
        println(currentMailbox.address)
        println(currentMailbox.host)
        println(currentMailbox.key)
        println(currentMailbox.link)
        println(currentMailbox.millisecondsLeft)

        for (element in elements) {
            val mid = element.attr(MID_ATTR)
                .substringFrom(MID_START)
                .drop()
            with(
                Jsoup.connect(MESSAGE_INFO + mid)
                    .cookies(cookies)
                    .get()
                    .body()
            ) {
                val text = select(TEXT_QUERY)
                    .text()
                with(select(MAIL_HEADER)) {
                    val subject = select(SUBJECT_QUERY)
                        .text()
                    val (name, from) = selectAuthor()
                        .text()
                        .split(AUTHOR_INFO_DELIMITER)
                    val date = selectDate()
                    val attachments = selectAttachments()
                    var attachmentList: AttachmentList? = null
                    if (attachments.isNotEmpty()) {
                        attachmentList = mutableListOf()
                        for (attachment in attachments) with(attachment) {
                            attachmentList.add(
                                Attachment(
                                    BASE + attr(ATT_LINK_ATTR),
                                    attr(ATT_TITLE_ATTR)
                                )
                            )
                        }
                    }
                    val message = Message(
                        from.drop(), subject, name,
                        date, text, mid, attachmentList
                    )
                    with(mailbox) {
                        if (message !in messages) {
                            onUpdate(message)
                            if (isEmpty()) onFirstUpdate(message)
                            currentMailbox.messages.add(message)
                        }
                    }
                }
            }
        }
        return currentMailbox.also { mailbox = it }
    }

    @Suppress("unused")
    fun extend(): Mailbox {
        Jsoup.connect(MAILBOX_EXTEND)
            .cookies(cookies)
            .execute()
        return refreshMailboxState()
    }

    private fun initMailbox(): Mailbox =
        with(
            Jsoup.connect(MAILBOX_INFO)
                .cookies(cookies)
                .get()
                .body()
                .toString()
        ) {
            val time = between(TIME_START, TIME_END)
                .toLong() * 1000
            with(
                between(JSON_START, JSON_END)
                    .decode()
                    .replace()
                    .toJSON()
            ) {
                return Mailbox(
                    getString(MAIL_KEY), time,
                    getString(HOST_KEY),
                    getString(URL_KEY),
                    getString(KEY_KEY)
                )
            }
        }

    companion object Builder {
        var autoRefresh = DEFAULT_AUTO_REFRESH_VALUE
        var refreshPeriod = MINIMUM_AUTO_REFRESH_PERIOD_VALUE
        var onFirstUpdate: (Message) -> Unit = {}
        var onUpdate: (Message) -> Unit = {}

        @Suppress("unused")
        fun build() = Manager(autoRefresh, refreshPeriod, onFirstUpdate, onUpdate)
    }
}