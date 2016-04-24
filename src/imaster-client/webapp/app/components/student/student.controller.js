/**
 * @author yauheni.putsykovich
 */

/* jshint undef: false */
angular.module('imaster').controller('UserController', [
    'UserService', '$log',
    function (UserService, $log) {
        'use strict';

        var vm = this;

        UserService.getAllUsers().then(function (response) {
            $log.log('response: ', response);
            vm.userList = response.data;
        });
    }
]);