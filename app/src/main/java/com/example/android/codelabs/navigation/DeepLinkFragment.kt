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

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.app.NotificationCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs

/**
 * Fragment used to show how to deep link to a destination
 */
class DeepLinkFragment : Fragment() {
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.deeplink_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set the argument text by reading its value from Bundle arguments via Safe Args
        val navArgs by navArgs<DeepLinkFragmentArgs>()
        view.findViewById<TextView>(R.id.text).text = navArgs.myarg

        // Set Click listener on "Send Deep Link Notification" Button to create
        // and show a Notification for this Deep linked destination
        val notificationButton = view.findViewById<Button>(R.id.send_notification_button)
        notificationButton.setOnClickListener {
            // Read the value from edit text field and save it as Bundle arguments for the Intent
            val editArgs = view.findViewById<EditText>(R.id.args_edit_text)
            val args = Bundle()
            args.putString("myarg", editArgs.text.toString())

            // Construct a Pending Intent to launch this Deep linked destination from a Notification
            val deepLink = findNavController().createDeepLink()
                    .setDestination(R.id.deeplink_dest)
                    .setArguments(args) // Pass the argument bundle built
                    .createPendingIntent()

            // Retrieve the instance of NotificationManager to notify the user of the events
            val notificationManager =
                    context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                // Build a Notification Channel for devices with API level 26+
                // and register it with the NotificationManager
                notificationManager.createNotificationChannel(NotificationChannel(
                        DEEP_LINK_CHANNEL_ID,
                        requireContext().getString(R.string.deep_link_notification_channel_name),
                        NotificationManager.IMPORTANCE_HIGH
                ))
            }

            // Construct the Notification Content belonging to "Deep Links" Notification channel
            val builder = NotificationCompat.Builder(requireContext(), DEEP_LINK_CHANNEL_ID)
                    // Title of the Notification
                    .setContentTitle(requireContext().getString(R.string.title_deep_link_notification_content))
                    // Content text of the Notification
                    .setContentText(requireContext().getString(R.string.text_deep_link_notification_content))
                    // The required small icon
                    .setSmallIcon(R.drawable.ic_android)
                    // Pending Intent for the Notification which is Deep linked to this destination
                    .setContentIntent(deepLink)
                    // Automatically cancel/clear the Notification when clicked
                    .setAutoCancel(true)

            // Post the Notification with the Notification content built
            notificationManager.notify(
                    // Unique identifier for this notification within the app
                    DEEP_LINK_NOTIFICATION_ID,
                    // Build and show this notification
                    builder.build()
            )
        }
    }

    companion object {
        // Constant for the Notification Channel ID
        const val DEEP_LINK_CHANNEL_ID = "deepLink"

        // Constant for the Notification built for "Deep Links" Notification channel
        const val DEEP_LINK_NOTIFICATION_ID = 0
    }
}
