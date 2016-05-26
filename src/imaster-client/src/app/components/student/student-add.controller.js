/**
 * @author slesh
 */

angular
    .module('imaster')
    .controller('StudentAddController', StudentAddController);

/** @ngInject */
function StudentAddController(studentService, $state) {
    'use strict';

    var vm = this;
    vm.student = null;

    vm.save = function () {
        studentService.addStudent(vm.student).then(function () {
            $state.go('students');
        });
    };
}