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

    (function () {
        authService.exportMethodsTo(vm);
        var authData = authService.getAuthentication();
        if(authData && authData.personalInfoJson) {
            vm.personalInfo = authData.personalInfoJson;
        }
    })();
}