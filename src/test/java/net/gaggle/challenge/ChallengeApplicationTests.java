package net.gaggle.challenge;

import net.gaggle.challenge.controllers.MovieController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
class ChallengeApplicationTests {

	@Autowired
	private MovieController controller;

	@Autowired
	private MockMvc mockMvc;

	@Test
	void contextLoads() {
		assertNotNull(controller);
	}


	@Test
	public void findAMovieById() throws Exception {
		this.mockMvc.perform(get("/movies/id/1147483650")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("Fake")));
	}

	@Test
	public void findAllMovies() throws Exception {
		this.mockMvc.perform(get("/movies/all")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("Fake")));
	}

	@Test
	public void findAllPeople() throws Exception {
		this.mockMvc.perform(get("/people/all")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("Orson")));
	}

	@Test
	public void findAllCrews() throws Exception {
		this.mockMvc.perform(get("/crew/all")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("Orson")))
				.andExpect(content().string(containsString("Fake")));

	}

	@Test
	public void findAMovieCrewById() throws Exception {
		this.mockMvc.perform(get("/crew/movie/2147483650")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("Return of the Jedi")));
	}

	@Test
	public void findAPersonById() throws Exception {
		this.mockMvc.perform(get("/crew/person/2")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("Kane")));
	}

	@Test
	public void findAPersonById_Overflow() throws Exception {
		this.mockMvc.perform(get("/crew/person/1")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("Jedi")));
	}
}
