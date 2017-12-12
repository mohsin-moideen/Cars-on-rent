(function() {
    'use strict';

    angular
        .module('carsOnRentApp')
        .controller('ProviderRentalDetailController', ProviderRentalDetailController);

    ProviderRentalDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Provider', 'Location', 'Car'];

    function ProviderRentalDetailController($scope, $rootScope, $stateParams, previousState, entity, Provider, Location, Car) {
        var vm = this;

        vm.provider = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('carsOnRentApp:providerUpdate', function(event, result) {
            vm.provider = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
