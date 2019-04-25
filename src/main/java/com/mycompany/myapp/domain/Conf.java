package com.mycompany.myapp.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Conf.
 */
@Entity
@Table(name = "conf")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Conf implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "heuredebut", nullable = false)
    private Integer heuredebut;

    @NotNull
    @Column(name = "heurefin", nullable = false)
    private Integer heurefin;

    @NotNull
    @Column(name = "smtphost", nullable = false)
    private String smtphost;

    @NotNull
    @Column(name = "smtppassword", nullable = false)
    private String smtppassword;

    @NotNull
    @Column(name = "pushserver", nullable = false)
    private String pushserver;

    @NotNull
    @Column(name = "smtpuser", nullable = false)
    private String smtpuser;

    @NotNull
    @Column(name = "smsgateway", nullable = false)
    private String smsgateway;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getHeuredebut() {
        return heuredebut;
    }

    public Conf heuredebut(Integer heuredebut) {
        this.heuredebut = heuredebut;
        return this;
    }

    public void setHeuredebut(Integer heuredebut) {
        this.heuredebut = heuredebut;
    }

    public Integer getHeurefin() {
        return heurefin;
    }

    public Conf heurefin(Integer heurefin) {
        this.heurefin = heurefin;
        return this;
    }

    public void setHeurefin(Integer heurefin) {
        this.heurefin = heurefin;
    }

    public String getSmtphost() {
        return smtphost;
    }

    public Conf smtphost(String smtphost) {
        this.smtphost = smtphost;
        return this;
    }

    public void setSmtphost(String smtphost) {
        this.smtphost = smtphost;
    }

    public String getSmtppassword() {
        return smtppassword;
    }

    public Conf smtppassword(String smtppassword) {
        this.smtppassword = smtppassword;
        return this;
    }

    public void setSmtppassword(String smtppassword) {
        this.smtppassword = smtppassword;
    }

    public String getPushserver() {
        return pushserver;
    }

    public Conf pushserver(String pushserver) {
        this.pushserver = pushserver;
        return this;
    }

    public void setPushserver(String pushserver) {
        this.pushserver = pushserver;
    }

    public String getSmtpuser() {
        return smtpuser;
    }

    public Conf smtpuser(String smtpuser) {
        this.smtpuser = smtpuser;
        return this;
    }

    public void setSmtpuser(String smtpuser) {
        this.smtpuser = smtpuser;
    }

    public String getSmsgateway() {
        return smsgateway;
    }

    public Conf smsgateway(String smsgateway) {
        this.smsgateway = smsgateway;
        return this;
    }

    public void setSmsgateway(String smsgateway) {
        this.smsgateway = smsgateway;
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
        Conf conf = (Conf) o;
        if (conf.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), conf.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Conf{" +
            "id=" + getId() +
            ", heuredebut=" + getHeuredebut() +
            ", heurefin=" + getHeurefin() +
            ", smtphost='" + getSmtphost() + "'" +
            ", smtppassword='" + getSmtppassword() + "'" +
            ", pushserver='" + getPushserver() + "'" +
            ", smtpuser='" + getSmtpuser() + "'" +
            ", smsgateway='" + getSmsgateway() + "'" +
            "}";
    }
}
