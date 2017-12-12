package org.carsonrent.rentals.repository.search;

import org.carsonrent.rentals.domain.Coordinates;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Coordinates entity.
 */
public interface CoordinatesSearchRepository extends ElasticsearchRepository<Coordinates, Long> {
}
