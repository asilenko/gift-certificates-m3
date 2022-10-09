package com.epam.esm.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

@MappedSuperclass
public class AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @PrePersist
    public void onPrePersist() {
        Logger logger = LoggerFactory.getLogger(this.getClass());
        logger.debug("Operation: INSERT, class " + this.getClass().getName());
    }

    @PreUpdate
    public void onPreUpdate() {
        Logger logger = LoggerFactory.getLogger(this.getClass());
        logger.debug("Operation: UPDATE, class " + this.getClass().getName() + "Id: " + getId());
    }

    @PreRemove
    public void onPreRemove() {
        Logger logger = LoggerFactory.getLogger(this.getClass());
        logger.debug("Operation: REMOVE, class " + this.getClass().getName() + "Id: " + getId());
    }
}
