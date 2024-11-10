package integradora.SIGI.model;

import jakarta.persistence.*;

@Entity
@Table(name="categorias")
public class Categorias {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="name",columnDefinition="VARCHAR(30)")
    private String name;

    @Column(name="description",columnDefinition = "VARCHAR(50)")
    private String description;

    @Column(name="status",columnDefinition = "BOOL DEFAULT TRUE")
    private boolean status;

    public Categorias(){

    }

    public Categorias(String name, String description, boolean status) {
        this.name = name;
        this.description = description;
        this.status = status;
    }

    public Categorias(Long id, String name, String description, boolean status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
