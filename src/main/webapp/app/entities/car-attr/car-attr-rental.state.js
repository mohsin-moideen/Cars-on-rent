(function() {
    'use strict';

    angular
        .module('carsOnRentApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('car-attr-rental', {
            parent: 'entity',
            url: '/car-attr-rental',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'carsOnRentApp.carAttr.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/car-attr/car-attrsrental.html',
                    controller: 'CarAttrRentalController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('carAttr');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('car-attr-rental-detail', {
            parent: 'car-attr-rental',
            url: '/car-attr-rental/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'carsOnRentApp.carAttr.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/car-attr/car-attr-rental-detail.html',
                    controller: 'CarAttrRentalDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('carAttr');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'CarAttr', function($stateParams, CarAttr) {
                    return CarAttr.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'car-attr-rental',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('car-attr-rental-detail.edit', {
            parent: 'car-attr-rental-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/car-attr/car-attr-rental-dialog.html',
                    controller: 'CarAttrRentalDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CarAttr', function(CarAttr) {
                            return CarAttr.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('car-attr-rental.new', {
            parent: 'car-attr-rental',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/car-attr/car-attr-rental-dialog.html',
                    controller: 'CarAttrRentalDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                attrname: null,
                                attrval: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('car-attr-rental', null, { reload: 'car-attr-rental' });
                }, function() {
                    $state.go('car-attr-rental');
                });
            }]
        })
        .state('car-attr-rental.edit', {
            parent: 'car-attr-rental',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/car-attr/car-attr-rental-dialog.html',
                    controller: 'CarAttrRentalDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CarAttr', function(CarAttr) {
                            return CarAttr.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('car-attr-rental', null, { reload: 'car-attr-rental' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('car-attr-rental.delete', {
            parent: 'car-attr-rental',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/car-attr/car-attr-rental-delete-dialog.html',
                    controller: 'CarAttrRentalDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CarAttr', function(CarAttr) {
                            return CarAttr.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('car-attr-rental', null, { reload: 'car-attr-rental' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
