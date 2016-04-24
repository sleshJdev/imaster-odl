/**
    * @author slesh
    */

/* jshint undef: false */
angular.module('imaster').controller('StudentAddController', [
    'StudentService', '$state', '$log',
    function (StudentService, $state, $log) {
        'use strict';

        var vm = this;

        vm.student = {};

        vm.save = function () {
            StudentService.addStudent(vm.student).then(function (response) {
                $log.log('add student response: ', response);
                $state.go('students');
            });
        };
    }
]);