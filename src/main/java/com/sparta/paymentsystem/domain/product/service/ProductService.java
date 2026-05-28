package com.sparta.paymentsystem.domain.product.service;

import com.sparta.paymentsystem.domain.product.entity.Product;
import com.sparta.paymentsystem.domain.product.dto.ProductResponse;
import com.sparta.paymentsystem.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;

    /**
     * 상품을 전체조회를 합니다.
     * @return
     */
    public List<ProductResponse> findAll() {
        return productRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public ProductResponse findById(Long id) {
        Product product = findProductEntity(id);
        return toResponse(product);
    }

    // 다른 클래스에서 사용하기 위해서 만들어 주는거 / Product로 반환하는 메서드
    public Product findProductEntity(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("상품을 찾을 수 없습니다."));
    }

    private ProductResponse toResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getStock(),
                product.getDescription()
        );
    }
}