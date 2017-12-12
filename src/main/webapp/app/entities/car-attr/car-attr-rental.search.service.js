(function() {
    'use strict';

    angular
        .module('carsOnRentApp')
        .factory('CarAttrSearch', CarAttrSearch);

    CarAttrSearch.$inject = ['$resource'];

    function CarAttrSearch($resource) {
        var resourceUrl =  'api/_search/car-attrs/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
