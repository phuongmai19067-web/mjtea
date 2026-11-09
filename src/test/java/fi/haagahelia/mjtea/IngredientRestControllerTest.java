package fi.haagahelia.mjtea;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class IngredientRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // GET /ingredients phải trả 200 và JSON có chứa tên nguyên liệu mẫu
    @Test
    public void getAllIngredientsShouldReturnIngredients() throws Exception {
        this.mockMvc.perform(get("/ingredients").with(user("staff").roles("STAFF")))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Oat Milk")));
    }

    // GET /ingredients/1 phải trả 200
    @Test
    public void getIngredientByIdShouldReturnIngredient() throws Exception {
        this.mockMvc.perform(get("/ingredients/1").with(user("staff").roles("STAFF")))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("name")));
    }
}