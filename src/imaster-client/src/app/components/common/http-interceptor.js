/**
 * @author slesh
 */

/* jshint undef: false */

angular.module('imaster').factory('httpInterceptor', [
    '$injector', '$q', '$log',
    function ($injector, $q, $log) {
        'use strict';

        return {
            responseError: responseError
        };

        function responseError(config) {
            var responseDefer = $q.defer();
            $log.debug('error details ',
                ', status: ', config.status,
                ', status text: ', config.statusText,
                ', data:', config.data, ', ');
            switch (config.status) {
                case 401:
                case 403:
                case 500:
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