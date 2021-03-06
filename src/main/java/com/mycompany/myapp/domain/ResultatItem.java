package com.mycompany.myapp.domain;


import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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

    @Column(name = "statu")
    private Boolean statu;

    @Column(name = "note")
    private Boolean note;

    @Column(name = "titre")
    private String titre;

    @Column(name = "jhi_date")
    private String date;

    @Column(name = "url")
    private String url;

    @Column(name = "post_id")
    private String postId;

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

    public Boolean isStatu() {
        return statu;
    }

    public ResultatItem statu(Boolean statu) {
        this.statu = statu;
        return this;
    }

    public void setStatu(Boolean statu) {
        this.statu = statu;
    }

    public Boolean isNote() {
        return note;
    }

    public ResultatItem note(Boolean note) {
        this.note = note;
        return this;
    }

    public void setNote(Boolean note) {
        this.note = note;
    }

    public String getTitre() {
        return titre;
    }

    public ResultatItem titre(String titre) {
        this.titre = titre;
        return this;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDate() {
        return date;
    }

    public ResultatItem date(String date) {
        this.date = date;
        return this;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUrl() {
        return url;
    }

    public ResultatItem url(String url) {
        this.url = url;
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPostId() {
        return postId;
    }

    public ResultatItem postId(String postId) {
        this.postId = postId;
        return this;
    }

    public void setPostId(String postId) {
        this.postId = postId;
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
            ", statu='" + isStatu() + "'" +
            ", note='" + isNote() + "'" +
            ", titre='" + getTitre() + "'" +
            ", date='" + getDate() + "'" +
            ", url='" + getUrl() + "'" +
            ", postId='" + getPostId() + "'" +
            "}";
    }
}
