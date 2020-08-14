package com.github.kylichist.tenminutewindow.util

import org.json.JSONObject
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import java.util.Base64
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

internal fun String.between(from: String, to: String) =
    with(this) { substring(indexFrom(from), indexOf(to)) }

internal fun String.indexFrom(from: String) = indexOf(from) + from.length
internal fun String.substringFrom(from: String) = with(this) { substring(indexFrom(from)) }
internal fun String.drop() = dropLast(1)
internal fun String.decode() = String(Base64.getDecoder().decode(this))
internal fun String.replace() = replace("\\", "")
internal fun String.toJSON() = JSONObject(this)

internal fun Long.atLeast() = if (this < MINIMUM_AUTO_REFRESH_PERIOD_VALUE) MINIMUM_AUTO_REFRESH_PERIOD_VALUE else this

internal fun Elements.selectAuthor() = select(AUTHOR_INFO_QUERY)
    .select(AUTHOR_INFO_START)

internal fun Elements.selectDate() = select(DATE_QUERY)
    .attr(DATE_ATTR)

internal fun Elements.selectAttachments() = select(ATTACHMENTS_QUERY)
    .select(ATTACHMENTS_ATTR)

internal fun Document.selectMessages() = select(MESSAGES_TABLE)
    .select(MESSAGES_FIRST_QUERY)
    .select(MESSAGES_SECOND_QUERY)

internal fun ScheduledExecutorService.scheduleEvery(period: Long, work: () -> Unit) =
    this.scheduleAtFixedRate({ work() }, 0, period, TimeUnit.MILLISECONDS)