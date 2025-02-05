package mail

import (
	"fmt"
	"net/smtp"
	"notification-service/config"
	"notification-service/message"
)

func SendEmail(message message.EmailMessage) error {
	config, err := config.LoadConfig()
	if err != nil {
		return err
	}
	auth := smtp.PlainAuth("", config.Username, config.Password, config.Host)
	addr := fmt.Sprintf("%s:%s", config.Host, config.Port)

	emailBody := message.BuildMessage(config.From)

	err = smtp.SendMail(addr, auth, config.From, []string{message.To}, []byte(emailBody))
	if err != nil {
		return fmt.Errorf("E-posta gönderme basarisiz: %v", err)
	}
	fmt.Println("E-posta basarıyla gönderildi:", message.To)
	return nil
}
