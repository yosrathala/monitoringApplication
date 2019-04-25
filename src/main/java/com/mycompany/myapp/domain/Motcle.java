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
 * A Motcle.
 */
@Entity
@Table(name = "motcle")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Motcle implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    @NotNull
    @Column(name = "motinclue", nullable = false)
    private String motinclue;

    @NotNull
    @Column(name = "motexclue", nullable = false)
    private String motexclue;

    @OneToMany(mappedBy = "motcle")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Recherche> recherches = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public Motcle nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getMotinclue() {
        return motinclue;
    }

    public Motcle motinclue(String motinclue) {
        this.motinclue = motinclue;
        return this;
    }

    public void setMotinclue(String motinclue) {
        this.motinclue = motinclue;
    }

    public String getMotexclue() {
        return motexclue;
    }

    public Motcle motexclue(String motexclue) {
        this.motexclue = motexclue;
        return this;
    }

    public void setMotexclue(String motexclue) {
        this.motexclue = motexclue;
    }

    public Set<Recherche> getRecherches() {
        return recherches;
    }

    public Motcle recherches(Set<Recherche> recherches) {
        this.recherches = recherches;
        return this;
    }

    public Motcle addRecherche(Recherche recherche) {
        this.recherches.add(recherche);
        recherche.setMotcle(this);
        return this;
    }

    public Motcle removeRecherche(Recherche recherche) {
        this.recherches.remove(recherche);
        recherche.setMotcle(null);
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
        Motcle motcle = (Motcle) o;
        if (motcle.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), motcle.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Motcle{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", motinclue='" + getMotinclue() + "'" +
            ", motexclue='" + getMotexclue() + "'" +
            "}";
    }
}
