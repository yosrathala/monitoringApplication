package com.mycompany.myapp.config;

import java.time.Duration;

import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.jsr107.Eh107Configuration;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.jhipster.config.JHipsterProperties;
import io.github.jhipster.config.jcache.BeanClassLoaderAwareJCacheRegionFactory;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        BeanClassLoaderAwareJCacheRegionFactory.setBeanClassLoader(this.getClass().getClassLoader());
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(com.mycompany.myapp.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.UserExtra.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Source.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Source.class.getName() + ".recherches", jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Motcle.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Motcle.class.getName() + ".recherches", jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.ResultatRecherche.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.ResultatRecherche.class.getName() + ".resultatItems", jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Recherche.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Recherche.class.getName() + ".resultatRecherches", jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Recherche.class.getName() + ".sources", jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.ResultatItem.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Conf.class.getName(), jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
