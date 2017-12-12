(function() {
    'use strict';

    angular
        .module('carsOnRentApp')
        .controller('CarAttrRentalDeleteController',CarAttrRentalDeleteController);

    CarAttrRentalDeleteController.$inject = ['$uibModalInstance', 'entity', 'CarAttr'];

    function CarAttrRentalDeleteController($uibModalInstance, entity, CarAttr) {
        var vm = this;

        vm.carAttr = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CarAttr.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
