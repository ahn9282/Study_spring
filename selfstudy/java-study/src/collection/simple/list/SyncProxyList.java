package collection.simple.list;

import java.util.Arrays;

import static thread.util.ThreadUtils.sleep;

public class SyncProxyList implements SimpleList {

    private SimpleList target;

    public SyncProxyList(SimpleList target) {
        this.target = target;
    }

    @Override
    public synchronized Object get(int index) {
        return target.get(index);
    }

    @Override
    public synchronized void add(Object e) {
        target.add(e);
    }

    @Override
    public synchronized int size() {
        return target.size();
    }

    @Override
    public String toString() {
        return target.toString();
    }
}
