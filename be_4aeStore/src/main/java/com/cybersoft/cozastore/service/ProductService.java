package com.cybersoft.cozastore.service;

import com.cybersoft.cozastore.entity.CategoryEntity;
import com.cybersoft.cozastore.entity.ColorEntity;
import com.cybersoft.cozastore.entity.ProductEntity;
import com.cybersoft.cozastore.entity.SizeEntity;
import com.cybersoft.cozastore.payload.request.CreateProductRequest;
import com.cybersoft.cozastore.repository.CategoryRepository;
import com.cybersoft.cozastore.repository.ProductRepository;
import com.cybersoft.cozastore.service.imp.ProductServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements ProductServiceImp {

    @Value("${root.folder}")
    private String rootFolder;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public ProductEntity updateProduct(int id, CreateProductRequest request) throws ChangeSetPersister.NotFoundException {
        ProductEntity product= productRepository.findById(id).orElseThrow(() -> new ChangeSetPersister.NotFoundException());
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setQuantity(request.getQuantity());


        Optional<CategoryEntity> optionalCategory = categoryRepository.findById(request.getCategoryId());
        CategoryEntity category = optionalCategory.orElse(new CategoryEntity()); // Hoặc thực hiện xử lý nếu không tìm thấy Category
        product.setCategory(category);

        try {
            productRepository.save(product);
        } catch (Exception e) {
            throw new RuntimeException("Error saving product to the database: " + e.getMessage());
        }
        return product;
    }


    @Override
    public void deleteProduct(int id) {
        this.productRepository.deleteById(id);
    }

    @Override
    public List<ProductEntity> searchProducts(String keyword) {
        return productRepository.findByNameContaining(keyword);

    }

    @Override
    public boolean insertProduct(String name, MultipartFile file, double price, int quanity, int idColor, int idSize, int idCategory) throws IOException {

        String pathImage = rootFolder + "\\" + file.getOriginalFilename();

        Path path = Paths.get(rootFolder);
        Path pathImageCopy = Paths.get(pathImage);

        if (!Files.exists(path)) {
            Files.createDirectory(path);
        }

        Files.copy(file.getInputStream(), pathImageCopy, StandardCopyOption.REPLACE_EXISTING);

        ProductEntity productEntity = new ProductEntity();
        productEntity.setName(name);
        productEntity.setImage(file.getOriginalFilename());
        productEntity.setPrice(price);
        productEntity.setQuantity(quanity);

        ColorEntity colorEntity = new ColorEntity();
        colorEntity.setId(idColor);
        productEntity.setColor(colorEntity);

        SizeEntity sizeEntity = new SizeEntity();
        sizeEntity.setId(idSize);
        productEntity.setSize(sizeEntity);

        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setId(idCategory);
        productEntity.setCategory(categoryEntity);

        productRepository.save(productEntity);

        return false;
    }

}
