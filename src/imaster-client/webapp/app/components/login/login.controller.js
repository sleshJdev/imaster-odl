/**
 * @author yauheni.putsykovich
 */

/* jshint undef: false */
angular.module('imaster').controller('LoginController', [
    'AuthService', '$state', '$log',
    function (AuthService, $state, $log) {
        'use strict';
        var vm = this;
        AuthService.isLogging = true;
        vm.login = function () {
            AuthService.login(vm.username, vm.password).then(function () {
                $log.log("login successfully");
                $state.go("students");
            });
            AuthService.isLogging = false;
        };
        vm.cancel = function () {
            AuthService.isLogging = false;
            $state.go('/');
        };
    }]);