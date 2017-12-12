package org.carsonrent.rentals.repository.search;

import org.carsonrent.rentals.domain.Availability;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Availability entity.
 */
public interface AvailabilitySearchRepository extends ElasticsearchRepository<Availability, Long> {
}
