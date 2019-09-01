package com.mycompany.myapp.domain;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * A Recherche.
 */
@Entity
@Table(name = "recherche")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Recherche implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "periodicite", nullable = false)
    private Integer periodicite;

    @NotNull
    @Column(name = "emailnotif", nullable = false)
    private Boolean emailnotif;

    @NotNull
    @Column(name = "pushnotif", nullable = false)
    private Boolean pushnotif;

    @NotNull
    @Column(name = "smsnotif", nullable = false)
    private Boolean smsnotif;

    @Column(name = "activated")
    private Boolean activated;

    @ManyToOne
    @JsonIgnoreProperties("recherches")
    private Motcle motcle;

    @OneToMany(mappedBy = "recherche")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ResultatRecherche> resultatRecherches = new HashSet<>();
    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "recherche_source",
               joinColumns = @JoinColumn(name = "recherche_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "source_id", referencedColumnName = "id"))
    private Set<Source> sources = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPeriodicite() {
        return periodicite;
    }

    public Recherche periodicite(Integer periodicite) {
        this.periodicite = periodicite;
        return this;
    }

    public void setPeriodicite(Integer periodicite) {
        this.periodicite = periodicite;
    }

    public Boolean isEmailnotif() {
        return emailnotif;
    }

    public Recherche emailnotif(Boolean emailnotif) {
        this.emailnotif = emailnotif;
        return this;
    }

    public void setEmailnotif(Boolean emailnotif) {
        this.emailnotif = emailnotif;
    }

    public Boolean isPushnotif() {
        return pushnotif;
    }

    public Recherche pushnotif(Boolean pushnotif) {
        this.pushnotif = pushnotif;
        return this;
    }

    public void setPushnotif(Boolean pushnotif) {
        this.pushnotif = pushnotif;
    }

    public Boolean isSmsnotif() {
        return smsnotif;
    }

    public Recherche smsnotif(Boolean smsnotif) {
        this.smsnotif = smsnotif;
        return this;
    }

    public void setSmsnotif(Boolean smsnotif) {
        this.smsnotif = smsnotif;
    }

    public Boolean isActivated() {
        return activated;
    }

    public Recherche activated(Boolean activated) {
        this.activated = activated;
        return this;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public Motcle getMotcle() {
        return motcle;
    }

    public Recherche motcle(Motcle motcle) {
        this.motcle = motcle;
        return this;
    }

    public void setMotcle(Motcle motcle) {
        this.motcle = motcle;
    }

    public Set<ResultatRecherche> getResultatRecherches() {
        return resultatRecherches;
    }

    public Recherche resultatRecherches(Set<ResultatRecherche> resultatRecherches) {
        this.resultatRecherches = resultatRecherches;
        return this;
    }

    public Recherche addResultatRecherche(ResultatRecherche resultatRecherche) {
        this.resultatRecherches.add(resultatRecherche);
        resultatRecherche.setRecherche(this);
        return this;
    }

    public Recherche removeResultatRecherche(ResultatRecherche resultatRecherche) {
        this.resultatRecherches.remove(resultatRecherche);
        resultatRecherche.setRecherche(null);
        return this;
    }

    public void setResultatRecherches(Set<ResultatRecherche> resultatRecherches) {
        this.resultatRecherches = resultatRecherches;
    }

    public Set<Source> getSources() {
        return sources;
    }

    public Recherche sources(Set<Source> sources) {
        this.sources = sources;
        return this;
    }

    public Recherche addSource(Source source) {
        this.sources.add(source);
        source.getRecherches().add(this);
        return this;
    }

    public Recherche removeSource(Source source) {
        this.sources.remove(source);
        source.getRecherches().remove(this);
        return this;
    }

    public void setSources(Set<Source> sources) {
        this.sources = sources;
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
        Recherche recherche = (Recherche) o;
        if (recherche.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), recherche.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Recherche{" +
            "id=" + getId() +
            ", periodicite=" + getPeriodicite() +
            ", emailnotif='" + isEmailnotif() + "'" +
            ", pushnotif='" + isPushnotif() + "'" +
            ", smsnotif='" + isSmsnotif() + "'" +
            ", activated='" + isActivated() + "'" +
            "}";
    }
}
