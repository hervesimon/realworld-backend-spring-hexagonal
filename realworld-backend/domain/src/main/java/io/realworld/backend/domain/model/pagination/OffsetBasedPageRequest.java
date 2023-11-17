package io.realworld.backend.domain.model.pagination;

public class OffsetBasedPageRequest {
    private final int offset;
    private final int limit;
    private final Sort sort;

    /** Creates OffsetBasedPageRequest instance. */
    public OffsetBasedPageRequest(int offset, int limit, Sort sort) {
        this.offset = offset;
        this.limit = limit;
        this.sort = sort;
    }

    public int getPageNumber() {
        return offset / limit;
    }

    public int getPageSize() {
        return limit;
    }

    public long getOffset() {
        return offset;
    }

    public Sort getSort() {
        return sort;
    }

    public OffsetBasedPageRequest next() {
        return new OffsetBasedPageRequest((int) getOffset() + getPageSize(), getPageSize(), getSort());
    }

    private OffsetBasedPageRequest previous() {
        // The integers are positive. Subtracting does not let them become bigger than integer.
        return hasPrevious() ? new OffsetBasedPageRequest((int) getOffset() - getPageSize(), getPageSize(), getSort()) : this;
    }

    public OffsetBasedPageRequest previousOrFirst() {
        return hasPrevious() ? previous() : first();
    }

    public OffsetBasedPageRequest first() {
        return new OffsetBasedPageRequest(0, getPageSize(), getSort());
    }

    public boolean hasPrevious() {
        return offset > limit;
    }

    public static OffsetBasedPageRequest of(int offset, int limit, Sort sort) {
        return new OffsetBasedPageRequest(offset, limit, sort);
    }
}
