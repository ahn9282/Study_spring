package spring.study.proxy.trace.callback;

public interface TraceCallback<T> {
    T call();
}
