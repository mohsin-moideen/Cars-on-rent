(function() {
    'use strict';
    angular
        .module('carsOnRentApp')
        .factory('Coordinates', Coordinates);

    Coordinates.$inject = ['$resource'];

    function Coordinates ($resource) {
        var resourceUrl =  'api/coordinates/:id';

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
