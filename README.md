# QRBot
[![ktlint](https://img.shields.io/badge/code%20style-%E2%9D%A4-FF4081.svg)](https://ktlint.github.io/)

Simple bot for creating QR codes

## Start use bot

### Insert values

The bot is generally ready to use and work. It uses configuration file technologies. You need to insert your values into src/main/resources/config.properties:

```properties
tokenBot=1234:token-Of-YourBot  // (@BotFather to help)
qrPath=./src/main/resources/qr-code.png  // path to default qr code picture
qrInfoPath=./src/main/resources/qr-code-info.jpg  // path to qr code for command "/info"
version=1.0.0  // here version bot
creator=yourname/username/etc
```

## TODO list

- [x] Add "/help" command
- [ ] Create tests
- [ ] Add more QR codes type support
- [ ] Async

## Usage
- ![](https://avatars.githubusercontent.com/u/57418018?s=24) [Kotlin-telegram-bot](https://github.com/python-telegram-bot/python-telegram-bot) for Telegram API.
- ![](https://avatars.githubusercontent.com/u/672172?s=24) [QR-Code-generator](https://github.com/nayuki/QR-Code-generator) for generate QR Codes.
- ![](https://avatars.githubusercontent.com/u/1521407?s=24) [Slf4j](https://github.com/qos-ch/slf4j) for logging.
- ![](https://avatars.githubusercontent.com/u/56219?s=24) [Konfig](https://github.com/npryce/konfig) for work with properties file.

## License
QRBot is under the GNU General Public License v3.0. See the [LICENSE](LICENSE) for more information.
