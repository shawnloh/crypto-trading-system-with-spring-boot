package tech.betterwith.tradingsystem.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class PageResponseDto<T> {
    private List<T> data;
    private int pageSize;
    private int pageNumber;
    private int totalPages;
    private long totalElements;
    private boolean isLast;
}
