(function() {
    'use strict';
    angular
        .module('carsOnRentApp')
        .factory('Availability', Availability);

    Availability.$inject = ['$resource', 'DateUtils'];

    function Availability ($resource, DateUtils) {
        var resourceUrl =  'api/availabilities/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.startDate = DateUtils.convertDateTimeFromServer(data.startDate);
                        data.endDate = DateUtils.convertDateTimeFromServer(data.endDate);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
