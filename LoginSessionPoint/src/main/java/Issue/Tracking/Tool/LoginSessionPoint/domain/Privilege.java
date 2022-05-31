package Issue.Tracking.Tool.LoginSessionPoint.domain;

import Issue.Tracking.Tool.LoginSessionPoint.constants.PrivDeserializer;
import Issue.Tracking.Tool.LoginSessionPoint.constants.PrivSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "privilege")

@JsonSerialize(using = PrivSerializer.class)

public class Privilege {
    @Id

    @SequenceGenerator(
            name = "Privilege_Id_seq",
            sequenceName = "Privilege_Id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "Privilege_Id_seq"
    )

    @Column(name = "id")
    private Long id;

    private String name;


    @ManyToMany(mappedBy = "privileges", fetch = FetchType.EAGER)
    private Collection<Role> roles;

    @Override
    public String toString() {
        return "Privilege{" +
                "id=" + id +
                ", name=" + name + '\'' +

                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Privilege)) return false;
        Privilege privilege = (Privilege) o;
        return Objects.equals(getId(), privilege.getId()) && Objects.equals(getName(), privilege.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName());
    }
}
