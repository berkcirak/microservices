# 1️⃣ Küçük boyutlu ve hafif bir Golang imajı kullan
FROM golang:1.23.2-alpine

# 2️⃣ Çalışma dizinini belirle (notification-service içinde çalışacak)
WORKDIR /app

# 3️⃣ Go mod ve sum dosyalarını kopyala ve bağımlılıkları yükle
COPY go.mod go.sum ./
RUN go mod tidy

# 4️⃣ Tüm proje dosyalarını container içine kopyala
COPY . .

# 5️⃣ Projeyi derle (binary adı "notification-service" olacak)
RUN go build -o notification-service

# 6️⃣ Container başlatıldığında çalıştırılacak komut
CMD ["/app/notification-service"]
