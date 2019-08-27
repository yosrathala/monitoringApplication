package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import com.mycompany.myapp.domain.Recherche;
import com.mycompany.myapp.domain.Source;
import com.mycompany.myapp.repository.SourceRepository;
import com.mycompany.myapp.service.SourceQueryService;
import com.mycompany.myapp.service.SourceService;
import com.mycompany.myapp.web.rest.errors.ExceptionTranslator;

/**
 * Test class for the SourceResource REST controller.
 *
 * @see SourceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProjetApp.class)
public class SourceResourceIntTest {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_LOGIN = "AAAAAAAAAA";
    private static final String UPDATED_LOGIN = "BBBBBBBBBB";

    private static final String DEFAULT_MOT_PASSE = "AAAAAAAAAA";
    private static final String UPDATED_MOT_PASSE = "BBBBBBBBBB";

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    private static final String DEFAULT_KEY = "AAAAAAAAAA";
    private static final String UPDATED_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_DATA_HANDLER = "AAAAAAAAAA";
    private static final String UPDATED_DATA_HANDLER = "BBBBBBBBBB";

    @Autowired
    private SourceRepository sourceRepository;

    @Autowired
    private SourceService sourceService;

    @Autowired
    private SourceQueryService sourceQueryService;

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

    private MockMvc restSourceMockMvc;

    private Source source;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SourceResource sourceResource = new SourceResource(sourceService, sourceQueryService);
        this.restSourceMockMvc = MockMvcBuilders.standaloneSetup(sourceResource)
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
    public static Source createEntity(EntityManager em) {
        Source source = new Source()
            .nom(DEFAULT_NOM)
            .login(DEFAULT_LOGIN)
            .motPasse(DEFAULT_MOT_PASSE)
            .url(DEFAULT_URL)
            .key(DEFAULT_KEY)
            .dataHandler(DEFAULT_DATA_HANDLER);
        return source;
    }

    @Before
    public void initTest() {
        source = createEntity(em);
    }

    @Test
    @Transactional
    public void createSource() throws Exception {
        int databaseSizeBeforeCreate = sourceRepository.findAll().size();

        // Create the Source
        restSourceMockMvc.perform(post("/api/sources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(source)))
            .andExpect(status().isCreated());

        // Validate the Source in the database
        List<Source> sourceList = sourceRepository.findAll();
        assertThat(sourceList).hasSize(databaseSizeBeforeCreate + 1);
        Source testSource = sourceList.get(sourceList.size() - 1);
        assertThat(testSource.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testSource.getLogin()).isEqualTo(DEFAULT_LOGIN);
        assertThat(testSource.getMotPasse()).isEqualTo(DEFAULT_MOT_PASSE);
        assertThat(testSource.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testSource.getKey()).isEqualTo(DEFAULT_KEY);
        assertThat(testSource.getDataHandler()).isEqualTo(DEFAULT_DATA_HANDLER);
    }

    @Test
    @Transactional
    public void createSourceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sourceRepository.findAll().size();

        // Create the Source with an existing ID
        source.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSourceMockMvc.perform(post("/api/sources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(source)))
            .andExpect(status().isBadRequest());

        // Validate the Source in the database
        List<Source> sourceList = sourceRepository.findAll();
        assertThat(sourceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = sourceRepository.findAll().size();
        // set the field null
        source.setNom(null);

        // Create the Source, which fails.

        restSourceMockMvc.perform(post("/api/sources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(source)))
            .andExpect(status().isBadRequest());

        List<Source> sourceList = sourceRepository.findAll();
        assertThat(sourceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLoginIsRequired() throws Exception {
        int databaseSizeBeforeTest = sourceRepository.findAll().size();
        // set the field null
        source.setLogin(null);

        // Create the Source, which fails.

        restSourceMockMvc.perform(post("/api/sources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(source)))
            .andExpect(status().isBadRequest());

        List<Source> sourceList = sourceRepository.findAll();
        assertThat(sourceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMotPasseIsRequired() throws Exception {
        int databaseSizeBeforeTest = sourceRepository.findAll().size();
        // set the field null
        source.setMotPasse(null);

        // Create the Source, which fails.

        restSourceMockMvc.perform(post("/api/sources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(source)))
            .andExpect(status().isBadRequest());

        List<Source> sourceList = sourceRepository.findAll();
        assertThat(sourceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUrlIsRequired() throws Exception {
        int databaseSizeBeforeTest = sourceRepository.findAll().size();
        // set the field null
        source.setUrl(null);

        // Create the Source, which fails.

        restSourceMockMvc.perform(post("/api/sources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(source)))
            .andExpect(status().isBadRequest());

        List<Source> sourceList = sourceRepository.findAll();
        assertThat(sourceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkKeyIsRequired() throws Exception {
        int databaseSizeBeforeTest = sourceRepository.findAll().size();
        // set the field null
        source.setKey(null);

        // Create the Source, which fails.

        restSourceMockMvc.perform(post("/api/sources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(source)))
            .andExpect(status().isBadRequest());

        List<Source> sourceList = sourceRepository.findAll();
        assertThat(sourceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDataHandlerIsRequired() throws Exception {
        int databaseSizeBeforeTest = sourceRepository.findAll().size();
        // set the field null
        source.setDataHandler(null);

        // Create the Source, which fails.

        restSourceMockMvc.perform(post("/api/sources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(source)))
            .andExpect(status().isBadRequest());

        List<Source> sourceList = sourceRepository.findAll();
        assertThat(sourceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSources() throws Exception {
        // Initialize the database
        sourceRepository.saveAndFlush(source);

        // Get all the sourceList
        restSourceMockMvc.perform(get("/api/sources?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(source.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
            .andExpect(jsonPath("$.[*].login").value(hasItem(DEFAULT_LOGIN.toString())))
            .andExpect(jsonPath("$.[*].motPasse").value(hasItem(DEFAULT_MOT_PASSE.toString())))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL.toString())))
            .andExpect(jsonPath("$.[*].key").value(hasItem(DEFAULT_KEY.toString())))
            .andExpect(jsonPath("$.[*].dataHandler").value(hasItem(DEFAULT_DATA_HANDLER.toString())));
    }
    
    @Test
    @Transactional
    public void getSource() throws Exception {
        // Initialize the database
        sourceRepository.saveAndFlush(source);

        // Get the source
        restSourceMockMvc.perform(get("/api/sources/{id}", source.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(source.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()))
            .andExpect(jsonPath("$.login").value(DEFAULT_LOGIN.toString()))
            .andExpect(jsonPath("$.motPasse").value(DEFAULT_MOT_PASSE.toString()))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL.toString()))
            .andExpect(jsonPath("$.key").value(DEFAULT_KEY.toString()))
            .andExpect(jsonPath("$.dataHandler").value(DEFAULT_DATA_HANDLER.toString()));
    }

    @Test
    @Transactional
    public void getAllSourcesByNomIsEqualToSomething() throws Exception {
        // Initialize the database
        sourceRepository.saveAndFlush(source);

        // Get all the sourceList where nom equals to DEFAULT_NOM
        defaultSourceShouldBeFound("nom.equals=" + DEFAULT_NOM);

        // Get all the sourceList where nom equals to UPDATED_NOM
        defaultSourceShouldNotBeFound("nom.equals=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllSourcesByNomIsInShouldWork() throws Exception {
        // Initialize the database
        sourceRepository.saveAndFlush(source);

        // Get all the sourceList where nom in DEFAULT_NOM or UPDATED_NOM
        defaultSourceShouldBeFound("nom.in=" + DEFAULT_NOM + "," + UPDATED_NOM);

        // Get all the sourceList where nom equals to UPDATED_NOM
        defaultSourceShouldNotBeFound("nom.in=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllSourcesByNomIsNullOrNotNull() throws Exception {
        // Initialize the database
        sourceRepository.saveAndFlush(source);

        // Get all the sourceList where nom is not null
        defaultSourceShouldBeFound("nom.specified=true");

        // Get all the sourceList where nom is null
        defaultSourceShouldNotBeFound("nom.specified=false");
    }

    @Test
    @Transactional
    public void getAllSourcesByLoginIsEqualToSomething() throws Exception {
        // Initialize the database
        sourceRepository.saveAndFlush(source);

        // Get all the sourceList where login equals to DEFAULT_LOGIN
        defaultSourceShouldBeFound("login.equals=" + DEFAULT_LOGIN);

        // Get all the sourceList where login equals to UPDATED_LOGIN
        defaultSourceShouldNotBeFound("login.equals=" + UPDATED_LOGIN);
    }

    @Test
    @Transactional
    public void getAllSourcesByLoginIsInShouldWork() throws Exception {
        // Initialize the database
        sourceRepository.saveAndFlush(source);

        // Get all the sourceList where login in DEFAULT_LOGIN or UPDATED_LOGIN
        defaultSourceShouldBeFound("login.in=" + DEFAULT_LOGIN + "," + UPDATED_LOGIN);

        // Get all the sourceList where login equals to UPDATED_LOGIN
        defaultSourceShouldNotBeFound("login.in=" + UPDATED_LOGIN);
    }

    @Test
    @Transactional
    public void getAllSourcesByLoginIsNullOrNotNull() throws Exception {
        // Initialize the database
        sourceRepository.saveAndFlush(source);

        // Get all the sourceList where login is not null
        defaultSourceShouldBeFound("login.specified=true");

        // Get all the sourceList where login is null
        defaultSourceShouldNotBeFound("login.specified=false");
    }

    @Test
    @Transactional
    public void getAllSourcesByMotPasseIsEqualToSomething() throws Exception {
        // Initialize the database
        sourceRepository.saveAndFlush(source);

        // Get all the sourceList where motPasse equals to DEFAULT_MOT_PASSE
        defaultSourceShouldBeFound("motPasse.equals=" + DEFAULT_MOT_PASSE);

        // Get all the sourceList where motPasse equals to UPDATED_MOT_PASSE
        defaultSourceShouldNotBeFound("motPasse.equals=" + UPDATED_MOT_PASSE);
    }

    @Test
    @Transactional
    public void getAllSourcesByMotPasseIsInShouldWork() throws Exception {
        // Initialize the database
        sourceRepository.saveAndFlush(source);

        // Get all the sourceList where motPasse in DEFAULT_MOT_PASSE or UPDATED_MOT_PASSE
        defaultSourceShouldBeFound("motPasse.in=" + DEFAULT_MOT_PASSE + "," + UPDATED_MOT_PASSE);

        // Get all the sourceList where motPasse equals to UPDATED_MOT_PASSE
        defaultSourceShouldNotBeFound("motPasse.in=" + UPDATED_MOT_PASSE);
    }

    @Test
    @Transactional
    public void getAllSourcesByMotPasseIsNullOrNotNull() throws Exception {
        // Initialize the database
        sourceRepository.saveAndFlush(source);

        // Get all the sourceList where motPasse is not null
        defaultSourceShouldBeFound("motPasse.specified=true");

        // Get all the sourceList where motPasse is null
        defaultSourceShouldNotBeFound("motPasse.specified=false");
    }

    @Test
    @Transactional
    public void getAllSourcesByUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        sourceRepository.saveAndFlush(source);

        // Get all the sourceList where url equals to DEFAULT_URL
        defaultSourceShouldBeFound("url.equals=" + DEFAULT_URL);

        // Get all the sourceList where url equals to UPDATED_URL
        defaultSourceShouldNotBeFound("url.equals=" + UPDATED_URL);
    }

    @Test
    @Transactional
    public void getAllSourcesByUrlIsInShouldWork() throws Exception {
        // Initialize the database
        sourceRepository.saveAndFlush(source);

        // Get all the sourceList where url in DEFAULT_URL or UPDATED_URL
        defaultSourceShouldBeFound("url.in=" + DEFAULT_URL + "," + UPDATED_URL);

        // Get all the sourceList where url equals to UPDATED_URL
        defaultSourceShouldNotBeFound("url.in=" + UPDATED_URL);
    }

    @Test
    @Transactional
    public void getAllSourcesByUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        sourceRepository.saveAndFlush(source);

        // Get all the sourceList where url is not null
        defaultSourceShouldBeFound("url.specified=true");

        // Get all the sourceList where url is null
        defaultSourceShouldNotBeFound("url.specified=false");
    }

    @Test
    @Transactional
    public void getAllSourcesByKeyIsEqualToSomething() throws Exception {
        // Initialize the database
        sourceRepository.saveAndFlush(source);

        // Get all the sourceList where key equals to DEFAULT_KEY
        defaultSourceShouldBeFound("key.equals=" + DEFAULT_KEY);

        // Get all the sourceList where key equals to UPDATED_KEY
        defaultSourceShouldNotBeFound("key.equals=" + UPDATED_KEY);
    }

    @Test
    @Transactional
    public void getAllSourcesByKeyIsInShouldWork() throws Exception {
        // Initialize the database
        sourceRepository.saveAndFlush(source);

        // Get all the sourceList where key in DEFAULT_KEY or UPDATED_KEY
        defaultSourceShouldBeFound("key.in=" + DEFAULT_KEY + "," + UPDATED_KEY);

        // Get all the sourceList where key equals to UPDATED_KEY
        defaultSourceShouldNotBeFound("key.in=" + UPDATED_KEY);
    }

    @Test
    @Transactional
    public void getAllSourcesByKeyIsNullOrNotNull() throws Exception {
        // Initialize the database
        sourceRepository.saveAndFlush(source);

        // Get all the sourceList where key is not null
        defaultSourceShouldBeFound("key.specified=true");

        // Get all the sourceList where key is null
        defaultSourceShouldNotBeFound("key.specified=false");
    }

    @Test
    @Transactional
    public void getAllSourcesByDataHandlerIsEqualToSomething() throws Exception {
        // Initialize the database
        sourceRepository.saveAndFlush(source);

        // Get all the sourceList where dataHandler equals to DEFAULT_DATA_HANDLER
        defaultSourceShouldBeFound("dataHandler.equals=" + DEFAULT_DATA_HANDLER);

        // Get all the sourceList where dataHandler equals to UPDATED_DATA_HANDLER
        defaultSourceShouldNotBeFound("dataHandler.equals=" + UPDATED_DATA_HANDLER);
    }

    @Test
    @Transactional
    public void getAllSourcesByDataHandlerIsInShouldWork() throws Exception {
        // Initialize the database
        sourceRepository.saveAndFlush(source);

        // Get all the sourceList where dataHandler in DEFAULT_DATA_HANDLER or UPDATED_DATA_HANDLER
        defaultSourceShouldBeFound("dataHandler.in=" + DEFAULT_DATA_HANDLER + "," + UPDATED_DATA_HANDLER);

        // Get all the sourceList where dataHandler equals to UPDATED_DATA_HANDLER
        defaultSourceShouldNotBeFound("dataHandler.in=" + UPDATED_DATA_HANDLER);
    }

    @Test
    @Transactional
    public void getAllSourcesByDataHandlerIsNullOrNotNull() throws Exception {
        // Initialize the database
        sourceRepository.saveAndFlush(source);

        // Get all the sourceList where dataHandler is not null
        defaultSourceShouldBeFound("dataHandler.specified=true");

        // Get all the sourceList where dataHandler is null
        defaultSourceShouldNotBeFound("dataHandler.specified=false");
    }

    @Test
    @Transactional
    public void getAllSourcesByRechercheIsEqualToSomething() throws Exception {
        // Initialize the database
        Recherche recherche = RechercheResourceIntTest.createEntity(em);
        em.persist(recherche);
        em.flush();
        source.addRecherche(recherche);
        sourceRepository.saveAndFlush(source);
        Long rechercheId = recherche.getId();

        // Get all the sourceList where recherche equals to rechercheId
        defaultSourceShouldBeFound("rechercheId.equals=" + rechercheId);

        // Get all the sourceList where recherche equals to rechercheId + 1
        defaultSourceShouldNotBeFound("rechercheId.equals=" + (rechercheId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultSourceShouldBeFound(String filter) throws Exception {
        restSourceMockMvc.perform(get("/api/sources?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(source.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].login").value(hasItem(DEFAULT_LOGIN)))
            .andExpect(jsonPath("$.[*].motPasse").value(hasItem(DEFAULT_MOT_PASSE)))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL)))
            .andExpect(jsonPath("$.[*].key").value(hasItem(DEFAULT_KEY)))
            .andExpect(jsonPath("$.[*].dataHandler").value(hasItem(DEFAULT_DATA_HANDLER)));

        // Check, that the count call also returns 1
        restSourceMockMvc.perform(get("/api/sources/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultSourceShouldNotBeFound(String filter) throws Exception {
        restSourceMockMvc.perform(get("/api/sources?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSourceMockMvc.perform(get("/api/sources/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingSource() throws Exception {
        // Get the source
        restSourceMockMvc.perform(get("/api/sources/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSource() throws Exception {
        // Initialize the database
        sourceService.save(source);

        int databaseSizeBeforeUpdate = sourceRepository.findAll().size();

        // Update the source
        Source updatedSource = sourceRepository.findById(source.getId()).get();
        // Disconnect from session so that the updates on updatedSource are not directly saved in db
        em.detach(updatedSource);
        updatedSource
            .nom(UPDATED_NOM)
            .login(UPDATED_LOGIN)
            .motPasse(UPDATED_MOT_PASSE)
            .url(UPDATED_URL)
            .key(UPDATED_KEY)
            .dataHandler(UPDATED_DATA_HANDLER);

        restSourceMockMvc.perform(put("/api/sources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSource)))
            .andExpect(status().isOk());

        // Validate the Source in the database
        List<Source> sourceList = sourceRepository.findAll();
        assertThat(sourceList).hasSize(databaseSizeBeforeUpdate);
        Source testSource = sourceList.get(sourceList.size() - 1);
        assertThat(testSource.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testSource.getLogin()).isEqualTo(UPDATED_LOGIN);
        assertThat(testSource.getMotPasse()).isEqualTo(UPDATED_MOT_PASSE);
        assertThat(testSource.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testSource.getKey()).isEqualTo(UPDATED_KEY);
        assertThat(testSource.getDataHandler()).isEqualTo(UPDATED_DATA_HANDLER);
    }

    @Test
    @Transactional
    public void updateNonExistingSource() throws Exception {
        int databaseSizeBeforeUpdate = sourceRepository.findAll().size();

        // Create the Source

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSourceMockMvc.perform(put("/api/sources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(source)))
            .andExpect(status().isBadRequest());

        // Validate the Source in the database
        List<Source> sourceList = sourceRepository.findAll();
        assertThat(sourceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSource() throws Exception {
        // Initialize the database
        sourceService.save(source);

        int databaseSizeBeforeDelete = sourceRepository.findAll().size();

        // Delete the source
        restSourceMockMvc.perform(delete("/api/sources/{id}", source.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Source> sourceList = sourceRepository.findAll();
        assertThat(sourceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Source.class);
        Source source1 = new Source();
        source1.setId(1L);
        Source source2 = new Source();
        source2.setId(source1.getId());
        assertThat(source1).isEqualTo(source2);
        source2.setId(2L);
        assertThat(source1).isNotEqualTo(source2);
        source1.setId(null);
        assertThat(source1).isNotEqualTo(source2);
    }
}
