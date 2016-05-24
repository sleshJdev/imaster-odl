/**
    * @author slesh
    */

angular.module('imaster').controller('StudentAddController', [
    'StudentService', '$state',
    function (StudentService, $state) {
        'use strict';

        var vm = this;
        vm.student = null;

        vm.save = function () {
            StudentService.addStudent(vm.student).then(function () {
                $state.go('students');
            });
        };
    }
]);