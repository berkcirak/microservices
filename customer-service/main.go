package main

import (
	"customer-service/db"
	"customer-service/routes"
	"github.com/gin-gonic/gin"
	_ "github.com/gin-gonic/gin"
)

func main() {

	// Veritabanını başlat
	db.InitDB()

	r := gin.Default()

	// Route'ları tanımla
	r.POST("/customers", routes.CreateCustomer)
	r.GET("/customers/:customerId", routes.GetCustomerByID)
	r.PUT("/customers/:customerId", routes.UpdateCustomer)
	r.DELETE("/customers/:customerId", routes.DeleteCustomer)

	// Sunucuyu çalıştır
	r.Run("localhost:8080")
}
