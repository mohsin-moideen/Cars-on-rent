(function() {
    'use strict';

    angular
        .module('carsOnRentApp')
        .factory('BookingsSearch', BookingsSearch);

    BookingsSearch.$inject = ['$resource'];

    function BookingsSearch($resource) {
        var resourceUrl =  'api/_search/bookings/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
