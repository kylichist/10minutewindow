@file:Suppress("unused")

package com.github.kylichist.tenminutewindow

import com.github.kylichist.tenminutewindow.data.Message
import com.github.kylichist.tenminutewindow.util.DEFAULT_AUTO_REFRESH_VALUE
import com.github.kylichist.tenminutewindow.util.MINIMUM_AUTO_REFRESH_PERIOD_VALUE

object Managers {
    fun single(
        autoRefresh: Boolean = DEFAULT_AUTO_REFRESH_VALUE,
        refreshPeriod: Long = MINIMUM_AUTO_REFRESH_PERIOD_VALUE,
        onFirstUpdate: (Message) -> Unit
    ) = Manager.apply {
        this.autoRefresh = autoRefresh
        this.refreshPeriod = refreshPeriod
        this.onFirstUpdate = onFirstUpdate
    }.build()

    fun common(
        autoRefresh: Boolean = DEFAULT_AUTO_REFRESH_VALUE,
        refreshPeriod: Long = MINIMUM_AUTO_REFRESH_PERIOD_VALUE,
        onUpdate: (Message) -> Unit
    ) = Manager.apply {
        this.autoRefresh = autoRefresh
        this.refreshPeriod = refreshPeriod
        this.onUpdate = onUpdate
    }.build()
}