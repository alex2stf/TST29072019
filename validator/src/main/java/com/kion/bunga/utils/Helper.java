package com.kion.bunga.utils;

import com.kion.bunga.errors.ResourceNotFoundException;
import java.util.concurrent.CompletableFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.async.DeferredResult;

public class Helper {

  private static final Logger log = LoggerFactory.getLogger(Helper.class);

  public static <T> DeferredResult<T> asDeferredResult(CompletableFuture<T> future) {
    DeferredResult<T> result = new DeferredResult();
    future.whenCompleteAsync((value, exception) -> {
      if (value != null) {
        result.setResult(value);
      } else if (exception != null) {
        result.setErrorResult(exception.getCause());
        log.error("Error on future!", exception);
      } else {
        result.setErrorResult(new ResourceNotFoundException("Not found!"));
      }

    });
    return result;
  }

}
