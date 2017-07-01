package dawid.spring.model;

/**
 * Created by private on 01.07.17.
 */
public enum ColumnKind {
    BACKLOG(Integer.MIN_VALUE, Integer.MAX_VALUE),
    NEXT_TODO(0,3),
    DOING(1,1),
    DONE(Integer.MAX_VALUE, Integer.MAX_VALUE);

    private int order;
    private int maxTaskInColumn;

    ColumnKind(int order, int maxTaskInColumn) {
        this.order = order;
        this.maxTaskInColumn = maxTaskInColumn;
    }

    public int getOrder() {
        return order;
    }

    public int getMaxTaskInColumn() {
        return maxTaskInColumn;
    }
}
