package org.example.service;

import org.example.model.ApiPageRequest;
import org.example.model.PostOfficeCollection;

public interface PostOfficeService {
  PostOfficeCollection findAllPostOffice(ApiPageRequest pageRequest);
}
