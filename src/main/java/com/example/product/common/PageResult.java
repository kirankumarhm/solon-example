package com.example.product.common;

import com.mybatisflex.core.paginate.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Standard Paginated Response Container
 *
 * @param <T> Item type
 */
@ApiModel(description = "Paginated list result")
public class PageResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "Current page number (1-based index)", example = "1")
    private long pageNumber;

    @ApiModelProperty(value = "Page size (records per page)", example = "10")
    private long pageSize;

    @ApiModelProperty(value = "Total number of records matching query", example = "42")
    private long totalRow;

    @ApiModelProperty(value = "Total number of calculated pages", example = "5")
    private long totalPage;

    @ApiModelProperty(value = "List of records for the current page")
    private List<T> records;

    public PageResult() {
        this.records = Collections.emptyList();
    }

    public PageResult(long pageNumber, long pageSize, long totalRow, long totalPage, List<T> records) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalRow = totalRow;
        this.totalPage = totalPage;
        this.records = records != null ? records : Collections.emptyList();
    }

    /**
     * Map MyBatis-Flex Page to PageResult with DTO transformation
     */
    public static <E, T> PageResult<T> from(Page<E> page, Function<E, T> mapper) {
        List<T> mappedRecords = page.getRecords() != null
                ? page.getRecords().stream().map(mapper).collect(Collectors.toList())
                : Collections.emptyList();

        return new PageResult<>(
                page.getPageNumber(),
                page.getPageSize(),
                page.getTotalRow(),
                page.getTotalPage(),
                mappedRecords
        );
    }

    public long getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(long pageNumber) {
        this.pageNumber = pageNumber;
    }

    public long getPageSize() {
        return pageSize;
    }

    public void setPageSize(long pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotalRow() {
        return totalRow;
    }

    public void setTotalRow(long totalRow) {
        this.totalRow = totalRow;
    }

    public long getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(long totalPage) {
        this.totalPage = totalPage;
    }

    public List<T> getRecords() {
        return records;
    }

    public void setRecords(List<T> records) {
        this.records = records;
    }
}
