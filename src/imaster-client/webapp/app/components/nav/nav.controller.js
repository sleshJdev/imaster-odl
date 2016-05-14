/**
 * @author yauheni.putsykovich
 */

/* jshint undef: false */
angular.module('imaster').controller('NavController', [
    'AuthService', '$state',
    function (AuthService, $state) {
        'use strict';
        var vm = this;
        AuthService.addMethods(vm);
        vm.logout = function () {
            AuthService.logout();
            $state.go('login');
        };
    }]);