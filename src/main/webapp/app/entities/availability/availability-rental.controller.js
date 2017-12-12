(function() {
    'use strict';

    angular
        .module('carsOnRentApp')
        .controller('AvailabilityRentalController', AvailabilityRentalController);

    AvailabilityRentalController.$inject = ['Availability', 'AvailabilitySearch'];

    function AvailabilityRentalController(Availability, AvailabilitySearch) {

        var vm = this;

        vm.availabilities = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            Availability.query(function(result) {
                vm.availabilities = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            AvailabilitySearch.query({query: vm.searchQuery}, function(result) {
                vm.availabilities = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
