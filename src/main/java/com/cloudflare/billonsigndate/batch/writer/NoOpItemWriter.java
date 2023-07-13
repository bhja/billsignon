package com.cloudflare.billonsigndate.batch.writer;

import org.springframework.batch.item.ItemWriter;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Placeholder class for No operation
 */
public class NoOpItemWriter
    implements ItemWriter {
  @Override
  public void write(List list) throws Exception {
    // do nothing
  }
}
