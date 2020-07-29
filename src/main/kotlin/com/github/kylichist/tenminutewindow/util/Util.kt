package com.github.kylichist.tenminutewindow.util

import org.apache.commons.lang3.StringUtils
import org.json.JSONObject
import java.util.Base64

internal fun String.between(from: String, to: String): String = StringUtils.substringBetween(this, from, to)
internal fun String.substringFrom(from: String): String = this.substring(this.indexOf(from) + from.length)
internal fun String.substringTo(to: String) = this.substring(0, this.indexOf(to))
internal fun String.cut(what: String): String = StringUtils.removeEnd(this, what)
internal fun String.decode(): String = String(Base64.getDecoder().decode(this))
internal fun String.replace(): String = this.replace("\\", "")
internal fun String.toJSON(): JSONObject = JSONObject(this)