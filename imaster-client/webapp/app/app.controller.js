/**
 * @author yauheni.putsykovich
 */

/* jshint undef: false */
angular.module('imaster').controller('AppController', [
    'AppService',
    function (AppService) {
        'use strict';
        var vm = this;
        vm.user = 'user';
    }]);