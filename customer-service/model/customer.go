package model

type Customer struct {
	ID      uint   `json:"id" gorm:"primaryKey"`
	Name    string `json:"name"`
	Email   string `json:"email" gorm:"unique"`
	Address string `json:"address"`
}
