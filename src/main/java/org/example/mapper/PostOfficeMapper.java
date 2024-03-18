package org.example.mapper;

import org.example.entity.PostOffice;
import org.example.model.PostOfficeCsvRow;
import org.example.model.PostOfficeDto;
import org.example.model.PostOfficeResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = StringMapper.class)
public interface PostOfficeMapper {
  PostOfficeMapper INSTANCE = Mappers.getMapper(PostOfficeMapper.class);

  PostOffice map(PostOfficeCsvRow source);

  PostOfficeResponse map(PostOffice source);

  PostOfficeResponse map(PostOfficeDto source);
}
