(function() {
    'use strict';

    angular
        .module('carsOnRentApp')
        .controller('CarPriceRentalDialogController', CarPriceRentalDialogController);

    CarPriceRentalDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CarPrice'];

    function CarPriceRentalDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CarPrice) {
        var vm = this;

        vm.carPrice = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.carPrice.id !== null) {
                CarPrice.update(vm.carPrice, onSaveSuccess, onSaveError);
            } else {
                CarPrice.save(vm.carPrice, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('carsOnRentApp:carPriceUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
