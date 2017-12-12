(function() {
    'use strict';

    angular
        .module('carsOnRentApp')
        .controller('BookingsRentalDialogController', BookingsRentalDialogController);

    BookingsRentalDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Bookings', 'Provider', 'User'];

    function BookingsRentalDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Bookings, Provider, User) {
        var vm = this;

        vm.bookings = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.providers = Provider.query();
        vm.users = User.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.bookings.id !== null) {
                Bookings.update(vm.bookings, onSaveSuccess, onSaveError);
            } else {
                Bookings.save(vm.bookings, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('carsOnRentApp:bookingsUpdate', result);
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
