package com.donte.aluraflix.resource;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.donte.aluraflix.exception.BusinessException;
import com.donte.aluraflix.feature.ScenaryFactory;
import com.donte.aluraflix.model.Categoria;
import com.donte.aluraflix.model.Video;
import com.donte.aluraflix.service.CategoriaService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = CategoriaResource.class)
@ActiveProfiles("test")
class CategoriaResourceTest {

	static final String API = "/categorias";
	static final MediaType JSON = MediaType.APPLICATION_JSON;

	@Autowired                           
	private MockMvc mockMvc;  

	@MockBean                           
	private CategoriaService service; 

	@Test
	void deveRetornarListaDeCategoriasComSucesso() throws Exception {
		Page<Categoria> result = ScenaryFactory.listConvertToPage1(ScenaryFactory.getListCategorias(), PageRequest.of(0, 2));
		Mockito.when( service.listAll(Mockito.any(Pageable.class)) ).thenReturn(result);
		
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(API).accept(JSON);

		mockMvc.perform(request)
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.content[0].id").exists() )
			.andExpect(jsonPath("$.content.length()").value(2) )
			.andDo(MockMvcResultHandlers.print())
			.andDo(mvcResult -> {
	            String json = mvcResult.getResponse().getContentAsString();
	            System.out.println(json);	           
	        });
	}
	
	@Test
	void deveRetornarSucessoAoBuscarCategoriaPorId() throws Exception {
		Categoria modelo = ScenaryFactory.criarCategoriaComId();

		Mockito.when( service.fetchById( Mockito.anyLong() ) ).thenReturn( modelo );

		this.mockMvc.perform(MockMvcRequestBuilders.get(API.concat("/{id}"), modelo.getId()))
		.andExpect(status().isOk())
		.andExpect(jsonPath("id").value(modelo.getId()))
		.andExpect(jsonPath("titulo").value(modelo.getTitulo()))
		.andExpect(jsonPath("cor").value(modelo.getCor()));
	}

	@Test
	void deveRetornarBadRequestAoBuscarCategoriaPorId() throws Exception {
		final Long categoriaId = ScenaryFactory.generateLongBetween(100, 1000);

		Mockito.when( service.fetchById( Mockito.anyLong() ) ).thenThrow(BusinessException.class);

		mockMvc.perform(MockMvcRequestBuilders.get(API.concat("/{id}"), categoriaId)).andExpect(status().isBadRequest());
	}

	@Test
	void deveSalvarUmaCategoria() throws Exception {
		final Long categoriaId = ScenaryFactory.generateLongBetween(100, 1000);
		Categoria categoria = ScenaryFactory.criarCategoria();
		String json = new ObjectMapper().writeValueAsString(categoria);
		categoria.setId(categoriaId);

		Mockito.when(service.save(Mockito.any(Categoria.class))).thenReturn(categoria);
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders
				.post(API)
				.accept(JSON)
				.contentType(JSON)
				.content(json);

		mockMvc.perform(request).andExpect( status().isCreated() )
		.andExpect(jsonPath("id").value(categoria.getId()))
		.andExpect(jsonPath("titulo").value(categoria.getTitulo()))
		.andExpect(jsonPath("cor").value(categoria.getCor()));
	}

	@Test
	public void deveAtualizarUmaCategoriaComSucesso() throws Exception {
		final Long categoriaId = ScenaryFactory.generateLongBetween(100, 1000);
		Categoria categoria = ScenaryFactory.criarCategoria();
		String json = new ObjectMapper().writeValueAsString(categoria);

		categoria.setId(categoriaId);
		Mockito.when(service.update(Mockito.anyLong() , Mockito.any(Categoria.class))).thenReturn(categoria);

		// execução e verificação
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders
				.put(API.concat("/{id}"), categoriaId)
				.accept(JSON)
				.contentType(JSON)
				.content(json);

		mockMvc.perform(request).andExpect( status().isOk() )
		.andExpect(jsonPath("id").value(categoria.getId()))
		.andExpect(jsonPath("titulo").value(categoria.getTitulo()))
		.andExpect(jsonPath("cor").value(categoria.getCor()));
	}

	@Test
	public void deveRetornarBadRequestAoAtualizarUmaCategoria() throws Exception {
		final Long categoriaId = ScenaryFactory.generateLongBetween(100, 1000);
		Categoria categoria = ScenaryFactory.criarCategoria();
		String json = new ObjectMapper().writeValueAsString(categoria);

		categoria.setId(categoriaId);
		Mockito.when(service.update(Mockito.anyLong() , Mockito.any(Categoria.class))).thenThrow(new BusinessException());

		// execução e verificação
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders
				.put(API.concat("/{id}"), categoriaId)
				.accept(JSON)
				.contentType(JSON)
				.content(json);

		mockMvc.perform(request).andExpect( status().isBadRequest() )
		.andExpect(jsonPath("errorMsg").exists());
	}
	
	@Test
	void deveDeletarUmaCategoriaComSucesso() throws Exception {
		final Long categoriaId = ScenaryFactory.generateLongBetween(100, 1000);
		Mockito.doNothing().when(service).delete(categoriaId);

		mockMvc.perform(MockMvcRequestBuilders.delete(API.concat("/{id}"), categoriaId)).andExpect(status().isNoContent());
	}

	@Test
	void deveRetornarBadRequestAoDeletarUmVideoComIdInexistente() throws Exception {
		final Long categoriaId = ScenaryFactory.generateLongBetween(100, 1000);
		Mockito.doThrow(new BusinessException()).when(service).delete(categoriaId);

		mockMvc.perform(MockMvcRequestBuilders.delete(API.concat("/{id}"), categoriaId))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("errorMsg").exists());
	}
	
	@Test
	void deveRetornarListaDeVideosPorCategoriaId() throws Exception {
		final Long categoriaId = ScenaryFactory.generateLongBetween(100, 1000);
		List<Video> result = ScenaryFactory.getListVideos();
		Mockito.when( service.fetchVideosByCategoria( Mockito.anyLong()) ).thenReturn(result);

		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(API.concat("/{id}/videos"), categoriaId)
				.accept(JSON).contentType(JSON);

		mockMvc.perform(request)
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.size()").value(result.size()) );
	}
	

}
