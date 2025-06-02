package core;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

import java.util.Objects;

@MappedSuperclass
public class Entity<Tid> {
    @Id
    @Column(name = "id")
    private Tid id;

    public Entity() {}

    public Entity(Tid id) {
        this.id = id;
    }

    public Tid getId() {
        return id;
    }

    public void setId(Tid id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Entity<?> entity = (Entity<?>) o;
        return Objects.equals(id, entity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
