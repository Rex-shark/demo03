package com.example.demoapi.contorller;

import com.example.demoservice.entity.OrderDemo;
import com.example.demoservice.entity.ProductDemo;
import com.example.demoservice.service.service.OrderDemoService;
import com.example.demoservice.service.service.ProductDemoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * order 與 product 是用來練習測試用的關聯實體
 */
@Tag(name = "4.商品",description = "商品相關API")
@Slf4j
@CrossOrigin(origins = {"http://localhost:4200"}) // 允許來自 Angular 前端的跨域請求
@RestController
@RequestMapping("/product")
public class ProductController {

    @Resource
    private ProductDemoService productDemoService;

    /**
     * 創建或更新商品
     * @param productDemo 商品資料
     * @return 創建或更新後的商品
     */
    @PostMapping
    public ResponseEntity<ProductDemo> saveProduct(@RequestBody ProductDemo productDemo) {
        System.out.println("新增商品saveProduct : " + productDemo);
        ProductDemo savedProduct = productDemoService.saveProduct(productDemo);
        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }

    /**
     * 查詢所有商品
     * @return 商品列表
     */
    @GetMapping
    public ResponseEntity<List<ProductDemo>> getAllProducts() {
        System.out.println("查詢商品");
        List<ProductDemo> products = productDemoService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    /**
     * 根據 UUID 查詢商品
     * @param uuid 商品的 UUID
     * @return 商品資料
     */
    @GetMapping("/{uuid}")
    public ResponseEntity<ProductDemo> getProductByUuid(@PathVariable String uuid) {
        System.out.println("查詢商品，UUID: {}"+ uuid);
        Optional<ProductDemo> product = productDemoService.getProductByUuid(uuid);
        return product.map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * 根據 ID 刪除商品
     * @param id 商品的 ID
     * @return 回應狀態
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductById(@PathVariable Long id) {
        log.info("刪除商品，ID: {}", id);
        productDemoService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * 根據 UUID 刪除商品
     * @param uuid 商品的 UUID
     * @return 回應狀態
     */
    @DeleteMapping("/uuid/{uuid}")
    public ResponseEntity<Void> deleteProductByUuid(@PathVariable String uuid) {
        log.info("刪除商品，UUID: {}", uuid);
        productDemoService.deleteProductByUuid(uuid);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
