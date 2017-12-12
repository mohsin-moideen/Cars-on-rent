(function() {
    'use strict';

    angular
        .module('carsOnRentApp')
        .controller('CarAttrRentalController', CarAttrRentalController);

    CarAttrRentalController.$inject = ['CarAttr', 'CarAttrSearch'];

    function CarAttrRentalController(CarAttr, CarAttrSearch) {

        var vm = this;

        vm.carAttrs = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            CarAttr.query(function(result) {
                vm.carAttrs = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            CarAttrSearch.query({query: vm.searchQuery}, function(result) {
                vm.carAttrs = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
