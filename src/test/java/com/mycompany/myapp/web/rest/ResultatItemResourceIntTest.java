package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.ProjetApp;

import com.mycompany.myapp.domain.ResultatItem;
import com.mycompany.myapp.repository.ResultatItemRepository;
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
 * Test class for the ResultatItemResource REST controller.
 *
 * @see ResultatItemResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProjetApp.class)
public class ResultatItemResourceIntTest {

    private static final String DEFAULT_CONTENU = "AAAAAAAAAA";
    private static final String UPDATED_CONTENU = "BBBBBBBBBB";

    private static final Boolean DEFAULT_STATU = false;
    private static final Boolean UPDATED_STATU = true;

    private static final Boolean DEFAULT_NOTE = false;
    private static final Boolean UPDATED_NOTE = true;

    private static final String DEFAULT_TITRE = "AAAAAAAAAA";
    private static final String UPDATED_TITRE = "BBBBBBBBBB";

    private static final String DEFAULT_DATE = "AAAAAAAAAA";
    private static final String UPDATED_DATE = "BBBBBBBBBB";

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    private static final String DEFAULT_POST_ID = "AAAAAAAAAA";
    private static final String UPDATED_POST_ID = "BBBBBBBBBB";

    @Autowired
    private ResultatItemRepository resultatItemRepository;

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

    private MockMvc restResultatItemMockMvc;

    private ResultatItem resultatItem;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ResultatItemResource resultatItemResource = new ResultatItemResource(resultatItemRepository);
        this.restResultatItemMockMvc = MockMvcBuilders.standaloneSetup(resultatItemResource)
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
    public static ResultatItem createEntity(EntityManager em) {
        ResultatItem resultatItem = new ResultatItem()
            .contenu(DEFAULT_CONTENU)
            .statu(DEFAULT_STATU)
            .note(DEFAULT_NOTE)
            .titre(DEFAULT_TITRE)
            .date(DEFAULT_DATE)
            .url(DEFAULT_URL)
            .postId(DEFAULT_POST_ID);
        return resultatItem;
    }

    @Before
    public void initTest() {
        resultatItem = createEntity(em);
    }

    @Test
    @Transactional
    public void createResultatItem() throws Exception {
        int databaseSizeBeforeCreate = resultatItemRepository.findAll().size();

        // Create the ResultatItem
        restResultatItemMockMvc.perform(post("/api/resultat-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resultatItem)))
            .andExpect(status().isCreated());

        // Validate the ResultatItem in the database
        List<ResultatItem> resultatItemList = resultatItemRepository.findAll();
        assertThat(resultatItemList).hasSize(databaseSizeBeforeCreate + 1);
        ResultatItem testResultatItem = resultatItemList.get(resultatItemList.size() - 1);
        assertThat(testResultatItem.getContenu()).isEqualTo(DEFAULT_CONTENU);
        assertThat(testResultatItem.isStatu()).isEqualTo(DEFAULT_STATU);
        assertThat(testResultatItem.isNote()).isEqualTo(DEFAULT_NOTE);
        assertThat(testResultatItem.getTitre()).isEqualTo(DEFAULT_TITRE);
        assertThat(testResultatItem.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testResultatItem.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testResultatItem.getPostId()).isEqualTo(DEFAULT_POST_ID);
    }

    @Test
    @Transactional
    public void createResultatItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = resultatItemRepository.findAll().size();

        // Create the ResultatItem with an existing ID
        resultatItem.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restResultatItemMockMvc.perform(post("/api/resultat-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resultatItem)))
            .andExpect(status().isBadRequest());

        // Validate the ResultatItem in the database
        List<ResultatItem> resultatItemList = resultatItemRepository.findAll();
        assertThat(resultatItemList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkContenuIsRequired() throws Exception {
        int databaseSizeBeforeTest = resultatItemRepository.findAll().size();
        // set the field null
        resultatItem.setContenu(null);

        // Create the ResultatItem, which fails.

        restResultatItemMockMvc.perform(post("/api/resultat-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resultatItem)))
            .andExpect(status().isBadRequest());

        List<ResultatItem> resultatItemList = resultatItemRepository.findAll();
        assertThat(resultatItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllResultatItems() throws Exception {
        // Initialize the database
        resultatItemRepository.saveAndFlush(resultatItem);

        // Get all the resultatItemList
        restResultatItemMockMvc.perform(get("/api/resultat-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(resultatItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].contenu").value(hasItem(DEFAULT_CONTENU.toString())))
            .andExpect(jsonPath("$.[*].statu").value(hasItem(DEFAULT_STATU.booleanValue())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE.booleanValue())))
            .andExpect(jsonPath("$.[*].titre").value(hasItem(DEFAULT_TITRE.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL.toString())))
            .andExpect(jsonPath("$.[*].postId").value(hasItem(DEFAULT_POST_ID.toString())));
    }
    
    @Test
    @Transactional
    public void getResultatItem() throws Exception {
        // Initialize the database
        resultatItemRepository.saveAndFlush(resultatItem);

        // Get the resultatItem
        restResultatItemMockMvc.perform(get("/api/resultat-items/{id}", resultatItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(resultatItem.getId().intValue()))
            .andExpect(jsonPath("$.contenu").value(DEFAULT_CONTENU.toString()))
            .andExpect(jsonPath("$.statu").value(DEFAULT_STATU.booleanValue()))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE.booleanValue()))
            .andExpect(jsonPath("$.titre").value(DEFAULT_TITRE.toString()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL.toString()))
            .andExpect(jsonPath("$.postId").value(DEFAULT_POST_ID.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingResultatItem() throws Exception {
        // Get the resultatItem
        restResultatItemMockMvc.perform(get("/api/resultat-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateResultatItem() throws Exception {
        // Initialize the database
        resultatItemRepository.saveAndFlush(resultatItem);

        int databaseSizeBeforeUpdate = resultatItemRepository.findAll().size();

        // Update the resultatItem
        ResultatItem updatedResultatItem = resultatItemRepository.findById(resultatItem.getId()).get();
        // Disconnect from session so that the updates on updatedResultatItem are not directly saved in db
        em.detach(updatedResultatItem);
        updatedResultatItem
            .contenu(UPDATED_CONTENU)
            .statu(UPDATED_STATU)
            .note(UPDATED_NOTE)
            .titre(UPDATED_TITRE)
            .date(UPDATED_DATE)
            .url(UPDATED_URL)
            .postId(UPDATED_POST_ID);

        restResultatItemMockMvc.perform(put("/api/resultat-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedResultatItem)))
            .andExpect(status().isOk());

        // Validate the ResultatItem in the database
        List<ResultatItem> resultatItemList = resultatItemRepository.findAll();
        assertThat(resultatItemList).hasSize(databaseSizeBeforeUpdate);
        ResultatItem testResultatItem = resultatItemList.get(resultatItemList.size() - 1);
        assertThat(testResultatItem.getContenu()).isEqualTo(UPDATED_CONTENU);
        assertThat(testResultatItem.isStatu()).isEqualTo(UPDATED_STATU);
        assertThat(testResultatItem.isNote()).isEqualTo(UPDATED_NOTE);
        assertThat(testResultatItem.getTitre()).isEqualTo(UPDATED_TITRE);
        assertThat(testResultatItem.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testResultatItem.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testResultatItem.getPostId()).isEqualTo(UPDATED_POST_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingResultatItem() throws Exception {
        int databaseSizeBeforeUpdate = resultatItemRepository.findAll().size();

        // Create the ResultatItem

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResultatItemMockMvc.perform(put("/api/resultat-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resultatItem)))
            .andExpect(status().isBadRequest());

        // Validate the ResultatItem in the database
        List<ResultatItem> resultatItemList = resultatItemRepository.findAll();
        assertThat(resultatItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteResultatItem() throws Exception {
        // Initialize the database
        resultatItemRepository.saveAndFlush(resultatItem);

        int databaseSizeBeforeDelete = resultatItemRepository.findAll().size();

        // Delete the resultatItem
        restResultatItemMockMvc.perform(delete("/api/resultat-items/{id}", resultatItem.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ResultatItem> resultatItemList = resultatItemRepository.findAll();
        assertThat(resultatItemList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ResultatItem.class);
        ResultatItem resultatItem1 = new ResultatItem();
        resultatItem1.setId(1L);
        ResultatItem resultatItem2 = new ResultatItem();
        resultatItem2.setId(resultatItem1.getId());
        assertThat(resultatItem1).isEqualTo(resultatItem2);
        resultatItem2.setId(2L);
        assertThat(resultatItem1).isNotEqualTo(resultatItem2);
        resultatItem1.setId(null);
        assertThat(resultatItem1).isNotEqualTo(resultatItem2);
    }
}
