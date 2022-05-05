package Issue.Tracking.Tool.LoginSessionPoint.fastDomain;


import Issue.Tracking.Tool.LoginSessionPoint.domain.APIUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class fastUserGroup {


    @Id
    private Long id;
    private String name;

    @ElementCollection
    private Collection<String> users = new ArrayList<>();




    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
