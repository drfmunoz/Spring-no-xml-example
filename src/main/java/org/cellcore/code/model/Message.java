package org.cellcore.code.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Message extends AbstractJPAEntity{

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date sentDate;

    @ManyToOne(fetch = FetchType.EAGER)
    private Author toAuthor;

    @ManyToOne(fetch = FetchType.EAGER)
    private Author fromAuthor;

   @Column(columnDefinition = "VARCHAR(4000)")
    private String message;

    public Date getSentDate() {
        return sentDate;
    }

    public void setSentDate(Date sentDate) {
        this.sentDate = sentDate;
    }

    public Author getToAuthor() {
        return toAuthor;
    }

    public void setToAuthor(Author toAuthor) {
        this.toAuthor = toAuthor;
    }

    public Author getFromAuthor() {
        return fromAuthor;
    }

    public void setFromAuthor(Author fromAuthor) {
        this.fromAuthor = fromAuthor;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
