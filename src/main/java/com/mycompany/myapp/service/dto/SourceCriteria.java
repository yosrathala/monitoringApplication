package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;

import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the Source entity. This class is used in SourceResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /sources?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class SourceCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nom;

    private StringFilter login;

    private StringFilter motPasse;

    private StringFilter url;

    private StringFilter key;

    private StringFilter dataHandler;

    private LongFilter rechercheId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getNom() {
        return nom;
    }

    public void setNom(StringFilter nom) {
        this.nom = nom;
    }

    public StringFilter getLogin() {
        return login;
    }

    public void setLogin(StringFilter login) {
        this.login = login;
    }

    public StringFilter getMotPasse() {
        return motPasse;
    }

    public void setMotPasse(StringFilter motPasse) {
        this.motPasse = motPasse;
    }

    public StringFilter getUrl() {
        return url;
    }

    public void setUrl(StringFilter url) {
        this.url = url;
    }

    public StringFilter getKey() {
        return key;
    }

    public void setKey(StringFilter key) {
        this.key = key;
    }

    public StringFilter getDataHandler() {
        return dataHandler;
    }

    public void setDataHandler(StringFilter dataHandler) {
        this.dataHandler = dataHandler;
    }

    public LongFilter getRechercheId() {
        return rechercheId;
    }

    public void setRechercheId(LongFilter rechercheId) {
        this.rechercheId = rechercheId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SourceCriteria that = (SourceCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nom, that.nom) &&
            Objects.equals(login, that.login) &&
            Objects.equals(motPasse, that.motPasse) &&
            Objects.equals(url, that.url) &&
            Objects.equals(key, that.key) &&
            Objects.equals(dataHandler, that.dataHandler) &&
            Objects.equals(rechercheId, that.rechercheId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nom,
        login,
        motPasse,
        url,
        key,
        dataHandler,
        rechercheId
        );
    }

    @Override
    public String toString() {
        return "SourceCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nom != null ? "nom=" + nom + ", " : "") +
                (login != null ? "login=" + login + ", " : "") +
                (motPasse != null ? "motPasse=" + motPasse + ", " : "") +
                (url != null ? "url=" + url + ", " : "") +
                (key != null ? "key=" + key + ", " : "") +
                (dataHandler != null ? "dataHandler=" + dataHandler + ", " : "") +
                (rechercheId != null ? "rechercheId=" + rechercheId + ", " : "") +
            "}";
    }

}
