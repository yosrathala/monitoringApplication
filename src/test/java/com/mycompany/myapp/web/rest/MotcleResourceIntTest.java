package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.ProjetApp;

import com.mycompany.myapp.domain.Motcle;
import com.mycompany.myapp.repository.MotcleRepository;
import com.mycompany.myapp.web.rest.errors.ExceptionTranslator;

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

import javax.persistence.EntityManager;
import java.util.List;


import static com.mycompany.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the MotcleResource REST controller.
 *
 * @see MotcleResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProjetApp.class)
public class MotcleResourceIntTest {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_MOTINCLUE = "AAAAAAAAAA";
    private static final String UPDATED_MOTINCLUE = "BBBBBBBBBB";

    private static final String DEFAULT_MOTEXCLUE = "AAAAAAAAAA";
    private static final String UPDATED_MOTEXCLUE = "BBBBBBBBBB";

    @Autowired
    private MotcleRepository motcleRepository;

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

    private MockMvc restMotcleMockMvc;

    private Motcle motcle;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MotcleResource motcleResource = new MotcleResource(motcleRepository);
        this.restMotcleMockMvc = MockMvcBuilders.standaloneSetup(motcleResource)
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
    public static Motcle createEntity(EntityManager em) {
        Motcle motcle = new Motcle()
            .nom(DEFAULT_NOM)
            .motinclue(DEFAULT_MOTINCLUE)
            .motexclue(DEFAULT_MOTEXCLUE);
        return motcle;
    }

    @Before
    public void initTest() {
        motcle = createEntity(em);
    }

    @Test
    @Transactional
    public void createMotcle() throws Exception {
        int databaseSizeBeforeCreate = motcleRepository.findAll().size();

        // Create the Motcle
        restMotcleMockMvc.perform(post("/api/motcles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(motcle)))
            .andExpect(status().isCreated());

        // Validate the Motcle in the database
        List<Motcle> motcleList = motcleRepository.findAll();
        assertThat(motcleList).hasSize(databaseSizeBeforeCreate + 1);
        Motcle testMotcle = motcleList.get(motcleList.size() - 1);
        assertThat(testMotcle.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testMotcle.getMotinclue()).isEqualTo(DEFAULT_MOTINCLUE);
        assertThat(testMotcle.getMotexclue()).isEqualTo(DEFAULT_MOTEXCLUE);
    }

    @Test
    @Transactional
    public void createMotcleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = motcleRepository.findAll().size();

        // Create the Motcle with an existing ID
        motcle.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMotcleMockMvc.perform(post("/api/motcles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(motcle)))
            .andExpect(status().isBadRequest());

        // Validate the Motcle in the database
        List<Motcle> motcleList = motcleRepository.findAll();
        assertThat(motcleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = motcleRepository.findAll().size();
        // set the field null
        motcle.setNom(null);

        // Create the Motcle, which fails.

        restMotcleMockMvc.perform(post("/api/motcles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(motcle)))
            .andExpect(status().isBadRequest());

        List<Motcle> motcleList = motcleRepository.findAll();
        assertThat(motcleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMotinclueIsRequired() throws Exception {
        int databaseSizeBeforeTest = motcleRepository.findAll().size();
        // set the field null
        motcle.setMotinclue(null);

        // Create the Motcle, which fails.

        restMotcleMockMvc.perform(post("/api/motcles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(motcle)))
            .andExpect(status().isBadRequest());

        List<Motcle> motcleList = motcleRepository.findAll();
        assertThat(motcleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMotexclueIsRequired() throws Exception {
        int databaseSizeBeforeTest = motcleRepository.findAll().size();
        // set the field null
        motcle.setMotexclue(null);

        // Create the Motcle, which fails.

        restMotcleMockMvc.perform(post("/api/motcles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(motcle)))
            .andExpect(status().isBadRequest());

        List<Motcle> motcleList = motcleRepository.findAll();
        assertThat(motcleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMotcles() throws Exception {
        // Initialize the database
        motcleRepository.saveAndFlush(motcle);

        // Get all the motcleList
        restMotcleMockMvc.perform(get("/api/motcles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(motcle.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
            .andExpect(jsonPath("$.[*].motinclue").value(hasItem(DEFAULT_MOTINCLUE.toString())))
            .andExpect(jsonPath("$.[*].motexclue").value(hasItem(DEFAULT_MOTEXCLUE.toString())));
    }
    
    @Test
    @Transactional
    public void getMotcle() throws Exception {
        // Initialize the database
        motcleRepository.saveAndFlush(motcle);

        // Get the motcle
        restMotcleMockMvc.perform(get("/api/motcles/{id}", motcle.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(motcle.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()))
            .andExpect(jsonPath("$.motinclue").value(DEFAULT_MOTINCLUE.toString()))
            .andExpect(jsonPath("$.motexclue").value(DEFAULT_MOTEXCLUE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMotcle() throws Exception {
        // Get the motcle
        restMotcleMockMvc.perform(get("/api/motcles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMotcle() throws Exception {
        // Initialize the database
        motcleRepository.saveAndFlush(motcle);

        int databaseSizeBeforeUpdate = motcleRepository.findAll().size();

        // Update the motcle
        Motcle updatedMotcle = motcleRepository.findById(motcle.getId()).get();
        // Disconnect from session so that the updates on updatedMotcle are not directly saved in db
        em.detach(updatedMotcle);
        updatedMotcle
            .nom(UPDATED_NOM)
            .motinclue(UPDATED_MOTINCLUE)
            .motexclue(UPDATED_MOTEXCLUE);

        restMotcleMockMvc.perform(put("/api/motcles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMotcle)))
            .andExpect(status().isOk());

        // Validate the Motcle in the database
        List<Motcle> motcleList = motcleRepository.findAll();
        assertThat(motcleList).hasSize(databaseSizeBeforeUpdate);
        Motcle testMotcle = motcleList.get(motcleList.size() - 1);
        assertThat(testMotcle.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testMotcle.getMotinclue()).isEqualTo(UPDATED_MOTINCLUE);
        assertThat(testMotcle.getMotexclue()).isEqualTo(UPDATED_MOTEXCLUE);
    }

    @Test
    @Transactional
    public void updateNonExistingMotcle() throws Exception {
        int databaseSizeBeforeUpdate = motcleRepository.findAll().size();

        // Create the Motcle

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMotcleMockMvc.perform(put("/api/motcles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(motcle)))
            .andExpect(status().isBadRequest());

        // Validate the Motcle in the database
        List<Motcle> motcleList = motcleRepository.findAll();
        assertThat(motcleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMotcle() throws Exception {
        // Initialize the database
        motcleRepository.saveAndFlush(motcle);

        int databaseSizeBeforeDelete = motcleRepository.findAll().size();

        // Delete the motcle
        restMotcleMockMvc.perform(delete("/api/motcles/{id}", motcle.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Motcle> motcleList = motcleRepository.findAll();
        assertThat(motcleList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Motcle.class);
        Motcle motcle1 = new Motcle();
        motcle1.setId(1L);
        Motcle motcle2 = new Motcle();
        motcle2.setId(motcle1.getId());
        assertThat(motcle1).isEqualTo(motcle2);
        motcle2.setId(2L);
        assertThat(motcle1).isNotEqualTo(motcle2);
        motcle1.setId(null);
        assertThat(motcle1).isNotEqualTo(motcle2);
    }
}
