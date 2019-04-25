package com.mycompany.myapp.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A ResultatItem.
 */
@Entity
@Table(name = "resultat_item")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ResultatItem implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "contenu", nullable = false)
    private String contenu;

    @NotNull
    @Column(name = "statut", nullable = false)
    private String statut;

    @NotNull
    @Column(name = "taux", nullable = false)
    private String taux;

    @ManyToOne
    @JsonIgnoreProperties("resultatItems")
    private ResultatRecherche resultatRecherche;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContenu() {
        return contenu;
    }

    public ResultatItem contenu(String contenu) {
        this.contenu = contenu;
        return this;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public String getStatut() {
        return statut;
    }

    public ResultatItem statut(String statut) {
        this.statut = statut;
        return this;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getTaux() {
        return taux;
    }

    public ResultatItem taux(String taux) {
        this.taux = taux;
        return this;
    }

    public void setTaux(String taux) {
        this.taux = taux;
    }

    public ResultatRecherche getResultatRecherche() {
        return resultatRecherche;
    }

    public ResultatItem resultatRecherche(ResultatRecherche resultatRecherche) {
        this.resultatRecherche = resultatRecherche;
        return this;
    }

    public void setResultatRecherche(ResultatRecherche resultatRecherche) {
        this.resultatRecherche = resultatRecherche;
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
        ResultatItem resultatItem = (ResultatItem) o;
        if (resultatItem.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), resultatItem.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ResultatItem{" +
            "id=" + getId() +
            ", contenu='" + getContenu() + "'" +
            ", statut='" + getStatut() + "'" +
            ", taux='" + getTaux() + "'" +
            "}";
    }
}
