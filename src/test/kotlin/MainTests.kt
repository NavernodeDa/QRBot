@file:Suppress("ktlint:standard:no-wildcard-imports")

package dev.kumchatka

import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertTrue

class MainTests {
    @Test
    fun `test create QR code`() {
        val text = "Hello, QR Code!"
        val qrFile = File("./src/test/resources/qr-code.png")

        val resultFile = createQrCode(text, qrFile)

        assertTrue(resultFile.exists(), "QR code file should exist after creation")
        assertTrue(resultFile.extension == "png", "QR code file should be a PNG image")

        // TODO: decode qr code
    }
}
