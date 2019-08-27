package com.mycompany.myapp.domain;


import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * A ResultatRecherche.
 */
@Entity
@Table(name = "resultat_recherche")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ResultatRecherche implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;


    @Column(name = "jhi_date", nullable = false)
    private ZonedDateTime date;

    @OneToMany(mappedBy = "resultatRecherche")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ResultatItem> resultatItems = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("resultatRecherches")
    private Recherche recherche;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public ResultatRecherche date(ZonedDateTime date) {
        this.date = date;
        return this;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public Set<ResultatItem> getResultatItems() {
        return resultatItems;
    }

    public ResultatRecherche resultatItems(Set<ResultatItem> resultatItems) {
        this.resultatItems = resultatItems;
        return this;
    }

    public ResultatRecherche addResultatItem(ResultatItem resultatItem) {
        this.resultatItems.add(resultatItem);
        resultatItem.setResultatRecherche(this);
        return this;
    }

    public ResultatRecherche removeResultatItem(ResultatItem resultatItem) {
        this.resultatItems.remove(resultatItem);
        resultatItem.setResultatRecherche(null);
        return this;
    }

    public void setResultatItems(Set<ResultatItem> resultatItems) {
        this.resultatItems = resultatItems;
    }

    public Recherche getRecherche() {
        return recherche;
    }

    public ResultatRecherche recherche(Recherche recherche) {
        this.recherche = recherche;
        return this;
    }

    public void setRecherche(Recherche recherche) {
        this.recherche = recherche;
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
        ResultatRecherche resultatRecherche = (ResultatRecherche) o;
        if (resultatRecherche.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), resultatRecherche.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ResultatRecherche{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            "}";
    }
}
