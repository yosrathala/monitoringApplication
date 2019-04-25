package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.ProjetApp;

import com.mycompany.myapp.domain.Recherche;
import com.mycompany.myapp.repository.RechercheRepository;
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
 * Test class for the RechercheResource REST controller.
 *
 * @see RechercheResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProjetApp.class)
public class RechercheResourceIntTest {

    private static final Integer DEFAULT_PERIODICITE = 1;
    private static final Integer UPDATED_PERIODICITE = 2;

    private static final Boolean DEFAULT_EMAILNOTIF = false;
    private static final Boolean UPDATED_EMAILNOTIF = true;

    private static final Boolean DEFAULT_PUSHNOTIF = false;
    private static final Boolean UPDATED_PUSHNOTIF = true;

    private static final Boolean DEFAULT_SMSNOTIF = false;
    private static final Boolean UPDATED_SMSNOTIF = true;

    @Autowired
    private RechercheRepository rechercheRepository;

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

    private MockMvc restRechercheMockMvc;

    private Recherche recherche;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RechercheResource rechercheResource = new RechercheResource(rechercheRepository);
        this.restRechercheMockMvc = MockMvcBuilders.standaloneSetup(rechercheResource)
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
    public static Recherche createEntity(EntityManager em) {
        Recherche recherche = new Recherche()
            .periodicite(DEFAULT_PERIODICITE)
            .emailnotif(DEFAULT_EMAILNOTIF)
            .pushnotif(DEFAULT_PUSHNOTIF)
            .smsnotif(DEFAULT_SMSNOTIF);
        return recherche;
    }

    @Before
    public void initTest() {
        recherche = createEntity(em);
    }

    @Test
    @Transactional
    public void createRecherche() throws Exception {
        int databaseSizeBeforeCreate = rechercheRepository.findAll().size();

        // Create the Recherche
        restRechercheMockMvc.perform(post("/api/recherches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recherche)))
            .andExpect(status().isCreated());

        // Validate the Recherche in the database
        List<Recherche> rechercheList = rechercheRepository.findAll();
        assertThat(rechercheList).hasSize(databaseSizeBeforeCreate + 1);
        Recherche testRecherche = rechercheList.get(rechercheList.size() - 1);
        assertThat(testRecherche.getPeriodicite()).isEqualTo(DEFAULT_PERIODICITE);
        assertThat(testRecherche.isEmailnotif()).isEqualTo(DEFAULT_EMAILNOTIF);
        assertThat(testRecherche.isPushnotif()).isEqualTo(DEFAULT_PUSHNOTIF);
        assertThat(testRecherche.isSmsnotif()).isEqualTo(DEFAULT_SMSNOTIF);
    }

    @Test
    @Transactional
    public void createRechercheWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = rechercheRepository.findAll().size();

        // Create the Recherche with an existing ID
        recherche.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRechercheMockMvc.perform(post("/api/recherches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recherche)))
            .andExpect(status().isBadRequest());

        // Validate the Recherche in the database
        List<Recherche> rechercheList = rechercheRepository.findAll();
        assertThat(rechercheList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkPeriodiciteIsRequired() throws Exception {
        int databaseSizeBeforeTest = rechercheRepository.findAll().size();
        // set the field null
        recherche.setPeriodicite(null);

        // Create the Recherche, which fails.

        restRechercheMockMvc.perform(post("/api/recherches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recherche)))
            .andExpect(status().isBadRequest());

        List<Recherche> rechercheList = rechercheRepository.findAll();
        assertThat(rechercheList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailnotifIsRequired() throws Exception {
        int databaseSizeBeforeTest = rechercheRepository.findAll().size();
        // set the field null
        recherche.setEmailnotif(null);

        // Create the Recherche, which fails.

        restRechercheMockMvc.perform(post("/api/recherches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recherche)))
            .andExpect(status().isBadRequest());

        List<Recherche> rechercheList = rechercheRepository.findAll();
        assertThat(rechercheList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPushnotifIsRequired() throws Exception {
        int databaseSizeBeforeTest = rechercheRepository.findAll().size();
        // set the field null
        recherche.setPushnotif(null);

        // Create the Recherche, which fails.

        restRechercheMockMvc.perform(post("/api/recherches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recherche)))
            .andExpect(status().isBadRequest());

        List<Recherche> rechercheList = rechercheRepository.findAll();
        assertThat(rechercheList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSmsnotifIsRequired() throws Exception {
        int databaseSizeBeforeTest = rechercheRepository.findAll().size();
        // set the field null
        recherche.setSmsnotif(null);

        // Create the Recherche, which fails.

        restRechercheMockMvc.perform(post("/api/recherches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recherche)))
            .andExpect(status().isBadRequest());

        List<Recherche> rechercheList = rechercheRepository.findAll();
        assertThat(rechercheList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRecherches() throws Exception {
        // Initialize the database
        rechercheRepository.saveAndFlush(recherche);

        // Get all the rechercheList
        restRechercheMockMvc.perform(get("/api/recherches?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(recherche.getId().intValue())))
            .andExpect(jsonPath("$.[*].periodicite").value(hasItem(DEFAULT_PERIODICITE)))
            .andExpect(jsonPath("$.[*].emailnotif").value(hasItem(DEFAULT_EMAILNOTIF.booleanValue())))
            .andExpect(jsonPath("$.[*].pushnotif").value(hasItem(DEFAULT_PUSHNOTIF.booleanValue())))
            .andExpect(jsonPath("$.[*].smsnotif").value(hasItem(DEFAULT_SMSNOTIF.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getRecherche() throws Exception {
        // Initialize the database
        rechercheRepository.saveAndFlush(recherche);

        // Get the recherche
        restRechercheMockMvc.perform(get("/api/recherches/{id}", recherche.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(recherche.getId().intValue()))
            .andExpect(jsonPath("$.periodicite").value(DEFAULT_PERIODICITE))
            .andExpect(jsonPath("$.emailnotif").value(DEFAULT_EMAILNOTIF.booleanValue()))
            .andExpect(jsonPath("$.pushnotif").value(DEFAULT_PUSHNOTIF.booleanValue()))
            .andExpect(jsonPath("$.smsnotif").value(DEFAULT_SMSNOTIF.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingRecherche() throws Exception {
        // Get the recherche
        restRechercheMockMvc.perform(get("/api/recherches/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRecherche() throws Exception {
        // Initialize the database
        rechercheRepository.saveAndFlush(recherche);

        int databaseSizeBeforeUpdate = rechercheRepository.findAll().size();

        // Update the recherche
        Recherche updatedRecherche = rechercheRepository.findById(recherche.getId()).get();
        // Disconnect from session so that the updates on updatedRecherche are not directly saved in db
        em.detach(updatedRecherche);
        updatedRecherche
            .periodicite(UPDATED_PERIODICITE)
            .emailnotif(UPDATED_EMAILNOTIF)
            .pushnotif(UPDATED_PUSHNOTIF)
            .smsnotif(UPDATED_SMSNOTIF);

        restRechercheMockMvc.perform(put("/api/recherches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRecherche)))
            .andExpect(status().isOk());

        // Validate the Recherche in the database
        List<Recherche> rechercheList = rechercheRepository.findAll();
        assertThat(rechercheList).hasSize(databaseSizeBeforeUpdate);
        Recherche testRecherche = rechercheList.get(rechercheList.size() - 1);
        assertThat(testRecherche.getPeriodicite()).isEqualTo(UPDATED_PERIODICITE);
        assertThat(testRecherche.isEmailnotif()).isEqualTo(UPDATED_EMAILNOTIF);
        assertThat(testRecherche.isPushnotif()).isEqualTo(UPDATED_PUSHNOTIF);
        assertThat(testRecherche.isSmsnotif()).isEqualTo(UPDATED_SMSNOTIF);
    }

    @Test
    @Transactional
    public void updateNonExistingRecherche() throws Exception {
        int databaseSizeBeforeUpdate = rechercheRepository.findAll().size();

        // Create the Recherche

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRechercheMockMvc.perform(put("/api/recherches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recherche)))
            .andExpect(status().isBadRequest());

        // Validate the Recherche in the database
        List<Recherche> rechercheList = rechercheRepository.findAll();
        assertThat(rechercheList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRecherche() throws Exception {
        // Initialize the database
        rechercheRepository.saveAndFlush(recherche);

        int databaseSizeBeforeDelete = rechercheRepository.findAll().size();

        // Delete the recherche
        restRechercheMockMvc.perform(delete("/api/recherches/{id}", recherche.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Recherche> rechercheList = rechercheRepository.findAll();
        assertThat(rechercheList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Recherche.class);
        Recherche recherche1 = new Recherche();
        recherche1.setId(1L);
        Recherche recherche2 = new Recherche();
        recherche2.setId(recherche1.getId());
        assertThat(recherche1).isEqualTo(recherche2);
        recherche2.setId(2L);
        assertThat(recherche1).isNotEqualTo(recherche2);
        recherche1.setId(null);
        assertThat(recherche1).isNotEqualTo(recherche2);
    }
}
