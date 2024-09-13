package collection.simple.list;

import java.util.Arrays;

import static thread.util.ThreadUtils.sleep;

public class SyncList implements SimpleList {


    private static final int DEFAULT_CAPACITY = 5;
    private Object[] elementData;
    private int size = 0;

    public SyncList() {
        elementData = new Object[DEFAULT_CAPACITY];
    }

    @Override
    public synchronized Object get(int index) {
        return size;
    }

    @Override
    public synchronized void add(Object e) {
        elementData[size] = e;
        sleep(100);
        size++;
    }

    @Override
    public synchronized int size() {
        return 0;
    }

    @Override
    public String toString() {
        return Arrays.toString(Arrays.copyOf(elementData, size)) + ", size = " + size + ", capacity : " + elementData.length;
    }
}
