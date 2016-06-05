/**
 * @author yauheni.putsykovich
 */

    angular
        .module('imaster')
        .controller('LoginController', LoginController);

    /** @ngInject */
    function LoginController(authService, $state) {
        'use strict';
        var vm = this;

        vm.login = function () {
            authService.login(vm.username, vm.password).then(function () {
                if (authService.isStudent()) {
                    $state.go("documents.list");
                } else {
                    $state.go("students.list");
                }
                authService.endLogging();
            });
        };

        vm.cancel = function () {
            authService.endLogging();
            $state.go('/');
        };

        (function () {
            authService.startLogging();
        })();
    }