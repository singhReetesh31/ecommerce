package com.axis.ecommerce.controller

import com.amazonaws.auth.AWSCredentials
import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.regions.Regions
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import com.amazonaws.services.s3.model.CannedAccessControlList
import com.amazonaws.services.s3.model.PutObjectRequest
import com.axis.ecommerce.dto.ERole
import com.axis.ecommerce.dto.ProductDto
import com.axis.ecommerce.entity.Product
import com.axis.ecommerce.exception.AuthorityException
import com.axis.ecommerce.exception.IdNotFoundException
import com.axis.ecommerce.repository.IUserRepository
import com.axis.ecommerce.service.ICategoryService
import com.axis.ecommerce.service.IProductService
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*

@CrossOrigin(origins = ["http://localhost:3000/"])
@RestController
@RequestMapping("/product")
class ProductController {
    var uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/productImages"
    @Autowired
    lateinit var productService:IProductService

    @Autowired
    lateinit var categoryService: ICategoryService

    @Autowired
    lateinit var userRepository: IUserRepository

    private var amazonS3client: AmazonS3? =null

    @Value("\${amazon.s3.bucket.name}")
    private val bucketName: String? = null

    //@Value("\${amazon.aws.access-key}")
    private val accessKey: String=""

   // @Value("\${amazon.aws.secret-key}")
    private val secretKey: String= ""
    //init
    init {
        println("access key:  $accessKey")
        println("secret key:  $secretKey")
        println("bucket $bucketName")
        val credentials: AWSCredentials = BasicAWSCredentials(accessKey, secretKey)
        amazonS3client = AmazonS3ClientBuilder
            .standard()
            .withCredentials(AWSStaticCredentialsProvider(credentials))
            .withRegion(Regions.US_EAST_2)
            .build()
    }
    /*@PostMapping("/add")
    fun addProduct(@RequestParam("image")file:MultipartFile ,
                   @RequestParam("productInfo") productInfo:String):ResponseEntity<Any>{
        var productDto= jacksonObjectMapper().readValue(productInfo,ProductDto::class.java)
        var product=Product()
        product.name=productDto.name
        product.category=categoryService.getById(productDto.categoryId)
        product.price=productDto.price
        product.description=productDto.description
        //check if this seller exist and check it's role
        val user=userRepository.findById(productDto.sellerId).orElseThrow { IdNotFoundException("Seller ID: ${productDto.sellerId} not found") }
        val roles=user.roles
        for (role in roles){
            if (role.name==(ERole.ROLE_SELLER)){
                product.sellerId=productDto.sellerId
            }else{
                throw AuthorityException("You don't have seller authority")
            }
        }

        var imageName:String
        if (!file.isEmpty){
            imageName= file.originalFilename!!
            var fileNameAndPath:Path= Paths.get(uploadDir,imageName) //img path(where to be stored),img name
            Files.write(fileNameAndPath,file.bytes)
        }else{
            imageName="no file found"
        }
        product.imageUrl=imageName
        return ResponseEntity(productService.add(product),HttpStatus.OK)
    }*/
    @PostMapping("/add")
    fun addProduct(
                   @RequestParam("image", required = false) imageFile: MultipartFile,
                   @RequestParam("name") name: String,
                   @RequestParam("price") price:Double,
                   @RequestParam("categoryId") categoryId:String,
                   @RequestParam("description") description:String,
                   @RequestParam("sellerId") sellerId:String
    ):ResponseEntity<Any> {
        println("access key:  $accessKey")
        println("secret key:  $secretKey")
        println("bucket $bucketName")
        var product=Product()
        product.name=name
        product.price=price
        product.description=description
       product.category=categoryService.getById(categoryId)
        //check if this seller exist and check it's role
        val user=userRepository.findById(sellerId).orElseThrow { IdNotFoundException("Seller ID: ${sellerId} not found") }
        val roles=user.roles
        for (role in roles){
            if (role.name==(ERole.ROLE_SELLER)){
                product.sellerId=sellerId
            }else{
                throw AuthorityException("You don't have seller authority")
            }
        }

        val imageFileName = imageFile.originalFilename.toString()
        //println("=======$imageFileName")
        val file = File(imageFileName)
        try {
            val fos=FileOutputStream(file)
            fos.write(imageFile.bytes)
        }catch (e:IOException){
            e.printStackTrace();
        }
        val fileName = UUID.randomUUID().toString() + "." + imageFile.originalFilename
        //println("=======$fileName")
        amazonS3client?.putObject(PutObjectRequest(bucketName, fileName, file).withCannedAcl(CannedAccessControlList.PublicRead))
        val finalImage = amazonS3client?.getUrl(bucketName, fileName).toString()
       // println("===========$finalImage")
        product.imageUrl=finalImage
        return ResponseEntity(productService.add(product),HttpStatus.OK)
    }

    @GetMapping("/get")
    fun getAllProducts():ResponseEntity<Any>{
        return ResponseEntity(productService.getAll(),HttpStatus.OK);
    }
    @GetMapping("/setOrderStatus/{orderId}")
    fun setOrderStatus(@PathVariable orderId:String):ResponseEntity<Any>{
        return ResponseEntity(productService.setOrderStatus(orderId),HttpStatus.OK)
    }
}
