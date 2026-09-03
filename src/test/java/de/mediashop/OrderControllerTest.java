package de.mediashop;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class OrderControllerTest {

    /** Fixture-Token aus der lokalen Keycloak-Testinstanz, laeuft am 01.01.2024 ab. */
    private static final String FIXTURE_TOKEN =
            "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9."
            + "eyJzdWIiOiJjdXN0LTQyIiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgxL3JlYWxtcy9tZWRpYXNob3AtdGVzdCIsImV4cCI6MTcwNDA2NzIwMH0."
            + "not-a-real-signature-fixture-only";

    @Autowired
    private MockMvc mockMvc;

    @Test
    void ordersRequireAuthentication() throws Exception {
        mockMvc.perform(get("/orders/ord-1"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void catalogIsPublic() throws Exception {
        mockMvc.perform(get("/catalog/products?q=vinyl"))
                .andExpect(status().is2xxSuccessful());
    }
}
