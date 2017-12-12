(function() {
    'use strict';

    angular
        .module('carsOnRentApp')
        .controller('CarAttrRentalDetailController', CarAttrRentalDetailController);

    CarAttrRentalDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CarAttr', 'Car'];

    function CarAttrRentalDetailController($scope, $rootScope, $stateParams, previousState, entity, CarAttr, Car) {
        var vm = this;

        vm.carAttr = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('carsOnRentApp:carAttrUpdate', function(event, result) {
            vm.carAttr = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
