package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.web.rest.TestUtil.createFormattingConversionService;
import static com.mycompany.myapp.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;

import javax.persistence.EntityManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import com.mycompany.myapp.ProjetApp;
import com.mycompany.myapp.domain.ResultatRecherche;
import com.mycompany.myapp.repository.ResultatRechercheRepository;
import com.mycompany.myapp.web.rest.errors.ExceptionTranslator;

/**
 * Test class for the ResultatRechercheResource REST controller.
 *
 * @see ResultatRechercheResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProjetApp.class)
public class ResultatRechercheResourceIntTest {

    private static final ZonedDateTime DEFAULT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private ResultatRechercheRepository resultatRechercheRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restResultatRechercheMockMvc;

    private ResultatRecherche resultatRecherche;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ResultatRechercheResource resultatRechercheResource = new ResultatRechercheResource(resultatRechercheRepository);
        this.restResultatRechercheMockMvc = MockMvcBuilders.standaloneSetup(resultatRechercheResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ResultatRecherche createEntity(EntityManager em) {
        ResultatRecherche resultatRecherche = new ResultatRecherche()
            .date(DEFAULT_DATE);
        return resultatRecherche;
    }

    @Before
    public void initTest() {
        resultatRecherche = createEntity(em);
    }

    @Test
    @Transactional
    public void createResultatRecherche() throws Exception {
        int databaseSizeBeforeCreate = resultatRechercheRepository.findAll().size();

        // Create the ResultatRecherche
        restResultatRechercheMockMvc.perform(post("/api/resultat-recherches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resultatRecherche)))
            .andExpect(status().isCreated());

        // Validate the ResultatRecherche in the database
        List<ResultatRecherche> resultatRechercheList = resultatRechercheRepository.findAll();
        assertThat(resultatRechercheList).hasSize(databaseSizeBeforeCreate + 1);
        ResultatRecherche testResultatRecherche = resultatRechercheList.get(resultatRechercheList.size() - 1);
        assertThat(testResultatRecherche.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void createResultatRechercheWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = resultatRechercheRepository.findAll().size();

        // Create the ResultatRecherche with an existing ID
        resultatRecherche.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restResultatRechercheMockMvc.perform(post("/api/resultat-recherches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resultatRecherche)))
            .andExpect(status().isBadRequest());

        // Validate the ResultatRecherche in the database
        List<ResultatRecherche> resultatRechercheList = resultatRechercheRepository.findAll();
        assertThat(resultatRechercheList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = resultatRechercheRepository.findAll().size();
        // set the field null
        resultatRecherche.setDate(null);

        // Create the ResultatRecherche, which fails.

        restResultatRechercheMockMvc.perform(post("/api/resultat-recherches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resultatRecherche)))
            .andExpect(status().isBadRequest());

        List<ResultatRecherche> resultatRechercheList = resultatRechercheRepository.findAll();
        assertThat(resultatRechercheList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllResultatRecherches() throws Exception {
        // Initialize the database
        resultatRechercheRepository.saveAndFlush(resultatRecherche);

        // Get all the resultatRechercheList
        restResultatRechercheMockMvc.perform(get("/api/resultat-recherches?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(resultatRecherche.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(sameInstant(DEFAULT_DATE))));
    }
    
    @Test
    @Transactional
    public void getResultatRecherche() throws Exception {
        // Initialize the database
        resultatRechercheRepository.saveAndFlush(resultatRecherche);

        // Get the resultatRecherche
        restResultatRechercheMockMvc.perform(get("/api/resultat-recherches/{id}", resultatRecherche.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(resultatRecherche.getId().intValue()))
            .andExpect(jsonPath("$.date").value(sameInstant(DEFAULT_DATE)));
    }

    @Test
    @Transactional
    public void getNonExistingResultatRecherche() throws Exception {
        // Get the resultatRecherche
        restResultatRechercheMockMvc.perform(get("/api/resultat-recherches/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateResultatRecherche() throws Exception {
        // Initialize the database
        resultatRechercheRepository.saveAndFlush(resultatRecherche);

        int databaseSizeBeforeUpdate = resultatRechercheRepository.findAll().size();

        // Update the resultatRecherche
        ResultatRecherche updatedResultatRecherche = resultatRechercheRepository.findById(resultatRecherche.getId()).get();
        // Disconnect from session so that the updates on updatedResultatRecherche are not directly saved in db
        em.detach(updatedResultatRecherche);
        updatedResultatRecherche
            .date(UPDATED_DATE);

        restResultatRechercheMockMvc.perform(put("/api/resultat-recherches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedResultatRecherche)))
            .andExpect(status().isOk());

        // Validate the ResultatRecherche in the database
        List<ResultatRecherche> resultatRechercheList = resultatRechercheRepository.findAll();
        assertThat(resultatRechercheList).hasSize(databaseSizeBeforeUpdate);
        ResultatRecherche testResultatRecherche = resultatRechercheList.get(resultatRechercheList.size() - 1);
        assertThat(testResultatRecherche.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingResultatRecherche() throws Exception {
        int databaseSizeBeforeUpdate = resultatRechercheRepository.findAll().size();

        // Create the ResultatRecherche

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResultatRechercheMockMvc.perform(put("/api/resultat-recherches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resultatRecherche)))
            .andExpect(status().isBadRequest());

        // Validate the ResultatRecherche in the database
        List<ResultatRecherche> resultatRechercheList = resultatRechercheRepository.findAll();
        assertThat(resultatRechercheList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteResultatRecherche() throws Exception {
        // Initialize the database
        resultatRechercheRepository.saveAndFlush(resultatRecherche);

        int databaseSizeBeforeDelete = resultatRechercheRepository.findAll().size();

        // Delete the resultatRecherche
        restResultatRechercheMockMvc.perform(delete("/api/resultat-recherches/{id}", resultatRecherche.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ResultatRecherche> resultatRechercheList = resultatRechercheRepository.findAll();
        assertThat(resultatRechercheList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ResultatRecherche.class);
        ResultatRecherche resultatRecherche1 = new ResultatRecherche();
        resultatRecherche1.setId(1L);
        ResultatRecherche resultatRecherche2 = new ResultatRecherche();
        resultatRecherche2.setId(resultatRecherche1.getId());
        assertThat(resultatRecherche1).isEqualTo(resultatRecherche2);
        resultatRecherche2.setId(2L);
        assertThat(resultatRecherche1).isNotEqualTo(resultatRecherche2);
        resultatRecherche1.setId(null);
        assertThat(resultatRecherche1).isNotEqualTo(resultatRecherche2);
    }
}
