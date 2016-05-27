/**
 * @author slesh
 */

angular
    .module('imaster')
    .factory('httpInterceptor', httpInterceptor);

/** @ngInject */
function httpInterceptor($injector, $q, $log) {
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
                var authService = $injector.get('AuthService');
                if (authService.isAuthenticated()) {
                    authService.logout();
                }
                $injector.get('$state').go('login');
                responseDefer.reject(config);
                break;
            default:
                responseDefer.resolve(config);
        }
        return responseDefer.promise;
    }
}