package collection.simple.list;

import java.util.Arrays;

import static thread.util.ThreadUtils.sleep;

public class BasicList implements SimpleList {


    private static final int DEFAULT_CAPACITY = 5;
    private Object[] elementData;
    private int size = 0;

    public BasicList() {
        elementData = new Object[DEFAULT_CAPACITY];
    }

    @Override
    public Object get(int index) {
        return size;
    }

    @Override
    public void add(Object e) {
        elementData[size] = e;
        sleep(100);
        size++;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public String toString() {
        return Arrays.toString(Arrays.copyOf(elementData, size)) + ", size = " + size + ", capacity : " + elementData.length;
    }
}