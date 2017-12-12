(function() {
    'use strict';

    angular
        .module('carsOnRentApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('coordinates-rental', {
            parent: 'entity',
            url: '/coordinates-rental',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'carsOnRentApp.coordinates.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/coordinates/coordinatesrental.html',
                    controller: 'CoordinatesRentalController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('coordinates');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('coordinates-rental-detail', {
            parent: 'coordinates-rental',
            url: '/coordinates-rental/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'carsOnRentApp.coordinates.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/coordinates/coordinates-rental-detail.html',
                    controller: 'CoordinatesRentalDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('coordinates');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Coordinates', function($stateParams, Coordinates) {
                    return Coordinates.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'coordinates-rental',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('coordinates-rental-detail.edit', {
            parent: 'coordinates-rental-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/coordinates/coordinates-rental-dialog.html',
                    controller: 'CoordinatesRentalDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Coordinates', function(Coordinates) {
                            return Coordinates.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('coordinates-rental.new', {
            parent: 'coordinates-rental',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/coordinates/coordinates-rental-dialog.html',
                    controller: 'CoordinatesRentalDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                lognitude: null,
                                latitude: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('coordinates-rental', null, { reload: 'coordinates-rental' });
                }, function() {
                    $state.go('coordinates-rental');
                });
            }]
        })
        .state('coordinates-rental.edit', {
            parent: 'coordinates-rental',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/coordinates/coordinates-rental-dialog.html',
                    controller: 'CoordinatesRentalDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Coordinates', function(Coordinates) {
                            return Coordinates.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('coordinates-rental', null, { reload: 'coordinates-rental' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('coordinates-rental.delete', {
            parent: 'coordinates-rental',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/coordinates/coordinates-rental-delete-dialog.html',
                    controller: 'CoordinatesRentalDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Coordinates', function(Coordinates) {
                            return Coordinates.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('coordinates-rental', null, { reload: 'coordinates-rental' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
