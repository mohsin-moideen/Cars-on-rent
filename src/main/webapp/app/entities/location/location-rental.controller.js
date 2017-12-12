(function() {
    'use strict';

    angular
        .module('carsOnRentApp')
        .controller('LocationRentalController', LocationRentalController);

    LocationRentalController.$inject = ['Location', 'LocationSearch'];

    function LocationRentalController(Location, LocationSearch) {

        var vm = this;

        vm.locations = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            Location.query(function(result) {
                vm.locations = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            LocationSearch.query({query: vm.searchQuery}, function(result) {
                vm.locations = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
