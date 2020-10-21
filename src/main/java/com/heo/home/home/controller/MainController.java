package com.heo.home.home.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.GsonBuilder;
import com.heo.home.home.vo.ProductDto;

import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
public class MainController {

    @PostMapping("")
    public ResponseEntity<?> postProduct() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProduct(@PathVariable Integer id){
        ProductDto productDto = new ProductDto();
        productDto.setId(1);
        productDto.setName("Spring In Action");
        productDto.setDesc("Spring");
        productDto.setQuantity(10);

        String jsonString = new GsonBuilder().setPrettyPrinting().create().toJson(productDto);

        return new ResponseEntity<>(jsonString, HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<?> getAllProducts(@RequestParam String page,
                                            @RequestParam String size) {
        ProductDto productDto = new ProductDto();
        productDto.setId(1);
        productDto.setName("Spring In Action");
        productDto.setDesc("Spring");
        productDto.setQuantity(10);

        List<ProductDto> result = new ArrayList<ProductDto>();
        result.add(productDto);

        String jsonString = new GsonBuilder().setPrettyPrinting().create().toJson(result);

        return new ResponseEntity<>(jsonString, HttpStatus.OK);
    }

    @GetMapping("/test")
    public ResponseEntity<?> getTrueFalse(@RequestParam int cnt){
        boolean result = false;
        if(cnt > 0){
            result = true;
        }else{
            result = false;
        }

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("test", result);

        String jsonString = new GsonBuilder().setPrettyPrinting().create().toJson(map);

        return new ResponseEntity<>(jsonString, HttpStatus.OK);
        // return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/dailycommit/{id}")
    public ResponseEntity<?> getDailyCommit(@PathVariable String id){
        Map<String,Object> commit = new HashMap<String, Object>();

        commit.put("date", "2020-10-21");
        commit.put("continue", 100);
        commit.put("daily", true);
        commit.put("user", "user_id");

        String jsonString = new GsonBuilder().setPrettyPrinting().create().toJson(commit);

        return new ResponseEntity<>(jsonString, HttpStatus.OK);
    }
}
