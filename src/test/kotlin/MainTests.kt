@file:Suppress("ktlint:standard:no-wildcard-imports")

package dev.kumchatka

import com.google.zxing.BinaryBitmap
import com.google.zxing.MultiFormatReader
import com.google.zxing.client.j2se.BufferedImageLuminanceSource
import com.google.zxing.common.HybridBinarizer
import org.junit.jupiter.api.Test
import java.io.File
import java.io.FileInputStream
import javax.imageio.ImageIO
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class MainTests {
    @Test
    fun `test create QR code`() {
        val resultFile = createQrCode("Hello, QR Code!", File("./src/test/resources/qr-code.png"))

        assertTrue(resultFile.exists(), "QR code file should exist after creation")
        assertTrue(resultFile.extension == "png", "QR code file should be a PNG image")
    }

    @Test
    fun `test content QR code`() {
        val filePath = "./src/test/resources/qr-code.png"
        val initialText = "Hello, test!"

        createQrCode(initialText, File(filePath))

        val binaryBitmap =
            BinaryBitmap(
                HybridBinarizer(
                    BufferedImageLuminanceSource(
                        ImageIO.read(FileInputStream(filePath)),
                    ),
                ),
            )

        val resultText = MultiFormatReader().decode(binaryBitmap).text
        assertEquals(initialText, resultText)
    }
}
