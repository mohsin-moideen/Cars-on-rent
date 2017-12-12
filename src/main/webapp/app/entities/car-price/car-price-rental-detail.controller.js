(function() {
    'use strict';

    angular
        .module('carsOnRentApp')
        .controller('CarPriceRentalDetailController', CarPriceRentalDetailController);

    CarPriceRentalDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CarPrice'];

    function CarPriceRentalDetailController($scope, $rootScope, $stateParams, previousState, entity, CarPrice) {
        var vm = this;

        vm.carPrice = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('carsOnRentApp:carPriceUpdate', function(event, result) {
            vm.carPrice = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
