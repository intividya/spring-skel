package com.cepheid.cloud.skel.model;

import com.cepheid.cloud.skel.model.enums.State;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.Set;

@Entity
public class Item extends AbstractEntity implements Serializable {

    private String name;

    @Enumerated(EnumType.STRING)
    private State state;

    @OneToMany(mappedBy = "item", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<Description> descriptions;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Set<Description> getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(Set<Description> descriptions) {
        this.descriptions = descriptions;
    }

    @Override
    public String toString() {
        return String.format("Item{name='%s', state=%s, descriptions=%s, id=%d, createdAt=%s, updatedAt=%s}", name, state, descriptions, id, createdAt, updatedAt);
    }

}
