/**
 * @author yauheni.putsykovich
 */

angular.module('imaster').controller('LoginController', [
    'AuthService', '$state', '$log',
    function (AuthService, $state, $log) {
        'use strict';
        var vm = this;

        vm.login = function () {
            AuthService.login(vm.username, vm.password).then(function () {
                $log.log("login successfully");
                $state.go("students");
                AuthService.endLogging();
            });
        };

        vm.cancel = function () {
            AuthService.endLogging();
            $state.go('/');
        };

        init();

        function init() {
            AuthService.startLogging();
        }
    }]);