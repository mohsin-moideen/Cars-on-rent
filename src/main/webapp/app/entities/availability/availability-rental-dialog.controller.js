(function() {
    'use strict';

    angular
        .module('carsOnRentApp')
        .controller('AvailabilityRentalDialogController', AvailabilityRentalDialogController);

    AvailabilityRentalDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Availability', 'Car'];

    function AvailabilityRentalDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Availability, Car) {
        var vm = this;

        vm.availability = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
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
            if (vm.availability.id !== null) {
                Availability.update(vm.availability, onSaveSuccess, onSaveError);
            } else {
                Availability.save(vm.availability, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('carsOnRentApp:availabilityUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.startDate = false;
        vm.datePickerOpenStatus.endDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
