package com.donte.aluraflix.resource;

//import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.donte.aluraflix.exception.BusinessException;
import com.donte.aluraflix.feature.ScenaryFactory;
import com.donte.aluraflix.model.Video;
import com.donte.aluraflix.projection.VideoDto;
import com.donte.aluraflix.service.VideoService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = VideoResource.class)
@ActiveProfiles("test")
class VideoResourceTest {

	static final String API = "/videos";
	static final MediaType JSON = MediaType.APPLICATION_JSON;

	@Autowired                           
	private MockMvc mockMvc;  

	@MockBean                           
	private VideoService videoService; 


	@Test
	void deveRetornarListaDeVideosComSucesso() throws Exception {
		List<Video> result = ScenaryFactory.getListVideos();
		Mockito.when( videoService.listAll( Mockito.any(Video.class) ) ).thenReturn(result);

		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(API).accept(JSON).contentType(JSON);

		mockMvc.perform(request)
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.size()").value(result.size()) );
	}

	@Test
	void deveRetornarSucessoAoBuscarVideoPorId() throws Exception {
		Video modelo = ScenaryFactory.criarVideoComId();
		VideoDto dto = new VideoDto(modelo);

		Mockito.when( videoService.fetchById( Mockito.anyLong() ) ).thenReturn( modelo );

		this.mockMvc.perform(MockMvcRequestBuilders.get(API.concat("/{id}"), modelo.getId()))
		.andExpect(status().isOk())
		.andExpect(jsonPath("id").value(dto.getId()))
		.andExpect(jsonPath("titulo").value(dto.getTitulo()))
		.andExpect(jsonPath("descricao").value(dto.getDescricao()))
		.andExpect(jsonPath("categoriaId").value(dto.getCategoriaId()))
		.andExpect(jsonPath("url").value(dto.getUrl()));
	}

	@Test
	void deveRetornarBadRequestAoBuscarVideoPorId() throws Exception {
		final Long videoId = ScenaryFactory.generateLongBetween(100, 1000);

		Mockito.when( videoService.fetchById( Mockito.anyLong() ) ).thenThrow(BusinessException.class);

		mockMvc.perform(MockMvcRequestBuilders.get(API.concat("/{id}"), videoId)).andExpect(status().isBadRequest());
	}

	@Test
	public void deveSalvarUmVideo() throws Exception {
		// cenário
		Video video = ScenaryFactory.criarVideoComId();
		VideoDto dto = new VideoDto(video);
		String json = new ObjectMapper().writeValueAsString(dto);
		Mockito.when(videoService.save(Mockito.any(Video.class))).thenReturn(video);

		// execução e verificação
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders
				.post(API)
				.accept(JSON)
				.contentType(JSON)
				.content(json);

		mockMvc.perform(request).andExpect( status().isCreated() )
		.andExpect(jsonPath("id").value(dto.getId()))
		.andExpect(jsonPath("titulo").value(dto.getTitulo()))
		.andExpect(jsonPath("descricao").value(dto.getDescricao()))
		.andExpect(jsonPath("categoriaId").value(dto.getCategoriaId()))
		.andExpect(jsonPath("url").value(dto.getUrl()));
	}

	@Test
	public void deveAtualizarUmVideoComSucesso() throws Exception {
		// cenário
		final Long videoId = ScenaryFactory.generateLongBetween(100, 1000);
		VideoDto dto = ScenaryFactory.criarVideoDto();
		String json = new ObjectMapper().writeValueAsString(dto);

		dto.setId(videoId);
		Mockito.when(videoService.update(Mockito.anyLong() , Mockito.any(Video.class))).thenReturn(new Video(dto));

		// execução e verificação
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders
				.put(API.concat("/{id}"), videoId)
				.accept(JSON)
				.contentType(JSON)
				.content(json);

		mockMvc.perform(request).andExpect( status().isOk() )
		.andExpect(jsonPath("id").value(dto.getId()))
		.andExpect(jsonPath("titulo").value(dto.getTitulo()))
		.andExpect(jsonPath("descricao").value(dto.getDescricao()))
		.andExpect(jsonPath("categoriaId").value(dto.getCategoriaId()))
		.andExpect(jsonPath("url").value(dto.getUrl()));
	}

	@Test
	public void deveRetornarBadRequestAtualizarUmVideoComCategoriaInexistente() throws Exception {
		// cenário
		final Long videoId = ScenaryFactory.generateLongBetween(100, 1000);
		VideoDto dto = ScenaryFactory.criarVideoDto();
		String json = new ObjectMapper().writeValueAsString(dto);

		dto.setId(videoId);
		Mockito.when(videoService.update(Mockito.anyLong() , Mockito.any(Video.class))).thenThrow(new BusinessException("Categoria não cadastrada"));

		// execução e verificação
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders
				.put(API.concat("/{id}"), videoId)
				.accept(JSON)
				.contentType(JSON)
				.content(json);

		mockMvc.perform(request).andExpect( status().isBadRequest() )
		.andExpect(jsonPath("errorMsg").value("Categoria não cadastrada"));
	}

	@Test
	public void deveRetornarBadRequestAtualizarUmVideoInexistente() throws Exception {
		// cenário
		final Long videoId = ScenaryFactory.generateLongBetween(100, 1000);
		VideoDto dto = ScenaryFactory.criarVideoDto();
		String json = new ObjectMapper().writeValueAsString(dto);

		dto.setId(videoId);
		Mockito.when(videoService.update(Mockito.anyLong() , Mockito.any(Video.class))).thenThrow(new BusinessException("Vídeo não encontrado"));

		// execução e verificação
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders
				.put(API.concat("/{id}"), videoId)
				.accept(JSON)
				.contentType(JSON)
				.content(json);

		mockMvc.perform(request).andExpect( status().isBadRequest() )
		.andExpect(jsonPath("errorMsg").value("Vídeo não encontrado"));
	}


	@Test
	void deveRetornarBadRequestAoSalvarVideoComCategoriaInexistente() throws Exception {

		VideoDto dto = ScenaryFactory.criarVideoDto();
		String json = new ObjectMapper().writeValueAsString(dto);
		Mockito.when( videoService.save(Mockito.any(Video.class)) ).thenThrow(new BusinessException("Categoria não cadastrada"));

		// execução e verificação
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders
				.post(API)
				.accept(JSON)
				.contentType(JSON)
				.content(json);

		mockMvc.perform(request).andExpect( status().isBadRequest() )
		.andExpect(jsonPath("errorMsg").value("Categoria não cadastrada"));
	}

	@Test
	void deveDeletarUmVideoComSucesso() throws Exception {
		final Long videoId = ScenaryFactory.generateLongBetween(100, 1000);
		Mockito.doNothing().when(videoService).delete(videoId);

		mockMvc.perform(MockMvcRequestBuilders.delete(API.concat("/{id}"), videoId)).andExpect(status().isNoContent());
	}

	@Test
	void deveRetornarBadRequestAoDeletarUmVideoComIdInexistente() throws Exception {
		final Long videoId = ScenaryFactory.generateLongBetween(100, 1000);
		Mockito.doThrow(new BusinessException("Vídeo inexistente")).when(videoService).delete(videoId);

		mockMvc.perform(MockMvcRequestBuilders.delete(API.concat("/{id}"), videoId))
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("errorMsg").value("Vídeo inexistente"));;
	}

}
