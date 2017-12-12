(function() {
    'use strict';

    angular
        .module('carsOnRentApp')
        .controller('CarPriceRentalController', CarPriceRentalController);

    CarPriceRentalController.$inject = ['CarPrice', 'CarPriceSearch'];

    function CarPriceRentalController(CarPrice, CarPriceSearch) {

        var vm = this;

        vm.carPrices = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            CarPrice.query(function(result) {
                vm.carPrices = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            CarPriceSearch.query({query: vm.searchQuery}, function(result) {
                vm.carPrices = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
