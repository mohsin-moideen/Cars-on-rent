package org.carsonrent.rentals.repository.search;

import org.carsonrent.rentals.domain.CarPrice;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the CarPrice entity.
 */
public interface CarPriceSearchRepository extends ElasticsearchRepository<CarPrice, Long> {
}
