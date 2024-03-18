package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.model.ApiPageRequest;
import org.example.model.BaseResponse;
import org.example.model.PostOfficeCollection;
import org.example.service.PostOfficeService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/post-office")
@RequiredArgsConstructor
public class PostOfficeController {

  private final PostOfficeService postOfficeService;

  @GetMapping
  public BaseResponse<PostOfficeCollection> findAllPostOffice(
      @RequestParam(defaultValue = "0") Integer pageNo,
      @RequestParam(defaultValue = "50") Integer pageSize,
      @RequestParam(defaultValue = "state") String columnName,
      @RequestParam(defaultValue = "ASC") Sort.Direction sortDir,
      @RequestParam(defaultValue = "false") boolean fetchMetaData) {
    final var pageRequest = PageRequest.of(pageNo, pageSize, Sort.by(sortDir, columnName));
    return BaseResponse.<PostOfficeCollection>builder()
        .data(
            this.postOfficeService.findAllPostOffice(
                ApiPageRequest.builder()
                    .pageRequest(pageRequest)
                    .fetchMetaData(fetchMetaData)
                    .build()))
        .build();
  }
}
