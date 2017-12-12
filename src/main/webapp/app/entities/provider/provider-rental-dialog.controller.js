(function() {
    'use strict';

    angular
        .module('carsOnRentApp')
        .controller('ProviderRentalDialogController', ProviderRentalDialogController);

    ProviderRentalDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Provider', 'Location', 'Car'];

    function ProviderRentalDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Provider, Location, Car) {
        var vm = this;

        vm.provider = entity;
        vm.clear = clear;
        vm.save = save;
        vm.locations = Location.query({filter: 'provider-is-null'});
        $q.all([vm.provider.$promise, vm.locations.$promise]).then(function() {
            if (!vm.provider.location || !vm.provider.location.id) {
                return $q.reject();
            }
            return Location.get({id : vm.provider.location.id}).$promise;
        }).then(function(location) {
            vm.locations.push(location);
        });
        vm.cars = Car.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.provider.id !== null) {
                Provider.update(vm.provider, onSaveSuccess, onSaveError);
            } else {
                Provider.save(vm.provider, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('carsOnRentApp:providerUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
