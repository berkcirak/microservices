package db

import (
	"customer-service/model"
	_ "customer-service/model"
	"fmt"
	"gorm.io/driver/mysql"
	"gorm.io/gorm"
	"log"
)

var DB *gorm.DB

func InitDB() {
	dsn := "springstudent:springstudent@tcp(localhost:3306)/customer_service?charset=utf8mb4&parseTime=True&loc=Local"

	var err error
	DB, err = gorm.Open(mysql.Open(dsn), &gorm.Config{})
	if err != nil {
		log.Fatal("could not found database: ", err)
	}
	err = DB.AutoMigrate(&model.Customer{})
	if err != nil {
		log.Fatal("An error occurred while connecting to the database")
	}
	fmt.Println("Successfully connected to database and table created!")

}
