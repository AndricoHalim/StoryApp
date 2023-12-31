package com.andricohalim.storyapp.ui.login

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.andricohalim.storyapp.R
import com.andricohalim.storyapp.retrofit.ApiConfig
import com.andricohalim.storyapp.ui.main.MainActivity
import com.andricohalim.storyapp.util.JsonConverter
import com.andricohalim.storyapp.utils.EspressoIdlingResource
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class LoginActivityTest {
    private val mockWebServer = MockWebServer()

    @get:Rule
    val activity = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setUp() {
        mockWebServer.start(8080)
        ApiConfig.BASE_URL = "http://127.0.0.1:8080/"
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @Test
    fun loginAndLogout() {
        onView(withId(R.id.btnLogin)).perform(ViewActions.click())
        onView(withId(R.id.ed_login_email)).perform(ViewActions.click())
        onView(withId(R.id.ed_login_email)).perform(ViewActions.typeText("test@gmail.com"))

        onView(withId(R.id.ed_login_password)).perform(ViewActions.click())
        onView(withId(R.id.ed_login_password)).perform(ViewActions.typeText("12345678"))


        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(JsonConverter.readStringFromFile("login_response.json"))
        mockWebServer.enqueue(mockResponse)

        onView(withId(R.id.btn_login)).perform(ViewActions.click())
        onView(withText(R.string.lanjut)).perform(ViewActions.click())

        onView(withId(R.id.menu_logout)).perform(ViewActions.click())
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }
}