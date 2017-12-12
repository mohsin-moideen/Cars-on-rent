(function() {
    'use strict';

    angular
        .module('carsOnRentApp')
        .controller('LocationRentalDialogController', LocationRentalDialogController);

    LocationRentalDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Location', 'Coordinates'];

    function LocationRentalDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Location, Coordinates) {
        var vm = this;

        vm.location = entity;
        vm.clear = clear;
        vm.save = save;
        vm.coordinates = Coordinates.query({filter: 'location-is-null'});
        $q.all([vm.location.$promise, vm.coordinates.$promise]).then(function() {
            if (!vm.location.coordinates || !vm.location.coordinates.id) {
                return $q.reject();
            }
            return Coordinates.get({id : vm.location.coordinates.id}).$promise;
        }).then(function(coordinates) {
            vm.coordinates.push(coordinates);
        });

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.location.id !== null) {
                Location.update(vm.location, onSaveSuccess, onSaveError);
            } else {
                Location.save(vm.location, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('carsOnRentApp:locationUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
