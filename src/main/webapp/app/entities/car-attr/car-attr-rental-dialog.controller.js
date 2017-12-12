(function() {
    'use strict';

    angular
        .module('carsOnRentApp')
        .controller('CarAttrRentalDialogController', CarAttrRentalDialogController);

    CarAttrRentalDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CarAttr', 'Car'];

    function CarAttrRentalDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CarAttr, Car) {
        var vm = this;

        vm.carAttr = entity;
        vm.clear = clear;
        vm.save = save;
        vm.cars = Car.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.carAttr.id !== null) {
                CarAttr.update(vm.carAttr, onSaveSuccess, onSaveError);
            } else {
                CarAttr.save(vm.carAttr, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('carsOnRentApp:carAttrUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
