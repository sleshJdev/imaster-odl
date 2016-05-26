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

    init();

    function init() {
        authService.exportMethodsTo(vm);
    }
}