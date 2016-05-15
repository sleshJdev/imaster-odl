/**
 * @author yauheni.putsykovich
 */

/* jshint undef: false */
angular.module('imaster').controller('NavController', [
    'AuthService', '$state',
    function (AuthService, $state) {
        'use strict';
        var vm = this;

        vm.logout = function () {
            AuthService.logout();
            $state.go('login');
        };

        init();

        function init() {
            AuthService.exportMethodsTo(vm);
        }
    }]);