package com.example.demoservice.service.service;

import com.example.demoservice.entity.ProductDemo;
import com.example.demoservice.repository.IProductDemoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductDemoService {

    @Autowired
    private IProductDemoRepository productDemoRepository;

    /**
     * 創建或更新商品
     * @param productDemo 要創建或更新的商品
     * @return 返回創建或更新後的商品
     */
    public ProductDemo saveProduct(ProductDemo productDemo) {
        return productDemoRepository.save(productDemo);
    }

    /**
     * 根據 UUID 查詢商品
     * @param uuid 商品的 UUID
     * @return 返回商品實體，若找不到則返回空
     */
    public Optional<ProductDemo> getProductByUuid(String uuid) {
        return productDemoRepository.findByUuid(uuid);
    }

    /**
     * 查詢所有商品
     * @return 返回所有商品列表
     */
    public List<ProductDemo> getAllProducts() {
        return productDemoRepository.findAll();
    }

    /**
     * 根據 ID 刪除商品
     * @param id 商品的 ID
     */
    public void deleteProduct(Long id) {
        productDemoRepository.deleteById(id);
    }

    /**
     * 根據 UUID 刪除商品
     * @param uuid 商品的 UUID
     */
    public void deleteProductByUuid(String uuid) {
        Optional<ProductDemo> productOptional = productDemoRepository.findByUuid(uuid);
        productOptional.ifPresent(product -> productDemoRepository.delete(product));
    }
}
