package main

import (
	"fmt"
	"notification-service/mail"
	"notification-service/message"
)

func main() {
	message := message.EmailMessage{
		To:      "example2@gmail.com",
		Subject: "test subject",
		Body:    "test body",
	}
	err := mail.SendEmail(message)
	if err != nil {
		fmt.Println("E-posta gönderme basarısız: ", err)
	} else {
		fmt.Println("E-posta basarıyla gönderildi")
	}
}
