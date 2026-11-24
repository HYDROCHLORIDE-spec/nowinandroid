/*
 * Copyright 2026 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.samples.apps.nowinandroid.feature.topic.impl

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.google.samples.apps.nowinandroid.core.designsystem.component.NiaBackground
import com.google.samples.apps.nowinandroid.core.designsystem.theme.NiaTheme
import com.google.samples.apps.nowinandroid.core.testing.util.DefaultTestDevices
import com.google.samples.apps.nowinandroid.core.testing.util.captureForDevice
import com.google.samples.apps.nowinandroid.core.testing.util.captureMultiDevice
import com.google.samples.apps.nowinandroid.core.ui.UserNewsResourcePreviewParameterProvider
import dagger.hilt.android.testing.HiltTestApplication
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode
import org.robolectric.annotation.LooperMode
import java.util.TimeZone

/**
 * Screenshot tests for the [TopicScreen].
 */
@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(application = HiltTestApplication::class)
@LooperMode(LooperMode.Mode.PAUSED)
class TopicScreenScreenshotTests {

    /**
     * Use a test activity to set the content on.
     */
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val userNewsResources = UserNewsResourcePreviewParameterProvider().values.first()

    @Before
    fun setTimeZone() {
        // Make time zone deterministic in tests
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"))
    }

    @Test
    fun topicScreenPopulated() {
        composeTestRule.captureMultiDevice("TopicScreenPopulated") {
            TopicScreenPopulated()
        }
    }

    @Test
    fun topicScreenPopulated_dark() {
        composeTestRule.captureForDevice(
            deviceName = "phone_dark",
            deviceSpec = DefaultTestDevices.PHONE.spec,
            screenshotName = "TopicScreenPopulated",
            darkMode = true,
        ) {
            TopicScreenPopulated()
        }
    }

    @Test
    fun topicScreenLoading() {
        composeTestRule.captureMultiDevice("TopicScreenLoading") {
            TopicScreenLoading()
        }
    }

    @Test
    fun topicScreenLoading_dark() {
        composeTestRule.captureForDevice(
            deviceName = "phone_dark",
            deviceSpec = DefaultTestDevices.PHONE.spec,
            screenshotName = "TopicScreenLoading",
            darkMode = true,
        ) {
            TopicScreenLoading()
        }
    }

    @Composable
    private fun TopicScreenPopulated() {
        ThemedTopicScreen(
            topicUiState = TopicUiState.Success(userNewsResources[0].followableTopics[0]),
            newsUiState = NewsUiState.Success(userNewsResources),
        )
    }

    @Composable
    private fun TopicScreenLoading() {
        ThemedTopicScreen(
            topicUiState = TopicUiState.Loading,
            newsUiState = NewsUiState.Loading,
        )
    }

    @Composable
    private fun ThemedTopicScreen(
        topicUiState: TopicUiState,
        newsUiState: NewsUiState,
    ) {
        NiaTheme {
            NiaBackground {
                TopicScreen(
                    topicUiState = topicUiState,
                    newsUiState = newsUiState,
                    showBackButton = true,
                    onBackClick = {},
                    onFollowClick = {},
                    onTopicClick = {},
                    onBookmarkChanged = { _, _ -> },
                    onNewsResourceViewed = {},
                )
            }
        }
    }
}
