package me.ianhe.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.annotation.WebServlet;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * @author iHelin
 * @since 2017/8/26 15:07
 */
@RunWith(SpringJUnit4ClassRunner.class) //spring的单元测试
@WebAppConfiguration
@WebServlet
@ContextConfiguration({"classpath:spring/applicationContext.xml","classpath:spring/spring-web.xml"})
public class TestControllerTest {

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;
    private MockHttpSession session;

    @Before
    public void setUp() throws Exception {
        this.session = new MockHttpSession();
        this.mockMvc = webAppContextSetup(this.wac).build();
        /*this.mockMvc.perform(MockMvcRequestBuilders.post("/login").session(session)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .header("Content-Type", "application/json;charset=UTF-8")
                .andExpect(status().isOk())
                .andDo(print());*/
    }

    @Test
    public void dateTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/dateTest").session(this.session)
                .header("Content-Type", "application/json;charset=UTF-8")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .param("date", "2017-08-26 15:08:30"))
                .andDo(print())
                .andExpect(status().isOk());
    }

}