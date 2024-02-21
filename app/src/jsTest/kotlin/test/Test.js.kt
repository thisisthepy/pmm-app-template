package test

import device.ui.Greeting
import kotlin.test.Test
import kotlin.test.assertTrue

class JsGreetingTest {

    @Test
    fun testExample() {
        assertTrue(Greeting().greet().contains("Web/JS"), "Check JS is mentioned")
    }
}
