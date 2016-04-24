/**
 * @author yauheni.putsykovich
 */

/* jshint undef: false */
angular.module('imaster').controller('LoginController', [
    'AuthService', '$state', '$log',
    function (AuthService, $state, $log) {
        'use strict';

        var vm = this;

        vm.login = function () {
            AuthService.login(vm.username, vm.password).then(function () {
                $log.log("login successfully");
                $state.go("students");
            });
        };
    }]);