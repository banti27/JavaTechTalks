package org.example.util;

import com.opencsv.bean.CsvToBeanBuilder;
import java.io.FileNotFoundException;
import java.io.FileReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.exception.CsvFileReadingException;
import org.example.mapper.PostOfficeMapper;
import org.example.model.PostOfficeCsvRow;
import org.example.repository.PostOfficeRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StopWatch;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataLoader {

  private final PostOfficeRepository postOfficeRepository;

  @Value("${file.all-india-po}")
  private String allIndiaPOFilePath;

  public void loadPostOfficeData() {
    try {
      StopWatch stopWatch = new StopWatch();
      stopWatch.start();

      final var file = ResourceUtils.getFile(this.allIndiaPOFilePath);
      final var fileReader = new FileReader(file);

      final var csvRows =
          new CsvToBeanBuilder<PostOfficeCsvRow>(fileReader)
                  .withType(PostOfficeCsvRow.class)
                  .withExceptionHandler(new CsvFileReadingException())
                  .build()
                  .stream();

      final var postOfficeList = csvRows.map(PostOfficeMapper.INSTANCE::map).toList();

      this.postOfficeRepository.saveInBatch(postOfficeList);

      stopWatch.stop();

      log.info("Total time taken: {}ms", stopWatch.getTotalTimeMillis());
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
  }
}
