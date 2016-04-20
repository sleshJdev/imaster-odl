/**
 * @author yauheni.putsykovich
 */

/* jshint undef: false */
angular.module('imaster').controller('LoginController', [
    'AuthService', '$location',
    function (AuthService, $location) {
        'use strict';
        var vm = this;
        vm.login = function () {
            AuthService.login(vm.username, vm.password).then(function () {
                $location.path("/");
            });
        };
    }]);