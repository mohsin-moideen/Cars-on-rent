(function() {
    'use strict';

    angular
        .module('carsOnRentApp')
        .controller('CarRentalDetailController', CarRentalDetailController);

    CarRentalDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Car', 'Provider', 'CarPrice', 'CarAttr', 'Availability'];

    function CarRentalDetailController($scope, $rootScope, $stateParams, previousState, entity, Car, Provider, CarPrice, CarAttr, Availability) {
        var vm = this;

        vm.car = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('carsOnRentApp:carUpdate', function(event, result) {
            vm.car = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
