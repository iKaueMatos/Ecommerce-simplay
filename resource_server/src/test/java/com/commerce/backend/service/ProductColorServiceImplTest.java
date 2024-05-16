package com.commerce.backend.service;

import com.commerce.backend.core.error.exception.ResourceNotFoundException;
import com.commerce.backend.product.application.converter.ProductColorResponseConverter;
import com.commerce.backend.product.application.useCases.dto.ProductColorResponse;
import com.commerce.backend.product.domain.model.ColorDTO;
import com.commerce.backend.product.domain.service.ProductColorServiceImpl;
import com.commerce.backend.product.infra.cache.ProductColorCacheService;
import com.commerce.backend.product.infra.entity.Color;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class ProductColorServiceImplTest {

    @InjectMocks
    private ProductColorServiceImpl productColorService;

    @Mock
    private ProductColorCacheService productColorCacheService;

    @Mock
    private ProductColorResponseConverter productColorResponseConverter;

    private Faker faker;

    @BeforeEach
    public void setUp() {
        faker = new Faker();
    }

    @Test
    void it_should_find_all_product_colors() {

        // given
        String colorName = faker.color().name();
        Color color = new Color();
        color.setName(colorName);
        ProductColorResponse productColorResponse = new ProductColorResponse();
        productColorResponse.setColor(ColorDTO.builder().name(colorName).build());

        List<Color> colorList = Stream.generate(() -> color)
                .limit(faker.number().randomDigitNotZero())
                .collect(Collectors.toList());

        given(productColorCacheService.findAll()).willReturn(colorList);
        given(productColorResponseConverter.apply(any(Color.class))).willReturn(productColorResponse);

        // when
        List<ProductColorResponse> productColorResponseList = productColorService.findAll();

        // then
        then(productColorResponseList.size()).isEqualTo(colorList.size());
        productColorResponseList.forEach(productColorResponse1 -> then(productColorResponse1.getColor().getName()).isEqualTo(color.getName()));

    }

    @Test
    void it_should_throw_exception_when_no_color() {
        given(productColorCacheService.findAll()).willReturn(Collections.emptyList());

        assertThatThrownBy(() -> productColorService.findAll())
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Could not find product colors");
    }
}