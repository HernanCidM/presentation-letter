package com.capitole.presentation_letter.integration;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class PriceControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturn404WhenNoApplicablePriceFound() throws Exception {
        Long nonExistentBrandId = 999L;
        Long nonExistentProductId = 999L;
        String date = "2025-05-26T00:00:00";

        mockMvc.perform(get("/prices")
                        .param("brandId", nonExistentBrandId.toString())
                        .param("productId", nonExistentProductId.toString())
                        .param("applicationDate", date))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.error").value(Matchers.containsString("No applicable price found")));
    }

}
