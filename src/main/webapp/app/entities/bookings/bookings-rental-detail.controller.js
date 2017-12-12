(function() {
    'use strict';

    angular
        .module('carsOnRentApp')
        .controller('BookingsRentalDetailController', BookingsRentalDetailController);

    BookingsRentalDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Bookings', 'Provider', 'User'];

    function BookingsRentalDetailController($scope, $rootScope, $stateParams, previousState, entity, Bookings, Provider, User) {
        var vm = this;

        vm.bookings = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('carsOnRentApp:bookingsUpdate', function(event, result) {
            vm.bookings = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
