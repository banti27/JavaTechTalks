package org.example.exception;

import com.opencsv.bean.exceptionhandler.CsvExceptionHandler;
import com.opencsv.exceptions.CsvException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CsvFileReadingException implements CsvExceptionHandler {

  @Override
  public CsvException handleException(CsvException e) {
    log.error("Cause: {}", e);
    log.info("Line No: {} Value: {}", e.getLineNumber(), e.getLine());
    return null;
  }
}
