package org.example.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Root;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.PostOffice;
import org.example.mapper.PostOfficeMapper;
import org.example.model.ApiPageRequest;
import org.example.model.PageInfo;
import org.example.model.PostOfficeCollection;
import org.example.repository.PostOfficeRepository;
import org.example.util.PaginationUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Service
@RequiredArgsConstructor
public class SimplePostOfficeService implements PostOfficeService {

  private final PostOfficeRepository postOfficeRepository;

  @PersistenceContext private EntityManager entityManager;

  @Transactional(readOnly = true)
  @Override
  public PostOfficeCollection findAllPostOffice(ApiPageRequest apiPageRequest) {
    // return findFromPreDefinedMethod(apiPageRequest); // CASE 1
    // return customQuerySingleSelect(apiPageRequest); // CASE 2
    return usingCriteriaQueries(apiPageRequest); // CASE 3
    // return usingKeySetPagination(apiPageRequest); // CASE 4
  }

  private PostOfficeCollection usingKeySetPagination(ApiPageRequest apiPageRequest) {
    List<PostOffice> data =
        this.entityManager
            .createNativeQuery(
                """
                select * from post_office as po
                where po.id < :lastId
                order by po.id asc limit :pageSize
                """)
            .setParameter("lastId", 201L)
            .setParameter("limit", apiPageRequest.getPageRequest().getPageSize())
            .getResultList();
    return null;
  }

  private PostOfficeCollection usingCriteriaQueries(ApiPageRequest apiPageRequest) {
    final var pageRequest = apiPageRequest.getPageRequest();
    final var criteriaBuilder = this.entityManager.getCriteriaBuilder();

    // Pagination data
    final var dataQuery = criteriaBuilder.createQuery(PostOffice.class);
    final var dataRoot = dataQuery.from(PostOffice.class);
    dataQuery.select(dataRoot).orderBy(buildOrderList(pageRequest, criteriaBuilder, dataRoot));
    final var postOffices =
        this.entityManager
            .createQuery(dataQuery)
            .setFirstResult(
                PaginationUtils.calculateOffset(
                    pageRequest.getPageNumber(), pageRequest.getPageSize()))
            .setMaxResults(pageRequest.getPageSize())
            .getResultList();

    // Count data
    PageInfo pageInfo = null;
    if (apiPageRequest.isFetchMetaData()) {
      final var countQuery = criteriaBuilder.createTupleQuery();
      final var countRoot = countQuery.from(PostOffice.class);
      countQuery.multiselect(criteriaBuilder.count(countRoot).alias("TOTAL"));
      final var tuple = this.entityManager.createQuery(countQuery).getSingleResult();
      pageInfo = PaginationUtils.buildPageInfo((Long) tuple.get("TOTAL"), pageRequest);
    }

    return PostOfficeCollection.builder()
        .postOfficeResponseList(postOffices.stream().map(PostOfficeMapper.INSTANCE::map).toList())
        .pageInfo(pageInfo)
        .build();
  }

  private static List<Order> buildOrderList(
      PageRequest pageRequest, CriteriaBuilder criteriaBuilder, Root<PostOffice> root) {
    return pageRequest.getSort().stream()
        .map(
            order -> {
              if (order.isAscending()) {
                return criteriaBuilder.asc(root.get(order.getProperty()));
              } else {
                return criteriaBuilder.desc(root.get(order.getProperty()));
              }
            })
        .toList();
  }

  private PostOfficeCollection customQuerySingleSelect(ApiPageRequest apiPageRequest) {
    final var pageRequest = apiPageRequest.getPageRequest();
    final var postOfficeDto = this.postOfficeRepository.fetchAllPostOffice(pageRequest);
    final var firstPostOffice =
        postOfficeDto.stream()
            .findAny()
            .orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NO_CONTENT, "No records present"));

    return PostOfficeCollection.builder()
        .postOfficeResponseList(postOfficeDto.stream().map(PostOfficeMapper.INSTANCE::map).toList())
        .pageInfo(
            PageInfo.builder()
                .totalRecord(firstPostOffice.getTotalRecords())
                .totalPages(
                    PaginationUtils.getTotalPages(
                        firstPostOffice.getTotalRecords(), pageRequest.getPageSize()))
                .build())
        .build();
  }

  private PostOfficeCollection findFromPreDefinedMethod(ApiPageRequest apiPageRequest) {
    final var pageRequest = apiPageRequest.getPageRequest();
    final var postOfficePage = this.postOfficeRepository.findAll(pageRequest);

    final var postOfficeList = postOfficePage.stream().map(PostOfficeMapper.INSTANCE::map).toList();

    return PostOfficeCollection.builder()
        .postOfficeResponseList(postOfficeList)
        .pageInfo(
            PageInfo.builder()
                .totalRecord(postOfficePage.getTotalElements())
                .totalPages(
                    PaginationUtils.getTotalPages(
                        postOfficePage.getTotalElements(), pageRequest.getPageSize()))
                .build())
        .build();
  }
}
