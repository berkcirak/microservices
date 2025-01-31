package routes

import (
	"customer-service/db"
	"customer-service/model"
	_ "customer-service/model"
	"github.com/gin-gonic/gin"
	"net/http"
)

func CreateCustomer(c *gin.Context) {
	var customer model.Customer
	if err := c.ShouldBindJSON(&customer); err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}
	if err := db.DB.Create(&customer).Error; err != nil {
		c.JSON(http.StatusInternalServerError, gin.H{"error": "Customer could not found"})
		return
	}
	c.JSON(http.StatusCreated, customer)
}
func GetCustomerByID(c *gin.Context) {
	var customer model.Customer
	id := c.Param("customerId")
	if err := db.DB.First(&customer, id).Error; err != nil {
		c.JSON(http.StatusNotFound, gin.H{"error": "Customer could not found"})
		return
	}
	c.JSON(http.StatusOK, customer)
}
func UpdateCustomer(c *gin.Context) {
	id := c.Param("customerId")
	var customer model.Customer
	if err := db.DB.First(&customer, id).Error; err != nil {
		c.JSON(http.StatusNotFound, gin.H{"error": "Customer could not found"})
		return
	}
	var updateData = model.Customer{}
	if err := c.ShouldBindJSON(&updateData); err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}
	db.DB.Model(&customer).Updates(updateData)
	c.JSON(http.StatusOK, customer)
}
func DeleteCustomer(c *gin.Context) {
	id := c.Param("customerId")
	if err := db.DB.Delete(&model.Customer{}, id).Error; err != nil {
		c.JSON(http.StatusNotFound, gin.H{"error": "Customer could not found"})
		return
	}
	c.JSON(http.StatusNoContent, nil)
}
