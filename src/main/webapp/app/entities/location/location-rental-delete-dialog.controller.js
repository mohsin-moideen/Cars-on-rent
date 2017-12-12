(function() {
    'use strict';

    angular
        .module('carsOnRentApp')
        .controller('LocationRentalDeleteController',LocationRentalDeleteController);

    LocationRentalDeleteController.$inject = ['$uibModalInstance', 'entity', 'Location'];

    function LocationRentalDeleteController($uibModalInstance, entity, Location) {
        var vm = this;

        vm.location = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Location.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
