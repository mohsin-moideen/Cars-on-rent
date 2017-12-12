(function() {
    'use strict';

    angular
        .module('carsOnRentApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('location-rental', {
            parent: 'entity',
            url: '/location-rental',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'carsOnRentApp.location.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/location/locationsrental.html',
                    controller: 'LocationRentalController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('location');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('location-rental-detail', {
            parent: 'location-rental',
            url: '/location-rental/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'carsOnRentApp.location.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/location/location-rental-detail.html',
                    controller: 'LocationRentalDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('location');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Location', function($stateParams, Location) {
                    return Location.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'location-rental',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('location-rental-detail.edit', {
            parent: 'location-rental-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/location/location-rental-dialog.html',
                    controller: 'LocationRentalDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Location', function(Location) {
                            return Location.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('location-rental.new', {
            parent: 'location-rental',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/location/location-rental-dialog.html',
                    controller: 'LocationRentalDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                streetAddress: null,
                                postalCode: null,
                                city: null,
                                stateProvince: null,
                                countryName: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('location-rental', null, { reload: 'location-rental' });
                }, function() {
                    $state.go('location-rental');
                });
            }]
        })
        .state('location-rental.edit', {
            parent: 'location-rental',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/location/location-rental-dialog.html',
                    controller: 'LocationRentalDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Location', function(Location) {
                            return Location.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('location-rental', null, { reload: 'location-rental' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('location-rental.delete', {
            parent: 'location-rental',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/location/location-rental-delete-dialog.html',
                    controller: 'LocationRentalDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Location', function(Location) {
                            return Location.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('location-rental', null, { reload: 'location-rental' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
