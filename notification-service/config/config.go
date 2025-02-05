package config

import (
	"fmt"
	"github.com/joho/godotenv"
	"os"
)

type SMTPConfig struct {
	Host     string
	Port     string
	Username string
	Password string
	From     string
}

func LoadConfig() (*SMTPConfig, error) {
	err := godotenv.Load()
	if err != nil {
		fmt.Println("Warning: No .env file found, using environment variables")
	}
	config := &SMTPConfig{
		Host:     os.Getenv("SMTP_HOST"),
		Port:     os.Getenv("SMTP_PORT"),
		Username: os.Getenv("SMTP_USERNAME"),
		Password: os.Getenv("SMTP_PASSWORD"),
		From:     os.Getenv("SMTP_FROM"),
	}
	if config.Host == "" || config.Port == "" || config.Username == "" || config.Password == "" || config.From == "" {
		return nil, fmt.Errorf("check the .env file")
	}
	return config, nil
}
