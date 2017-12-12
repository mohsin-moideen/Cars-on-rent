(function() {
    'use strict';

    angular
        .module('carsOnRentApp')
        .controller('CoordinatesRentalDialogController', CoordinatesRentalDialogController);

    CoordinatesRentalDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Coordinates'];

    function CoordinatesRentalDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Coordinates) {
        var vm = this;

        vm.coordinates = entity;
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
            if (vm.coordinates.id !== null) {
                Coordinates.update(vm.coordinates, onSaveSuccess, onSaveError);
            } else {
                Coordinates.save(vm.coordinates, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('carsOnRentApp:coordinatesUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
