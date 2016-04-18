/**
 * @author yauheni.putsykovich
 */

/* jshint undef: false */
angular.module('imaster').service('LoginService', [
    '$http',
    function ($http) {
        'use strict';
        var vm = this;
        vm.login = function (username, password) {
            return $http.post('/api/login', {username: username, password: password});
        };
    }
]);