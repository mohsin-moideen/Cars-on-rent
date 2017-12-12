(function() {
    'use strict';

    angular
        .module('carsOnRentApp')
        .controller('CarRentalDialogController', CarRentalDialogController);

    CarRentalDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Car', 'Provider', 'CarPrice', 'CarAttr', 'Availability'];

    function CarRentalDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Car, Provider, CarPrice, CarAttr, Availability) {
        var vm = this;

        vm.car = entity;
        vm.clear = clear;
        vm.save = save;
        vm.providers = Provider.query();
        vm.carprices = CarPrice.query({filter: 'car-is-null'});
        $q.all([vm.car.$promise, vm.carprices.$promise]).then(function() {
            if (!vm.car.carPrice || !vm.car.carPrice.id) {
                return $q.reject();
            }
            return CarPrice.get({id : vm.car.carPrice.id}).$promise;
        }).then(function(carPrice) {
            vm.carprices.push(carPrice);
        });
        vm.carattrs = CarAttr.query();
        vm.availabilities = Availability.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.car.id !== null) {
                Car.update(vm.car, onSaveSuccess, onSaveError);
            } else {
                Car.save(vm.car, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('carsOnRentApp:carUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
