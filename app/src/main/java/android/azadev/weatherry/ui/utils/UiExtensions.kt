package android.azadev.weatherry.ui.utils

import android.view.View

/**
 * Created by : Azamat Kalmurzayev
 * Date : 3/17/2024
 */

object UiExtensions {

    fun View.visible() {
        visibility = View.VISIBLE
    }

    fun View.inVisible() {
        visibility = View.INVISIBLE
    }
}