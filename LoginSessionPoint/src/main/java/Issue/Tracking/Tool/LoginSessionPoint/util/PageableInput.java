package Issue.Tracking.Tool.LoginSessionPoint.util;

import lombok.Data;

@Data
public class PageableInput {
    private String PageIndex;
    private String PageSize;
    private String SortBy;
    private String SortDirection;
}

