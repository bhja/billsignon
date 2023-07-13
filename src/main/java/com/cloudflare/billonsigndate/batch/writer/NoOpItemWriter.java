package com.cloudflare.billonsigndate.batch.writer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 *
 */
public class NoOpItemWriter
    implements ItemWriter<CompletableFuture<String>> {


  private Logger logger = LoggerFactory.getLogger(NoOpItemWriter.class);
  @Override
  public void write(List<? extends CompletableFuture<String>> list) throws Exception {
    //Will wait for all of the tasks to complete to make sure nothing is missed.
    CompletableFuture.allOf(list.toArray(new CompletableFuture[list.size()])).join();
    List<String> results = list
        .stream()
        .filter(future -> !future.isCompletedExceptionally())
        .map(CompletableFuture::join)
        .collect(Collectors.toList());
    logger.info("{}",results);
  }
}
