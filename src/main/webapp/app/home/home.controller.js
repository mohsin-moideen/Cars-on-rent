(function() {
    'use strict';

    var module = angular.module('carsOnRentApp');
    module.controller('HomeController', HomeController);
    module.controller('FormController', FormController);

    HomeController.$inject = ['$scope', 'Principal', 'LoginService', '$state'];
    FormController.$inject = ['$scope'];

    function HomeController ($scope, Principal, LoginService, $state) {
        var vm = this;

        vm.account = null;
        vm.isAuthenticated = null;
        vm.login = LoginService.open;
        vm.register = register;
        $scope.$on('authenticationSuccess', function() {
            getAccount();
        });

        getAccount();

        function getAccount() {
            Principal.identity().then(function(account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
            });
        }
        function register () {
            $state.go('register');
        }
    }
    
    function FormController ($scope ){
    	
    }
})();
