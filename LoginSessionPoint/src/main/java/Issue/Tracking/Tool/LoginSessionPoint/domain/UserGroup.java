package Issue.Tracking.Tool.LoginSessionPoint.domain;


import Issue.Tracking.Tool.LoginSessionPoint.constants.AbstractPersistentObject;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@EqualsAndHashCode(callSuper = false)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_group")

public class UserGroup extends AbstractPersistentObject implements Serializable {
    @JsonSerialize
    @JsonDeserialize

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)


    private Long id;

    @Column(unique = true)
    private String name;
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date createdAt;


    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    private Collection<APIUser> users;



    @OneToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH,CascadeType.DETACH})
    @Fetch( FetchMode.SELECT)
    @JoinColumn(name= "user_group_id",referencedColumnName = "id", insertable = true, updatable = true)
    private APIUser Leader;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date lastUpdated;

    @Override
    public String toString() {
        return "UserGroup{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", createdAt=" + createdAt +
                ", users=" + users.toString() +
                ", Leader=" + Leader.toString() +
                ", lastUpdated=" + lastUpdated +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserGroup)) return false;
        if (!super.equals(o)) return false;
        UserGroup userGroup = (UserGroup) o;
        return Objects.equals(getId(), userGroup.getId()) && Objects.equals(getName(), userGroup.getName()) && Objects.equals(getCreatedAt(), userGroup.getCreatedAt()) && Objects.equals(getUsers(), userGroup.getUsers()) && Objects.equals(getLeader(), userGroup.getLeader()) && Objects.equals(getLastUpdated(), userGroup.getLastUpdated());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, name, createdAt, users, Leader, lastUpdated);
    }
}
