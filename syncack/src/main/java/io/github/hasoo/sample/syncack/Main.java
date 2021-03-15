package io.github.hasoo.sample.syncack;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {

  public static void main(String[] args) throws ExecutionException, InterruptedException {
    SyncAck<String> syncAck = new SyncAck<>();

    ExecutorService service = Executors.newCachedThreadPool();

    Future<Void> result = service.submit(() -> {
      log.debug("start");
      Thread.sleep(3000);
      syncAck.save("Hasoo");
      return null;
    });

    log.debug(syncAck.receive(10, TimeUnit.SECONDS));
    service.shutdown();
  }
}
