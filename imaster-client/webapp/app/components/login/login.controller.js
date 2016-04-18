/**
 * @author yauheni.putsykovich
 */

/* jshint undef: false */
angular.module('imaster').controller('LoginController', [
    'LoginService',
    function (LoginService) {
        'use strict';
        var vm = this;
        vm.login = function () {
            LoginService.login(vm.username, vm.password);
        };
    }]);