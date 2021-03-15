package io.github.hasoo.sample.syncack;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

public class SyncAck<T> {

  private final SynchronousQueue<T> queue = new SynchronousQueue<>();

  public T receive(long timeout, TimeUnit timeUnit) throws InterruptedException {
    return queue.poll(timeout, timeUnit);
  }

  public void save(T t) throws InterruptedException {
    queue.put(t);
  }
}
