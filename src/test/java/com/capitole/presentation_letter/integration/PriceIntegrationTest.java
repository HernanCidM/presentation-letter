package com.capitole.presentation_letter.integration;

import com.capitole.presentation_letter.PresentationLetterApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = PresentationLetterApplication.class)
@AutoConfigureMockMvc
public class PriceIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    private void performRequestAndValidate(String dateTime, int expectedPriceList, double expectedPrice) throws Exception {
        ResultActions result = mockMvc.perform(get("/prices")
                .param("brandId", "1")
                .param("productId", "35455")
                .param("applicationDate", dateTime));

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.priceList").value(expectedPriceList))
                .andExpect(jsonPath("$.price").value(expectedPrice));
    }

    @Test
    void test1() throws Exception {
        performRequestAndValidate("2020-06-14T10:00:00", 1, 35.50);
    }

    @Test
    void test2() throws Exception {
        performRequestAndValidate("2020-06-14T16:00:00", 2, 25.45);
    }

    @Test
    void test3() throws Exception {
        performRequestAndValidate("2020-06-14T21:00:00", 1, 35.50);
    }

    @Test
    void test4() throws Exception {
        performRequestAndValidate("2020-06-15T10:00:00", 3, 30.50);
    }

    @Test
    void test5() throws Exception {
        performRequestAndValidate("2020-06-16T21:00:00", 4, 38.95);
    }
}
