package com.inventorymanagement.serviceimpl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inventorymanagement.entity.Product;
import com.inventorymanagement.exception.AlreadyExistsException;
import com.inventorymanagement.exception.ResourceNotFoundException;
import com.inventorymanagement.repository.ProductRepository;
import com.inventorymanagement.service.ProductService;
import com.inventorymanagement.repository.CategoryRepository;
import com.inventorymanagement.repository.SupplierRepository;
import com.inventorymanagement.dto.ProductDTO;
import com.inventorymanagement.dto.CreateProductDTO;
import com.inventorymanagement.entity.Category;
import com.inventorymanagement.entity.Supplier;
import com.inventorymanagement.dto.CreateNotificationDTO;
import com.inventorymanagement.entity.User;
import com.inventorymanagement.repository.UserRepository;
import com.inventorymanagement.service.NotificationService;
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;
   

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private NotificationService notificationService;

    @Autowired
    private UserRepository userRepository;
   
    private ProductDTO convertToDTO(Product product) {

        ProductDTO dto = new ProductDTO();

        dto.setProductId(product.getProductId());
        dto.setProductName(product.getProductName());
        dto.setCategoryName(product.getCategory().getCategoryName());
        dto.setQuantity(product.getQuantity());
        dto.setPrice(product.getPrice());
       
        return dto;
    }
    private Product convertToEntity(
            CreateProductDTO dto) {

        Product product = new Product();

        product.setProductName(
                dto.getProductName());

        product.setQuantity(
                dto.getQuantity());

        product.setPrice(
                dto.getPrice());

        Category category =
                categoryRepository.findById(
                        dto.getCategoryId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Category Not Found"));

       
        product.setCategory(category);
       
        return product;
    }
    private User getAdminUser() {

        return userRepository.findByRole("ADMIN")
                .stream()
                .findFirst()
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Admin Not Found"));
    }
    private void checkLowStock(Product product) {

        if (product.getQuantity() <= 10) {

            User admin = getAdminUser();

            CreateNotificationDTO notification =
                    new CreateNotificationDTO();

            notification.setUserId(
                    admin.getUserId());

            notification.setTitle(
                    "LOW STOCK ALERT");

            notification.setMessage(
                    product.getProductName()
                    + " stock is low. Remaining quantity: "
                    + product.getQuantity());

            notification.setType(
                    "LOW_STOCK");

            notificationService
                    .createNotification(
                            notification);
        }
    }
    
    // Save Product
    @Override
    public ProductDTO saveProduct(
            CreateProductDTO dto) {

        Product product =
                convertToEntity(dto);

        boolean exists =
        		productRepository
        		.existsByProductNameAndCategory_CategoryId(

        		        product.getProductName(),

        		        product.getCategory()
        		               .getCategoryId());
        if(exists) {

            throw new AlreadyExistsException(
                    "Product already exists");
        }

        Product savedProduct =
                productRepository.save(
                        product);
        checkLowStock(savedProduct);

        return convertToDTO(
                savedProduct);
    }

    // Get All Products
    @Override
    public List<ProductDTO>
    getAllProducts() {

        return productRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    // Get Product By ID
    @Override
    public ProductDTO getProductById(
            Integer id) {

        Product product =
                productRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Product Not Found"));

        return convertToDTO(product);
    }
    // Update Product
    @Override
    public ProductDTO updateProduct(
            Integer id,
            CreateProductDTO dto) {

        Product existingProduct =
                productRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Product Not Found With ID : "
                                        + id));

        Category category =
                categoryRepository.findById(
                        dto.getCategoryId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Category Not Found"));

        
        existingProduct.setProductName(
                dto.getProductName());

        existingProduct.setQuantity(
                dto.getQuantity());

        existingProduct.setPrice(
                dto.getPrice());

        existingProduct.setCategory(
                category);

      
        Product updatedProduct =
                productRepository.save(
                        existingProduct);
        checkLowStock(updatedProduct);

        return convertToDTO(
                updatedProduct);
    }

    // Delete Product
    @Override
    public void deleteProduct(Integer id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product Not Found"));

        productRepository.delete(product);
    }
    // Delete All Products
    @Override
    public void deleteAllProducts() {
        productRepository.deleteAll();
    }

    // Filter By Supplier ID (FIXED)

    // Search By Product Name
    @Override
    public List<ProductDTO> searchProductsByName(String productName) {
        return productRepository
                .findByProductNameContainingIgnoreCase(productName)
                .stream()
                .map(this::convertToDTO)
                .toList();
    }
    // Search By Category (FIXED for entity relationship)
    @Override
    public List<ProductDTO>
    searchProductsByCategory(
            String category) {

        return productRepository
                .findByCategory_CategoryNameContainingIgnoreCase(
                        category)
                .stream()
                .map(this::convertToDTO)
                .toList();
    }
    // Search By Supplier Name
 
    // Filter By Exact Category
    @Override
    public List<ProductDTO>
    getProductsByCategory(
            String category) {

        return productRepository
                .findByCategory_CategoryNameContainingIgnoreCase(
                        category)
                .stream()
                .map(this::convertToDTO)
                .toList();
    }
    // Filter By Price
    @Override
    public List<ProductDTO>
    getProductsByPrice(
            BigDecimal price) {

        return productRepository
                .findByPriceLessThanEqual(
                        price)
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    // Filter By Quantity
    @Override
    public List<ProductDTO>
    getProductsByQuantity(
            Integer quantity) {

        return productRepository
                .findByQuantityLessThanEqual(
                        quantity)
                .stream()
                .map(this::convertToDTO)
                .toList();
    }
}