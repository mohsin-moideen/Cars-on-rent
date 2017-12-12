(function() {
    'use strict';

    angular
        .module('carsOnRentApp')
        .controller('ProviderRentalDeleteController',ProviderRentalDeleteController);

    ProviderRentalDeleteController.$inject = ['$uibModalInstance', 'entity', 'Provider'];

    function ProviderRentalDeleteController($uibModalInstance, entity, Provider) {
        var vm = this;

        vm.provider = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Provider.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
