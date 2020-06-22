/*
 * Copyright (C) 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.codelabs.navigation

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.os.Bundle
import android.widget.RemoteViews
import androidx.navigation.NavDeepLinkBuilder

/**
 * App Widget that deep links you to the [DeepLinkFragment].
 */
class DeepLinkAppWidgetProvider : AppWidgetProvider() {
    /**
     * Called in response to the [AppWidgetManager.ACTION_APPWIDGET_UPDATE] and
     * [AppWidgetManager.ACTION_APPWIDGET_RESTORED] broadcasts when this AppWidget Provider
     * is being asked to provide [RemoteViews] for a set of AppWidgets.
     *
     * @param context [Context] in which this receiver is running.
     * @param appWidgetManager [AppWidgetManager] instance to call [AppWidgetManager.updateAppWidget] on.
     * @param appWidgetIds The appWidgetIds for which an update is needed.  Note that this
     * may be all of the AppWidget instances for this provider, or just a subset of them.
     */
    override fun onUpdate(
            context: Context,
            appWidgetManager: AppWidgetManager,
            appWidgetIds: IntArray
    ) {
        // Construct the RemoteViews
        val remoteViews = RemoteViews(
                context.packageName,
                R.layout.deep_link_appwidget
        )

        // Construct the Arguments for the Deep Linked destination
        val args = Bundle()
        args.putString("myarg", "From Widget")

        // Completed STEP 10 - construct and set a PendingIntent using DeepLinkBuilder
        // Construct the Pending Intent to launch the Deep Linked destination
        val pendingIntent = NavDeepLinkBuilder(context)
                .setGraph(R.navigation.mobile_navigation)
                .setDestination(R.id.deeplink_dest)
                .setArguments(args)
                .createPendingIntent()

        // Register a click handler on the App Widget's Button
        // to launch the Deep Linked destination with the given Pending Intent
        remoteViews.setOnClickPendingIntent(R.id.deep_link_button, pendingIntent)

        // Instruct the Widget manager to update the App Widgets
        appWidgetManager.updateAppWidget(appWidgetIds, remoteViews)
    }
}
