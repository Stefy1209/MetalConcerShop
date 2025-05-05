package core;

import java.util.Objects;

public class Entity<Tid> {
    private Tid id;

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
