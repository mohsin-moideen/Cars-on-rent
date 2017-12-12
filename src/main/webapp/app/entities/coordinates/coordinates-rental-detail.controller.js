(function() {
    'use strict';

    angular
        .module('carsOnRentApp')
        .controller('CoordinatesRentalDetailController', CoordinatesRentalDetailController);

    CoordinatesRentalDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Coordinates'];

    function CoordinatesRentalDetailController($scope, $rootScope, $stateParams, previousState, entity, Coordinates) {
        var vm = this;

        vm.coordinates = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('carsOnRentApp:coordinatesUpdate', function(event, result) {
            vm.coordinates = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
