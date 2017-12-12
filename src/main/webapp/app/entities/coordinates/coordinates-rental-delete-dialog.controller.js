(function() {
    'use strict';

    angular
        .module('carsOnRentApp')
        .controller('CoordinatesRentalDeleteController',CoordinatesRentalDeleteController);

    CoordinatesRentalDeleteController.$inject = ['$uibModalInstance', 'entity', 'Coordinates'];

    function CoordinatesRentalDeleteController($uibModalInstance, entity, Coordinates) {
        var vm = this;

        vm.coordinates = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Coordinates.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
