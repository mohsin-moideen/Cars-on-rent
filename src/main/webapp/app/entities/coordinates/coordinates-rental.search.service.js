(function() {
    'use strict';

    angular
        .module('carsOnRentApp')
        .factory('CoordinatesSearch', CoordinatesSearch);

    CoordinatesSearch.$inject = ['$resource'];

    function CoordinatesSearch($resource) {
        var resourceUrl =  'api/_search/coordinates/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
