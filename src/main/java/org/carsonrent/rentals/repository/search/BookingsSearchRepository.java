package org.carsonrent.rentals.repository.search;

import org.carsonrent.rentals.domain.Bookings;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Bookings entity.
 */
public interface BookingsSearchRepository extends ElasticsearchRepository<Bookings, Long> {
}
