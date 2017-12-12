(function() {
    'use strict';
    angular
        .module('carsOnRentApp')
        .factory('CarPrice', CarPrice);

    CarPrice.$inject = ['$resource'];

    function CarPrice ($resource) {
        var resourceUrl =  'api/car-prices/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
