package com.uwefuchs.demo.heroestutorial.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity(name="HERO")
public class Hero implements Serializable {
	private static final long serialVersionUID = 1L;

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

    @Column(nullable = false)
    private String name;
    
    // jackson
    @SuppressWarnings("unused")
	private Hero() {    	
    }

    public Hero (Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public boolean nameContains(String sequence) {
        return sequence != null 
            && !sequence.trim().isEmpty()
            && this.getName() != null 
            && this.getName().trim().toLowerCase().contains(sequence.trim().toLowerCase());
    }
}
