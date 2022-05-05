package Issue.Tracking.Tool.LoginSessionPoint.fastDomain;

import Issue.Tracking.Tool.LoginSessionPoint.domain.*;
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
@NoArgsConstructor
@AllArgsConstructor
public class fastIssue {

    @Id
    private Long id;
    private String name;

    private String status_name;
    private String details;
    private String priority_name;

    @ElementCollection
    private Collection <String> solutions = new ArrayList<>();

    @ElementCollection
    private Collection <String> userGroups = new ArrayList<>();

    @ElementCollection
    private Collection <String> users = new ArrayList<>();


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
