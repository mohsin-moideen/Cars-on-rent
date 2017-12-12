(function() {
    'use strict';

    angular
        .module('carsOnRentApp')
        .controller('AvailabilityRentalDetailController', AvailabilityRentalDetailController);

    AvailabilityRentalDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Availability', 'Car'];

    function AvailabilityRentalDetailController($scope, $rootScope, $stateParams, previousState, entity, Availability, Car) {
        var vm = this;

        vm.availability = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('carsOnRentApp:availabilityUpdate', function(event, result) {
            vm.availability = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
