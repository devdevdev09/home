package com.heo.home.home.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.heo.home.home.TestConfig;
import com.heo.home.home.vo.ProductDto;

// import com.google.gson.Gson;
// import com.google.gson.GsonBuilder;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.request.PathParametersSnippet;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.request.RequestDocumentation.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestConfig.class)
public class MainControllerTest {

    @Rule
    public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;
    private RestDocumentationResultHandler document;

    @Before
    public void setUp(){
        this.document = document(
            "{class-name}/{method-name}",
            preprocessResponse(prettyPrint())
        );
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
        .apply(documentationConfiguration(this.restDocumentation)
        .uris().withScheme("http").withHost("localhost").withPort(8080))
        .alwaysDo(document)
        .build();
    }

    @Test
    public void 상품_등록() throws Exception{
        ProductDto productDto = new ProductDto();
        productDto.setName("갤럭시 폴드");
        productDto.setDesc("삼성의 폴더블 스마트폰");
        productDto.setQuantity(10);
        productDto.setId(10);

        String jsonString = new GsonBuilder().setPrettyPrinting().create().toJson(productDto);
        Map<String, Object> data = new Gson().fromJson(jsonString, Map.class);

        mockMvc.perform(
        post("/api/products")
        .contentType(MediaType.APPLICATION_JSON)
        .content(jsonString)
        .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andDo(document.document(
            requestFields(
                    fieldWithPath("name").description("상품 이름"),
                    fieldWithPath("desc").description("상품 설명"),
                    fieldWithPath("quantity").type(Integer.class).description("상품 수량"),
                    fieldWithPath("id").type(Integer.class).description("아이디")
            )
        ));
    }

    //#region 상품조회
    @Test
    public void 상품_조회() throws Exception{
        mockMvc.perform(
            get("/api/products/{id}", 1)
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document.document(
                pathParameters(
                    parameterWithName("id").description("상품 id")
                ),
                responseFields(
                    fieldWithPath("id").description("상품 아이디"),
                    fieldWithPath("name").description("상품이름"),
                    fieldWithPath("desc").description("상품 설명"),
                    fieldWithPath("quantity").type(Integer.class).description("상품 수량")
                )
            ))
            .andExpect(jsonPath("id", is(notNullValue())))
            .andExpect(jsonPath("name", is(notNullValue())))
            .andExpect(jsonPath("desc", is(notNullValue())))
            .andExpect(jsonPath("quantity", is(notNullValue())));
    }
    //#endregion
    
    @Test
    public void 상품_리스트_조회() throws Exception {
        mockMvc.perform(
            get("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .param("page", "1")
                .param("size", "10")
                .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andDo(document.document(
            requestParameters(
                    parameterWithName("page").description("페이지 번호"),
                    parameterWithName("size").description("페이지 사이즈")
            ),
            responseFields(
                fieldWithPath("[].id").description("상품 아이디"),
                fieldWithPath("[].name").description("상품 이름"),
                fieldWithPath("[].desc").description("상품 설명"),
                fieldWithPath("[].quantity").type(Integer.class).description("상품 수량")
            )
        ))
            .andExpect(jsonPath("[0].id", is(notNullValue())))
            .andExpect(jsonPath("[0].name", is(notNullValue())))
            .andExpect(jsonPath("[0].desc", is(notNullValue())))
            .andExpect(jsonPath("[0].quantity", is(notNullValue())));
    }

    @Test
    public void 테스트_테스트_코드() throws Exception{
        mockMvc.perform(
            get("/api/products/test")
                .contentType(MediaType.APPLICATION_JSON)
                .param("cnt", "5")
                .accept(MediaType.APPLICATION_JSON))
            // .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("test", is(true)))
            ;

        mockMvc.perform(
            get("/api/products/test")
                .contentType(MediaType.APPLICATION_JSON)
                .param("cnt", "10")
                .accept(MediaType.APPLICATION_JSON))
            // .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().json("{'test':true}"))
            ;

        mockMvc.perform(
            get("/api/products/test")
                .contentType(MediaType.APPLICATION_JSON)
                .param("cnt", "-1")
                .accept(MediaType.APPLICATION_JSON))
            // .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().json("{'test':false}"))
            .andDo(document.document(
                requestParameters(
                    parameterWithName("cnt").description("카운트값")
                ),
                responseFields(
                    fieldWithPath("test").description("리턴결과")
                )
            ))
            ;
    }


}
