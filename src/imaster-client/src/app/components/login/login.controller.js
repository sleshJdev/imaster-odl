/**
 * @author yauheni.putsykovich
 */

    angular
        .module('imaster')
        .controller('LoginController', LoginController);

    /** @ngInject */
    function LoginController(authService, roleService, $state) {
        'use strict';
        var vm = this;

        vm.userInfo = {
            username: null,
            password: null,
            loginAs: null
        };

        vm.login = function () {
            authService.login(vm.userInfo).then(function () {
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
            roleService.getAllRoles().then(function (response) {
                vm.roles = response.data;
                vm.userInfo.loginAs = vm.roles[0].name;
            });
        })();
    }