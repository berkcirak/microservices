package mail

import (
	"fmt"
	"net/smtp"
	"notification-service/config"
	"notification-service/message"
	"time"
)

var lastSentTime time.Time

func SendEmail(message message.EmailMessage) error {

	// Eğer son gönderme üzerinden 5 dakika (300 saniye) geçmediyse, e-posta göndermeyi durdur.
	if time.Since(lastSentTime) < 5*time.Minute {
		fmt.Println("Skipping email: Last email sent recently.")
		return nil
	}

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
