/**
 * @author slesh
 */

/* jshint undef: false */

angular.module('imaster').factory('httpInterceptor', [
    '$injector', '$q',
    function ($injector, $q) {
        'use strict';

        return {
            responseError: responseError
        };

        function responseError(config) {
            var responseDefer = $q.defer();
            switch (config.status) {
                case 401:
                case 403:
                    var AuthService = $injector.get('AuthService');
                    if (AuthService.isAuthenticated()) {
                        AuthService.logout();
                    }
                    $injector.get('$state').transitionTo('login');
                    responseDefer.reject(config);
                    break;
                default:
                    responseDefer.resolve(config);
            }
            return responseDefer.promise;
        }
    }
]);