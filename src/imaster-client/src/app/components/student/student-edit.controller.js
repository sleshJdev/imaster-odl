/**
 * @author slesh
 */

angular
    .module('imaster')
    .controller('StudentEditController', StudentEditController);

/** @ngInject */
function StudentEditController(studentService, $state, id, $log) {
    'use strict';
    $log.debug('edit student, id: ', id);

    var vm = this;
    vm.student = null;

    vm.save = function () {
        studentService.updateStudent(vm.student).then(function () {
            $state.go('students');
        });
    };

    (function () {
        studentService.getStudentById(id).then(function (response) {
            vm.student = response.data;
        });
    })();
}