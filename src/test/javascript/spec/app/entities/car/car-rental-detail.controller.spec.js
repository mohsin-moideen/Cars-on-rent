'use strict';

describe('Controller Tests', function() {

    describe('Car Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockCar, MockProvider, MockCarPrice, MockCarAttr, MockAvailability;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockCar = jasmine.createSpy('MockCar');
            MockProvider = jasmine.createSpy('MockProvider');
            MockCarPrice = jasmine.createSpy('MockCarPrice');
            MockCarAttr = jasmine.createSpy('MockCarAttr');
            MockAvailability = jasmine.createSpy('MockAvailability');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Car': MockCar,
                'Provider': MockProvider,
                'CarPrice': MockCarPrice,
                'CarAttr': MockCarAttr,
                'Availability': MockAvailability
            };
            createController = function() {
                $injector.get('$controller')("CarRentalDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'carsOnRentApp:carUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
