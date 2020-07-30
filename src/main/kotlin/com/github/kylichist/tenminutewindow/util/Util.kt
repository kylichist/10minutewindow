package com.github.kylichist.tenminutewindow.util

import org.apache.commons.lang3.StringUtils
import org.json.JSONObject
import java.util.Base64

internal fun String.between(from: String, to: String) = StringUtils.substringBetween(this, from, to)
internal fun String.substringFrom(from: String) = this.substring(this.indexOf(from) + from.length)
internal fun String.substringTo(to: String) = this.substring(0, this.indexOf(to))
internal fun String.cut(what: String) = StringUtils.removeEnd(this, what)
internal fun String.decode() = String(Base64.getDecoder().decode(this))
internal fun String.replace() = this.replace("\\", "")
internal fun String.toJSON() = JSONObject(this)