package org.carsonrent.rentals.repository.search;

import org.carsonrent.rentals.domain.CarAttr;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the CarAttr entity.
 */
public interface CarAttrSearchRepository extends ElasticsearchRepository<CarAttr, Long> {
}
