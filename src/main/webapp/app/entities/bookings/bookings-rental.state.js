(function() {
    'use strict';

    angular
        .module('carsOnRentApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('bookings-rental', {
            parent: 'entity',
            url: '/bookings-rental',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'carsOnRentApp.bookings.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/bookings/bookingsrental.html',
                    controller: 'BookingsRentalController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('bookings');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('bookings-rental-detail', {
            parent: 'bookings-rental',
            url: '/bookings-rental/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'carsOnRentApp.bookings.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/bookings/bookings-rental-detail.html',
                    controller: 'BookingsRentalDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('bookings');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Bookings', function($stateParams, Bookings) {
                    return Bookings.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'bookings-rental',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('bookings-rental-detail.edit', {
            parent: 'bookings-rental-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/bookings/bookings-rental-dialog.html',
                    controller: 'BookingsRentalDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Bookings', function(Bookings) {
                            return Bookings.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('bookings-rental.new', {
            parent: 'bookings-rental',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/bookings/bookings-rental-dialog.html',
                    controller: 'BookingsRentalDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                startDate: null,
                                endDate: null,
                                status: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('bookings-rental', null, { reload: 'bookings-rental' });
                }, function() {
                    $state.go('bookings-rental');
                });
            }]
        })
        .state('bookings-rental.edit', {
            parent: 'bookings-rental',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/bookings/bookings-rental-dialog.html',
                    controller: 'BookingsRentalDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Bookings', function(Bookings) {
                            return Bookings.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('bookings-rental', null, { reload: 'bookings-rental' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('bookings-rental.delete', {
            parent: 'bookings-rental',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/bookings/bookings-rental-delete-dialog.html',
                    controller: 'BookingsRentalDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Bookings', function(Bookings) {
                            return Bookings.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('bookings-rental', null, { reload: 'bookings-rental' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
