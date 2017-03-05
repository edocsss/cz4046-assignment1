package model;

/**
 * Utility model for storing a pair of items
 * @param <T>   data type for item 1
 * @param <U>   data type for item 2
 */
public class Tuple<T, U> {
    private T item1;
    private U item2;

    public Tuple(T item1, U item2) {
        this.item1 = item1;
        this.item2 = item2;
    }

    public T getItem1() {
        return item1;
    }

    public U getItem2() {
        return item2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tuple<?, ?> tuple = (Tuple<?, ?>) o;

        if (item1 != null ? !item1.equals(tuple.item1) : tuple.item1 != null) return false;
        return item2 != null ? item2.equals(tuple.item2) : tuple.item2 == null;
    }

    @Override
    public int hashCode() {
        int result = item1 != null ? item1.hashCode() : 0;
        result = 31 * result + (item2 != null ? item2.hashCode() : 0);
        return result;
    }
}
