/**
 * @author yauheni.putsykovich
 */

/* jshint undef: false */
angular
    .module('imaster')
    .controller('NavController', NavController);

/** @ngInject */
function NavController(authService, $state) {
    'use strict';
    var vm = this;

    vm.logout = function () {
        authService.logout();
        $state.go('login');
    };

    vm.getPersonalInfo = function () {
        var authData = authService.getAuthentication();
        if (authData && authData.personalInfoJson) {
            return authData.personalInfoJson;
        }
    };

    (function () {
        authService.exportMethodsTo(vm);
    })();
}