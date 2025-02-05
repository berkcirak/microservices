package message

import "fmt"

type EmailMessage struct {
	To      string
	Subject string
	Body    string
}

func (m *EmailMessage) BuildMessage(from string) string {
	return fmt.Sprintf("From: %s\r\nTo: %s\r\nSubject: %s\r\n\r\n%s",
		from, m.To, m.Subject, m.Body)
}
