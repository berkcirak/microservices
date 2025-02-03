package com.example.product.service

import com.example.product.model.Product
import com.example.product.repository.ProductRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.IOException

@Service
class ProductService(
    private val productRepository: ProductRepository,
    private val fileStorageService: FileStorageService) {

    fun getAllProducts(pageNumber: Int, pageSize: Int): Page<Product> {
        val pageRequest = PageRequest.of(pageNumber, pageSize)
        return productRepository.findAll(pageRequest)
    }


    fun saveProduct(product: Product, imageFile: MultipartFile): Product {
        val savedProduct = productRepository.save(product) //ürünü veritabanına kaydet
        if (imageFile != null) {
            val photoUrl = fileStorageService.storeFile(savedProduct.id, imageFile)
            savedProduct.photoUrl = photoUrl
            return productRepository.save(savedProduct)
        }
        return savedProduct
    }
    fun getProductById(productId: Long): Product{
        return productRepository.findById(productId)
            .orElseThrow { RuntimeException("Product could not found")}
    }
    fun getAllProducts(): List<Product>{
        return productRepository.findAll()
    }
    fun getProductImage(productId: Long): ByteArray{
        val product = getProductById(productId)
        return fileStorageService.loadFile(product.photoUrl ?: throw RuntimeException("Image could not found"))
    }
    fun deleteProduct(productId: Long){
        productRepository.deleteById(productId)
    }
}