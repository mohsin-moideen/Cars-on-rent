(function() {
    'use strict';

    angular
        .module('carsOnRentApp')
        .controller('CarPriceRentalDeleteController',CarPriceRentalDeleteController);

    CarPriceRentalDeleteController.$inject = ['$uibModalInstance', 'entity', 'CarPrice'];

    function CarPriceRentalDeleteController($uibModalInstance, entity, CarPrice) {
        var vm = this;

        vm.carPrice = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CarPrice.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
