(function() {
    'use strict';

    angular
        .module('carsOnRentApp')
        .controller('CoordinatesRentalController', CoordinatesRentalController);

    CoordinatesRentalController.$inject = ['Coordinates', 'CoordinatesSearch'];

    function CoordinatesRentalController(Coordinates, CoordinatesSearch) {

        var vm = this;

        vm.coordinates = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            Coordinates.query(function(result) {
                vm.coordinates = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            CoordinatesSearch.query({query: vm.searchQuery}, function(result) {
                vm.coordinates = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
