@file:Suppress("ktlint:standard:no-consecutive-blank-lines")

package dev.kumchatka

import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.bot
import com.github.kotlintelegrambot.dispatch
import com.github.kotlintelegrambot.dispatcher.command
import com.github.kotlintelegrambot.dispatcher.message
import com.github.kotlintelegrambot.entities.ChatId
import com.github.kotlintelegrambot.entities.Message
import com.github.kotlintelegrambot.entities.TelegramFile
import com.github.kotlintelegrambot.extensions.filters.Filter
import com.github.kotlintelegrambot.logging.LogLevel
import com.natpryce.konfig.ConfigurationProperties
import com.natpryce.konfig.Key
import com.natpryce.konfig.stringType
import io.nayuki.qrcodegen.QrCode
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

val config: ConfigurationProperties = ConfigurationProperties.fromResource("config.properties")

val logger: Logger = LoggerFactory.getLogger("QRBot")

fun main() {
    val bot =
        bot {
            token = config[Key("tokenBot", stringType)]
            logLevel = LogLevel.Error

            dispatch {
                command("start") {
                    sendMessage(bot, message, "Hi, ${this.message.from!!.firstName}")
                }

                command("info") {
                    val me = bot.getMe().get()
                    val text =
                        """
                        Name: ${me.firstName} (@${me.username})
                        Version: v${config[Key("version", stringType)]}
                        Made by ${config[Key("creator", stringType)]}
                        """.trimIndent()
                    sendMessage(
                        bot,
                        message,
                        text,
                        createQrCode(text, File(config[Key("qrInfoPath", stringType)])),
                        text,
                    )
                }

                command("help") {
                    bot.sendMessage(
                        ChatId.fromId(message.chat.id),
                        "This bot is designed to generate QR codes easily. " +
                            "All you have to do is just message me or repay me in general chat for me to send the QR " +
                            "code of your post!",
                        replyToMessageId = message.messageId,
                    )
                }

                message(Filter.Text and (Filter.Private or (Filter.Group and Filter.Reply))) {
                    sendMessage(bot, message, message.text ?: "Not text :(")
                }
            }
        }

    logger.info("Bot is started")
    bot.startPolling()
}

fun createQrCode(
    text: String,
    qrPhoto: File,
): File {
    val qr = QrCode.encodeText(text, QrCode.Ecc.MEDIUM)
    toImage(qr).also { ImageIO.write(it, "png", qrPhoto) }

    return qrPhoto
}

@Suppress("ktlint:standard:annotation-spacing", "ktlint:standard:trailing-comma-on-declaration-site")
// Source: https://github.com/nayuki/QR-Code-generator/blob/master/java/QrCodeGeneratorDemo.java#L172
fun toImage(
    qr: QrCode,
    scale: Int = 4,
    border: Int = 3,
    lightColor: Int = 0xFFFFFF,
    darkColor: Int = 0x000000
): BufferedImage {
    require(!(scale <= 0 || border < 0)) { "Value out of range" }
    require(!(border > Int.MAX_VALUE / 2 || qr.size + border * 2L > Int.MAX_VALUE / scale)) { "Scale or border too large" }

    val result =
        BufferedImage(
            (qr.size + border * 2) * scale,
            (qr.size + border * 2) * scale,
            BufferedImage.TYPE_INT_RGB,
        )
    for (y in 0 until result.height) {
        for (x in 0 until result.width) {
            val color = qr.getModule(x / scale - border, y / scale - border)
            result.setRGB(x, y, if (color) darkColor else lightColor)
        }
    }
    return result
}

@Suppress("ktlint:standard:trailing-comma-on-declaration-site")
fun sendMessage(
    bot: Bot,
    message: Message,
    text: String,
    qrCode: File = File(config[Key("qrPath", stringType)]),
    caption: String? = null
) {
    try {
        bot.sendPhoto(
            ChatId.fromId(message.chat.id),
            TelegramFile.ByFile(createQrCode(text, qrCode)),
            replyToMessageId = message.messageId,
            caption = caption,
        )
    } catch (e: Exception) {
        logger.error("Failed to send message: ${e.message}", e)
        bot.sendMessage(
            ChatId.fromId(message.chat.id),
            "An error occurred \uD83D\uDE22:\n\n${e.message}",
        )
    }
}
