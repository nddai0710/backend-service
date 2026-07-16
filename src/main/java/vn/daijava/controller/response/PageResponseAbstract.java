package vn.daijava.controller.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageResponseAbstract {
    public int pageNumber;
    public int pageSize;
    public long totalPages;
    public long totalElements;
}
