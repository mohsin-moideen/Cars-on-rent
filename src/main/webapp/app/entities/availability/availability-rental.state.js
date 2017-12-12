(function() {
    'use strict';

    angular
        .module('carsOnRentApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('availability-rental', {
            parent: 'entity',
            url: '/availability-rental',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'carsOnRentApp.availability.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/availability/availabilitiesrental.html',
                    controller: 'AvailabilityRentalController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('availability');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('availability-rental-detail', {
            parent: 'availability-rental',
            url: '/availability-rental/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'carsOnRentApp.availability.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/availability/availability-rental-detail.html',
                    controller: 'AvailabilityRentalDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('availability');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Availability', function($stateParams, Availability) {
                    return Availability.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'availability-rental',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('availability-rental-detail.edit', {
            parent: 'availability-rental-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/availability/availability-rental-dialog.html',
                    controller: 'AvailabilityRentalDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Availability', function(Availability) {
                            return Availability.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('availability-rental.new', {
            parent: 'availability-rental',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/availability/availability-rental-dialog.html',
                    controller: 'AvailabilityRentalDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                startDate: null,
                                endDate: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('availability-rental', null, { reload: 'availability-rental' });
                }, function() {
                    $state.go('availability-rental');
                });
            }]
        })
        .state('availability-rental.edit', {
            parent: 'availability-rental',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/availability/availability-rental-dialog.html',
                    controller: 'AvailabilityRentalDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Availability', function(Availability) {
                            return Availability.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('availability-rental', null, { reload: 'availability-rental' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('availability-rental.delete', {
            parent: 'availability-rental',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/availability/availability-rental-delete-dialog.html',
                    controller: 'AvailabilityRentalDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Availability', function(Availability) {
                            return Availability.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('availability-rental', null, { reload: 'availability-rental' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
