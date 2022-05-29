package Issue.Tracking.Tool.LoginSessionPoint.constants;

import java.util.UUID;

public class AbstractPersistentObject  implements PersistentObject {

    private Long id = IdGenerator.createId();
    private Integer version;

    public Long getId() {
        return id;
    }
    public void setId(String id) {
        this.id = Long.valueOf(id);
    }

    public Integer getVersion() {
        return version;
    }
    public void setVersion(Integer version) {
        this.version = version;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null ||
                !(o instanceof PersistentObject)) {

            return false;
        }

        PersistentObject other
                = (PersistentObject)o;

        // if the id is missing, return false
        if (id == null) {
            return false;
        }

        // equivalence by id
        return id.equals(other.getId());
    }

    public int hashCode() {
        if (id != null) {
            return id.hashCode();
        } else {
            return super.hashCode();
        }
    }

    public String toString() {
        return this.getClass().getName()
                + "[id=" + id + "]";
    }
}

