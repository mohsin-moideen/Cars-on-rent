(function() {
    'use strict';

    angular
        .module('carsOnRentApp')
        .factory('CarPriceSearch', CarPriceSearch);

    CarPriceSearch.$inject = ['$resource'];

    function CarPriceSearch($resource) {
        var resourceUrl =  'api/_search/car-prices/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
