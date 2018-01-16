package edu.nju.qstlib.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "question")
public class Question implements Serializable {
    private String id;
    private String libId;
    private String content;
    private Set<Option> options = new HashSet<>();
    private QstLib lib;

    public Question() {
    }

    public Question(String id, String libId, String content) {
        this.id = id;
        this.libId = libId;
        this.content = content;
    }

    @Id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getLibId() {
        return libId;
    }

    public void setLibId(String libId) {
        this.libId = libId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public Set<Option> getOptions() {
        return options;
    }

    public void setOptions(Set<Option> options) {
        this.options = options;
    }

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "libId", referencedColumnName = "id", insertable = false, updatable = false)
    public QstLib getLib() {
        return lib;
    }

    public void setLib(QstLib lib) {
        this.lib = lib;
    }
}
