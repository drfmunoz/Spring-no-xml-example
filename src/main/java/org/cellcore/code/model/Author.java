package org.cellcore.code.model;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Author extends AbstractJPAEntity {
    @Column(columnDefinition = "VARCHAR(50)")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
