package com.mycompany.myapp.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Source.
 */
@Entity
@Table(name = "source")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Source implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    @NotNull
    @Column(name = "login", nullable = false)
    private String login;

    @NotNull
    @Column(name = "mot_passe", nullable = false)
    private String motPasse;

    @NotNull
    @Column(name = "url", nullable = false)
    private String url;

    @NotNull
    @Column(name = "jhi_key", nullable = false)
    private String key;

    @NotNull
    @Column(name = "data_handler", nullable = false)
    private String dataHandler;

    @OneToMany(mappedBy = "source")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Recherche> recherches = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove

    public Source(Long id, @NotNull String nom, @NotNull String login, @NotNull String motPasse, @NotNull String url,
                  @NotNull String key) {
        super();
        this.id = id;
        this.nom = nom;
        this.login = login;
        this.motPasse = motPasse;
        this.url = url;
        this.key = key;
    }

    public Source() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public Source nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getLogin() {
        return login;
    }

    public Source login(String login) {
        this.login = login;
        return this;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getMotPasse() {
        return motPasse;
    }

    public Source motPasse(String motPasse) {
        this.motPasse = motPasse;
        return this;
    }

    public void setMotPasse(String motPasse) {
        this.motPasse = motPasse;
    }

    public String getUrl() {
        return url;
    }

    public Source url(String url) {
        this.url = url;
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getKey() {
        return key;
    }

    public Source key(String key) {
        this.key = key;
        return this;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDataHandler() {
        return dataHandler;
    }

    public Source dataHandler(String dataHandler) {
        this.dataHandler = dataHandler;
        return this;
    }

    public void setDataHandler(String dataHandler) {
        this.dataHandler = dataHandler;
    }

    public Set<Recherche> getRecherches() {
        return recherches;
    }

    public Source recherches(Set<Recherche> recherches) {
        this.recherches = recherches;
        return this;
    }

    public Source addRecherche(Recherche recherche) {
        this.recherches.add(recherche);
        recherche.setSource(this);
        return this;
    }

    public Source removeRecherche(Recherche recherche) {
        this.recherches.remove(recherche);
        recherche.setSource(null);
        return this;
    }

    public void setRecherches(Set<Recherche> recherches) {
        this.recherches = recherches;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Source source = (Source) o;
        if (source.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), source.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Source{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", login='" + getLogin() + "'" +
            ", motPasse='" + getMotPasse() + "'" +
            ", url='" + getUrl() + "'" +
            ", key='" + getKey() + "'" +
            ", dataHandler='" + getDataHandler() + "'" +
            "}";
    }
}
