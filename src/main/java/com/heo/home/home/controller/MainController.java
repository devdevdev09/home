package com.heo.home.home.controller;

import java.util.ArrayList;
import java.util.List;

import com.heo.home.home.vo.ProductDto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
public class MainController {

    @PostMapping("")
    public ResponseEntity<?> postProduct() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("")
    @ResponseBody
    public ResponseEntity<?> getAllProducts(@RequestParam String page,
                                            @RequestParam String size) {
        ProductDto productDto = new ProductDto();
        productDto.setId(1);
        productDto.setName("Spring In Action");
        productDto.setDesc("Spring");
        productDto.setQuantity(10);

        List<ProductDto> result = new ArrayList<>();
        result.add(productDto);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
