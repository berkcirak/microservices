package com.example.product.controller

import com.example.product.model.Product
import com.example.product.service.ProductService

import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@RestController
@RequestMapping("/products")
class ProductController (private val productService: ProductService){

    private val objectMapper = ObjectMapper()

    @PostMapping(consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun createProduct(
        @RequestPart("product") productJson: String,
        @RequestPart("image") imageFile: MultipartFile
    ): ResponseEntity<Product> {
        val product = objectMapper.readValue(productJson, Product::class.java)
        val savedProduct = productService.saveProduct(product, imageFile)
        return ResponseEntity(savedProduct, HttpStatus.CREATED)
    }
    @GetMapping("/{productId}")
    fun getProduct(@PathVariable productId: Long): ResponseEntity<Product>{
        val product = productService.getProductById(productId)
        return ResponseEntity.ok(product)
    }
    @GetMapping
    fun getAllProducts(): ResponseEntity<List<Product>>{
        return ResponseEntity.ok(productService.getAllProducts())
    }
    @GetMapping("/{productId}/image", produces = [MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE])
    fun getProductImage(@PathVariable productId: Long):ResponseEntity<ByteArray>{
        val imageData = productService.getProductImage(productId)
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageData)
    }
    @DeleteMapping("/{productId}")
    fun deleteProduct(@PathVariable productId: Long): ResponseEntity<Void>{
        productService.deleteProduct(productId)
        return ResponseEntity.noContent().build()
    }

}