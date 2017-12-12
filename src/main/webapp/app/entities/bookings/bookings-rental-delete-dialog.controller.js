(function() {
    'use strict';

    angular
        .module('carsOnRentApp')
        .controller('BookingsRentalDeleteController',BookingsRentalDeleteController);

    BookingsRentalDeleteController.$inject = ['$uibModalInstance', 'entity', 'Bookings'];

    function BookingsRentalDeleteController($uibModalInstance, entity, Bookings) {
        var vm = this;

        vm.bookings = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Bookings.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
