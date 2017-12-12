(function() {
    'use strict';

    angular
        .module('carsOnRentApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('car-price-rental', {
            parent: 'entity',
            url: '/car-price-rental',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'carsOnRentApp.carPrice.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/car-price/car-pricesrental.html',
                    controller: 'CarPriceRentalController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('carPrice');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('car-price-rental-detail', {
            parent: 'car-price-rental',
            url: '/car-price-rental/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'carsOnRentApp.carPrice.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/car-price/car-price-rental-detail.html',
                    controller: 'CarPriceRentalDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('carPrice');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'CarPrice', function($stateParams, CarPrice) {
                    return CarPrice.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'car-price-rental',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('car-price-rental-detail.edit', {
            parent: 'car-price-rental-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/car-price/car-price-rental-dialog.html',
                    controller: 'CarPriceRentalDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CarPrice', function(CarPrice) {
                            return CarPrice.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('car-price-rental.new', {
            parent: 'car-price-rental',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/car-price/car-price-rental-dialog.html',
                    controller: 'CarPriceRentalDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                pricePerHour: null,
                                depositAmount: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('car-price-rental', null, { reload: 'car-price-rental' });
                }, function() {
                    $state.go('car-price-rental');
                });
            }]
        })
        .state('car-price-rental.edit', {
            parent: 'car-price-rental',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/car-price/car-price-rental-dialog.html',
                    controller: 'CarPriceRentalDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CarPrice', function(CarPrice) {
                            return CarPrice.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('car-price-rental', null, { reload: 'car-price-rental' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('car-price-rental.delete', {
            parent: 'car-price-rental',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/car-price/car-price-rental-delete-dialog.html',
                    controller: 'CarPriceRentalDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CarPrice', function(CarPrice) {
                            return CarPrice.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('car-price-rental', null, { reload: 'car-price-rental' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
