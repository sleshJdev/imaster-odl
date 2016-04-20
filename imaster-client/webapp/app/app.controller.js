/**
 * @author yauheni.putsykovich
 */

/* jshint undef: false */
angular.module('imaster').controller('AppController', [
    'AuthService',
    function (AuthService) {
        'use strict';

        var vm = this;

        vm.isLoggedUser = function () {
            return AuthService.isAuthenticated();
        };

        vm.getUserName = function () {
            var auth = AuthService.getAuthentication();
            return auth ? auth.username : null;
        };
    }]);