(function() {
    'use strict';
    angular
        .module('carsOnRentApp')
        .factory('CarAttr', CarAttr);

    CarAttr.$inject = ['$resource'];

    function CarAttr ($resource) {
        var resourceUrl =  'api/car-attrs/:id';

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
