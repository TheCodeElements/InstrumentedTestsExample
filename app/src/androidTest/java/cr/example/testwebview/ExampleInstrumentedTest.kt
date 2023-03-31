package cr.example.testwebview

import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.web.assertion.WebViewAssertions.webContent
import androidx.test.espresso.web.assertion.WebViewAssertions.webMatches
import androidx.test.espresso.web.matcher.DomMatchers
import androidx.test.espresso.web.model.Atoms.getCurrentUrl
import androidx.test.espresso.web.sugar.Web.onWebView
import androidx.test.platform.app.InstrumentationRegistry
import junit.framework.TestCase.assertEquals
import org.hamcrest.CoreMatchers.containsString
import org.junit.After
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runners.MethodSorters

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class ExampleInstrumentedTest {

    private lateinit var scenario: ActivityScenario<MainActivity>

    private fun startActivity_MainActivity() {
        val intent = Intent(ApplicationProvider.getApplicationContext(), MainActivity::class.java)
        scenario = ActivityScenario.launch(intent)
    }

    @After
    fun cleanup() {
        scenario.close()
    }

    @Before
    fun init() {
        startActivity_MainActivity()
    }

    @Test
    fun useAppContext() {

        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("cr.example.testwebview", appContext.packageName)
    }

    @Test
    fun check_if_google_de_is_loaded_at_start() {

        // startUrl = currenturl = "https:\\www.google.dex" // not existing
        onWebView()
            .forceJavascriptEnabled()
            .check(webMatches(getCurrentUrl(), containsString("www.google.de")))
            .reset()
    }


    @Test
    fun check_if_google_de_is_loaded_after_button_click() {

        // text in url 'eintippen'
        onView(withId(R.id.url))
            .perform(clearText(), typeText("https:\\www.google.de"))

        // button 'Load' drücken
        onView(withId(R.id.button_load))
            .perform(click())

        // gucken, was passiert
        onWebView()
            .forceJavascriptEnabled()
            .check(webMatches(getCurrentUrl(), containsString("www.google.de")))
            .reset()
    }

    @Test
    fun show_imprint_from_asset() {

        // button 'Load' drücken
        onView(withId(R.id.button_load_asset_html))
            .perform(click())

        // gucken, was passiert
        onWebView()
            .forceJavascriptEnabled()
            .check(webContent(DomMatchers.containingTextInBody("App Version")))
            .reset()
    }
}

