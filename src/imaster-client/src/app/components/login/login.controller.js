/**
 * @author yauheni.putsykovich
 */

angular
    .module('imaster')
    .controller('LoginController', LoginController);

/** @ngInject */
function LoginController(authService, $state, $log) {
    'use strict';
    var vm = this;

    vm.login = function () {
        authService.login(vm.username, vm.password).then(function () {
            $log.log("login successfully");
            if (authService.isStudent()) {
                $log.debug("student");
                $state.go("documents.list");
            } else {
                $log.debug("teacher");
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