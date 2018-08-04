/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.cetic.rssfeeds.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author cetic
 */
@Entity
public class Parameter  {

    @Id
    @GeneratedValue
    private Long id;
    
    private String name ; 
    private String type ;
    private String value;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "rss_uri_id")
    private final RSSFeed rssURI;

    public Parameter(RSSFeed rssURI) {
        this.rssURI = rssURI;
    }

    public Parameter() {
        this(null);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
    
    
}