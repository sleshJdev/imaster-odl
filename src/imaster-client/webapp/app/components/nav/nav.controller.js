/**
 * @author yauheni.putsykovich
 */

/* jshint undef: false */
angular.module('imaster').controller('NavController', [
    'AuthService', '$state',
    function (AuthService, $state) {
        'use strict';
        var vm = this;

        vm.isLoggedUser = function () {
            return AuthService.isAuthenticated();
        };

        vm.getUserName = function () {
            var auth = AuthService.getAuthentication();
            return auth ? auth.username : null;
        };

        vm.logout = function () {
            AuthService.logout();
            $state.go('login');
        };
    }]);