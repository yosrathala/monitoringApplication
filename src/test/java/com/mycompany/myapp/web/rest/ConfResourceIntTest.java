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
import com.mycompany.myapp.domain.Conf;
import com.mycompany.myapp.repository.ConfRepository;
import com.mycompany.myapp.web.rest.errors.ExceptionTranslator;

/**
 * Test class for the ConfResource REST controller.
 *
 * @see ConfResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProjetApp.class)
public class ConfResourceIntTest {

    private static final Integer DEFAULT_HEUREDEBUT = 1;
    private static final Integer UPDATED_HEUREDEBUT = 2;

    private static final Integer DEFAULT_HEUREFIN = 1;
    private static final Integer UPDATED_HEUREFIN = 2;

    private static final String DEFAULT_SMTPHOST = "AAAAAAAAAA";
    private static final String UPDATED_SMTPHOST = "BBBBBBBBBB";

    private static final String DEFAULT_SMTPPASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_SMTPPASSWORD = "BBBBBBBBBB";

    private static final String DEFAULT_PUSHSERVER = "AAAAAAAAAA";
    private static final String UPDATED_PUSHSERVER = "BBBBBBBBBB";

    private static final String DEFAULT_SMTPUSER = "AAAAAAAAAA";
    private static final String UPDATED_SMTPUSER = "BBBBBBBBBB";

    private static final String DEFAULT_SMSGATEWAY = "AAAAAAAAAA";
    private static final String UPDATED_SMSGATEWAY = "BBBBBBBBBB";

    @Autowired
    private ConfRepository confRepository;

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

    private MockMvc restConfMockMvc;

    private Conf conf;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ConfResource confResource = new ConfResource(confRepository);
        this.restConfMockMvc = MockMvcBuilders.standaloneSetup(confResource)
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
    public static Conf createEntity(EntityManager em) {
        Conf conf = new Conf()
            .heuredebut(DEFAULT_HEUREDEBUT)
            .heurefin(DEFAULT_HEUREFIN)
            .smtphost(DEFAULT_SMTPHOST)
            .smtppassword(DEFAULT_SMTPPASSWORD)
            .pushserver(DEFAULT_PUSHSERVER)
            .smtpuser(DEFAULT_SMTPUSER)
            .smsgateway(DEFAULT_SMSGATEWAY);
        return conf;
    }

    @Before
    public void initTest() {
        conf = createEntity(em);
    }

    @Test
    @Transactional
    public void createConf() throws Exception {
        int databaseSizeBeforeCreate = confRepository.findAll().size();

        // Create the Conf
        restConfMockMvc.perform(post("/api/confs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(conf)))
            .andExpect(status().isCreated());

        // Validate the Conf in the database
        List<Conf> confList = confRepository.findAll();
        assertThat(confList).hasSize(databaseSizeBeforeCreate + 1);
        Conf testConf = confList.get(confList.size() - 1);
        assertThat(testConf.getHeuredebut()).isEqualTo(DEFAULT_HEUREDEBUT);
        assertThat(testConf.getHeurefin()).isEqualTo(DEFAULT_HEUREFIN);
        assertThat(testConf.getSmtphost()).isEqualTo(DEFAULT_SMTPHOST);
        assertThat(testConf.getSmtppassword()).isEqualTo(DEFAULT_SMTPPASSWORD);
        assertThat(testConf.getPushserver()).isEqualTo(DEFAULT_PUSHSERVER);
        assertThat(testConf.getSmtpuser()).isEqualTo(DEFAULT_SMTPUSER);
        assertThat(testConf.getSmsgateway()).isEqualTo(DEFAULT_SMSGATEWAY);
    }

    @Test
    @Transactional
    public void createConfWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = confRepository.findAll().size();

        // Create the Conf with an existing ID
        conf.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restConfMockMvc.perform(post("/api/confs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(conf)))
            .andExpect(status().isBadRequest());

        // Validate the Conf in the database
        List<Conf> confList = confRepository.findAll();
        assertThat(confList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkHeuredebutIsRequired() throws Exception {
        int databaseSizeBeforeTest = confRepository.findAll().size();
        // set the field null
        conf.setHeuredebut(null);

        // Create the Conf, which fails.

        restConfMockMvc.perform(post("/api/confs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(conf)))
            .andExpect(status().isBadRequest());

        List<Conf> confList = confRepository.findAll();
        assertThat(confList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHeurefinIsRequired() throws Exception {
        int databaseSizeBeforeTest = confRepository.findAll().size();
        // set the field null
        conf.setHeurefin(null);

        // Create the Conf, which fails.

        restConfMockMvc.perform(post("/api/confs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(conf)))
            .andExpect(status().isBadRequest());

        List<Conf> confList = confRepository.findAll();
        assertThat(confList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSmtphostIsRequired() throws Exception {
        int databaseSizeBeforeTest = confRepository.findAll().size();
        // set the field null
        conf.setSmtphost(null);

        // Create the Conf, which fails.

        restConfMockMvc.perform(post("/api/confs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(conf)))
            .andExpect(status().isBadRequest());

        List<Conf> confList = confRepository.findAll();
        assertThat(confList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSmtppasswordIsRequired() throws Exception {
        int databaseSizeBeforeTest = confRepository.findAll().size();
        // set the field null
        conf.setSmtppassword(null);

        // Create the Conf, which fails.

        restConfMockMvc.perform(post("/api/confs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(conf)))
            .andExpect(status().isBadRequest());

        List<Conf> confList = confRepository.findAll();
        assertThat(confList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPushserverIsRequired() throws Exception {
        int databaseSizeBeforeTest = confRepository.findAll().size();
        // set the field null
        conf.setPushserver(null);

        // Create the Conf, which fails.

        restConfMockMvc.perform(post("/api/confs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(conf)))
            .andExpect(status().isBadRequest());

        List<Conf> confList = confRepository.findAll();
        assertThat(confList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSmtpuserIsRequired() throws Exception {
        int databaseSizeBeforeTest = confRepository.findAll().size();
        // set the field null
        conf.setSmtpuser(null);

        // Create the Conf, which fails.

        restConfMockMvc.perform(post("/api/confs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(conf)))
            .andExpect(status().isBadRequest());

        List<Conf> confList = confRepository.findAll();
        assertThat(confList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSmsgatewayIsRequired() throws Exception {
        int databaseSizeBeforeTest = confRepository.findAll().size();
        // set the field null
        conf.setSmsgateway(null);

        // Create the Conf, which fails.

        restConfMockMvc.perform(post("/api/confs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(conf)))
            .andExpect(status().isBadRequest());

        List<Conf> confList = confRepository.findAll();
        assertThat(confList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllConfs() throws Exception {
        // Initialize the database
        confRepository.saveAndFlush(conf);

        // Get all the confList
        restConfMockMvc.perform(get("/api/confs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(conf.getId().intValue())))
            .andExpect(jsonPath("$.[*].heuredebut").value(hasItem(DEFAULT_HEUREDEBUT)))
            .andExpect(jsonPath("$.[*].heurefin").value(hasItem(DEFAULT_HEUREFIN)))
            .andExpect(jsonPath("$.[*].smtphost").value(hasItem(DEFAULT_SMTPHOST.toString())))
            .andExpect(jsonPath("$.[*].smtppassword").value(hasItem(DEFAULT_SMTPPASSWORD.toString())))
            .andExpect(jsonPath("$.[*].pushserver").value(hasItem(DEFAULT_PUSHSERVER.toString())))
            .andExpect(jsonPath("$.[*].smtpuser").value(hasItem(DEFAULT_SMTPUSER.toString())))
            .andExpect(jsonPath("$.[*].smsgateway").value(hasItem(DEFAULT_SMSGATEWAY.toString())));
    }
    
    @Test
    @Transactional
    public void getConf() throws Exception {
        // Initialize the database
        confRepository.saveAndFlush(conf);

        // Get the conf
        restConfMockMvc.perform(get("/api/confs/{id}", conf.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(conf.getId().intValue()))
            .andExpect(jsonPath("$.heuredebut").value(DEFAULT_HEUREDEBUT))
            .andExpect(jsonPath("$.heurefin").value(DEFAULT_HEUREFIN))
            .andExpect(jsonPath("$.smtphost").value(DEFAULT_SMTPHOST.toString()))
            .andExpect(jsonPath("$.smtppassword").value(DEFAULT_SMTPPASSWORD.toString()))
            .andExpect(jsonPath("$.pushserver").value(DEFAULT_PUSHSERVER.toString()))
            .andExpect(jsonPath("$.smtpuser").value(DEFAULT_SMTPUSER.toString()))
            .andExpect(jsonPath("$.smsgateway").value(DEFAULT_SMSGATEWAY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingConf() throws Exception {
        // Get the conf
        restConfMockMvc.perform(get("/api/confs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConf() throws Exception {
        // Initialize the database
        confRepository.saveAndFlush(conf);

        int databaseSizeBeforeUpdate = confRepository.findAll().size();

        // Update the conf
        Conf updatedConf = confRepository.findById(conf.getId()).get();
        // Disconnect from session so that the updates on updatedConf are not directly saved in db
        em.detach(updatedConf);
        updatedConf
            .heuredebut(UPDATED_HEUREDEBUT)
            .heurefin(UPDATED_HEUREFIN)
            .smtphost(UPDATED_SMTPHOST)
            .smtppassword(UPDATED_SMTPPASSWORD)
            .pushserver(UPDATED_PUSHSERVER)
            .smtpuser(UPDATED_SMTPUSER)
            .smsgateway(UPDATED_SMSGATEWAY);

        restConfMockMvc.perform(put("/api/confs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedConf)))
            .andExpect(status().isOk());

        // Validate the Conf in the database
        List<Conf> confList = confRepository.findAll();
        assertThat(confList).hasSize(databaseSizeBeforeUpdate);
        Conf testConf = confList.get(confList.size() - 1);
        assertThat(testConf.getHeuredebut()).isEqualTo(UPDATED_HEUREDEBUT);
        assertThat(testConf.getHeurefin()).isEqualTo(UPDATED_HEUREFIN);
        assertThat(testConf.getSmtphost()).isEqualTo(UPDATED_SMTPHOST);
        assertThat(testConf.getSmtppassword()).isEqualTo(UPDATED_SMTPPASSWORD);
        assertThat(testConf.getPushserver()).isEqualTo(UPDATED_PUSHSERVER);
        assertThat(testConf.getSmtpuser()).isEqualTo(UPDATED_SMTPUSER);
        assertThat(testConf.getSmsgateway()).isEqualTo(UPDATED_SMSGATEWAY);
    }

    @Test
    @Transactional
    public void updateNonExistingConf() throws Exception {
        int databaseSizeBeforeUpdate = confRepository.findAll().size();

        // Create the Conf

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConfMockMvc.perform(put("/api/confs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(conf)))
            .andExpect(status().isBadRequest());

        // Validate the Conf in the database
        List<Conf> confList = confRepository.findAll();
        assertThat(confList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteConf() throws Exception {
        // Initialize the database
        confRepository.saveAndFlush(conf);

        int databaseSizeBeforeDelete = confRepository.findAll().size();

        // Delete the conf
        restConfMockMvc.perform(delete("/api/confs/{id}", conf.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Conf> confList = confRepository.findAll();
        assertThat(confList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Conf.class);
        Conf conf1 = new Conf();
        conf1.setId(1L);
        Conf conf2 = new Conf();
        conf2.setId(conf1.getId());
        assertThat(conf1).isEqualTo(conf2);
        conf2.setId(2L);
        assertThat(conf1).isNotEqualTo(conf2);
        conf1.setId(null);
        assertThat(conf1).isNotEqualTo(conf2);
    }
}
