package com.ga.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.fasterxml.jackson.databind.ObjectWriter;
import com.ga.entity.Song;
import com.ga.entity.User;
import com.ga.service.UserService;


@RunWith(MockitoJUnitRunner.class) 
public class UserControllerTest {

	private MockMvc mockMvc;

	@InjectMocks
	UserController userController;
	
	@Mock
	UserService userService;

	@Before
	public void init() {
		mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
	}
	
	
	@Test
	public void signup_User_Success() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders
			       .post("/user/signup")
			       .contentType(MediaType.APPLICATION_JSON)
			       .content(createUserInJson("batman","robin"));
		
		when(userService.signup(any())).thenReturn("jwt");
		
		MvcResult result = mockMvc.perform(requestBuilder)
	              .andExpect(status().isOk())
	              .andExpect(content().json("{\"token\":\"jwt\"}"))
	              .andReturn();
	      
	      System.out.println(result.getResponse().getContentAsString());
	}
	
	@Test
	public void signIn_User_Success() throws Exception{
		RequestBuilder requestBuilder = MockMvcRequestBuilders
			       .post("/user/login")
			       .contentType(MediaType.APPLICATION_JSON)
			       .content(createUserInJson("batman","robin"));
		
		when(userService.singIn(any())).thenReturn("jwt");
		
		mockMvc.perform(requestBuilder)
          .andExpect(status().isOk())
          .andExpect(content().json("{\"token\":\"jwt\"}"));
	}
	
//	@Test
//	public void updateUser_User_Success() throws Exception {
//		String response = createUserInJson("batman","alfred");
//
//		RequestBuilder requestBuilder = MockMvcRequestBuilders
//			       .put("/user/update/1")
//			       .contentType(MediaType.APPLICATION_JSON)
//			       .content(response);
//		
//		User u = new User();
//		u.setUserName("batman");
//		u.setPassword("alfred");
//	
//	    
//		when(userService.updateUser(any(), any())).thenReturn(u);
//		
//		mockMvc.perform(requestBuilder)
//        .andExpect(status().isOk())
//        .andExpect(content().json(response));
//	}

	
	private static String createUserInJson(String username, String password) {
        return "{ \"username\": \"" + username + "\", " +
                "\"password\":\"" + password + "\"}";
    }

}