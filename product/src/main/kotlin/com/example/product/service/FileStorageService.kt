package com.example.product.service

import com.example.product.repository.ProductRepository
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption
import java.util.UUID

@Service
class FileStorageService (private val productRepository: ProductRepository) {

    private val uploadDir: String = "uploads/" //resimlerin kaydedilecegi klasor

    fun storeFile(productId: Long, imageFile: MultipartFile): String {
        val uploadPath = Path.of(uploadDir)
        if (!Files.exists(uploadPath)){
            Files.createDirectories(uploadPath) //eger klasor yoksa olustur
        }
        //dosya adını olustur(benzersiz olmasi icin UUID ekliyoruz)
        val fileName = UUID.randomUUID().toString() + "-" + imageFile.originalFilename
        val filePath = uploadPath.resolve(fileName)

        try {
            Files.copy(imageFile.inputStream, filePath, StandardCopyOption.REPLACE_EXISTING)
        } catch (e: IOException){
            throw RuntimeException("Error occurred while file upload", e)
        }

        //upload the products photourl
        val product = productRepository.findById(productId).orElseThrow { RuntimeException("Product could not found") }
        product.photoUrl = fileName
        productRepository.save(product)
        return fileName //path to save in database

    }

    fun loadFile(fileName: String): ByteArray{
        val filePath = Path.of(uploadDir, fileName)
        return Files.readAllBytes(filePath)
    }



}