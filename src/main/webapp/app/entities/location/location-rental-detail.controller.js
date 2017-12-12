(function() {
    'use strict';

    angular
        .module('carsOnRentApp')
        .controller('LocationRentalDetailController', LocationRentalDetailController);

    LocationRentalDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Location', 'Coordinates'];

    function LocationRentalDetailController($scope, $rootScope, $stateParams, previousState, entity, Location, Coordinates) {
        var vm = this;

        vm.location = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('carsOnRentApp:locationUpdate', function(event, result) {
            vm.location = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
