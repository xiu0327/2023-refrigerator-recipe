package refrigerator.back.word_completion.adapter.in.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@SpringBootTest
@Transactional
class WordCompletionControllerTest {

    @Autowired
    MockMvc mockMvc;


    @Test
    void 레시피_자동_완성() throws Exception {
        String keyword = "참치";
        mockMvc.perform(get("/api/word-completion/recipe?keyword=" + keyword)
        ).andExpect(status().is2xxSuccessful()
        ).andDo(print());
    }

    @Test
    void 식재료_자동_완성() throws Exception {
        String keyword = "ㅇ";
        mockMvc.perform(get("/api/word-completion/ingredient?keyword=" + keyword)
        ).andExpect(status().is2xxSuccessful()
        ).andDo(print());
    }
}