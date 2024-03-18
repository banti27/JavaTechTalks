package org.example.repository;

import java.util.List;
import org.example.entity.PostOffice;
import org.example.model.PostOfficeDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

public interface PostOfficeRepository extends BatchRepository<PostOffice, Long> {

  @Query(
      """
             select po.id as id,
             po.officeName as officeName,
             po.pincode as pincode,
             po.officeType as officeType,
             po.deliveryStatus as deliveryStatus,
             po.district as district,
             po.state as state,
             (select count(p) from PostOffice p) as totalRecords
             from PostOffice po
           """)
  List<PostOfficeDto> fetchAllPostOffice(Pageable pageable);
}
