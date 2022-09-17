package com.epam.esm.pagination;

import com.epam.esm.model.BusinessModel;

import java.util.List;

/**
 * Provides methods to help implement HATEOAS links to pages.
 */
public class Page<T extends BusinessModel> {
    private static final int FIRST_PAGE = 1;
    private final int number;
    private final int size;
    private final int total;
    private final List<T> content;

    public Page(int number, int size, int total, List<T> content) {
        this.number = number;
        this.size = size;
        this.total = total;
        this.content = content;
    }

    public int getNumber() {
        return number;
    }

    public int getSize() {
        return size;
    }

    public List<T> getContent() {
        return content;
    }

    public boolean hasNext(){
        return number + 1 <= getTotalPages();

    }

    public int getTotalPages() {
        return getSize() == 0 ? 1 : (int) Math.ceil((double) total / (double) getSize());
    }

    public boolean hasPrevious() {
        return number -1 > 0;
    }

    public int getNextPageNumber() {
        return hasNext() ? number + 1 : getTotalPages();
    }

    public int getPreviousPageNumber() {
        return hasPrevious() ? number - 1 : FIRST_PAGE;
    }
}
