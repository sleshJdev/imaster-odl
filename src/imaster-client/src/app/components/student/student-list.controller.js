/**
 * @author yauheni.putsykovich
 */

angular
    .module('imaster')
    .controller('StudentListController', StudentListController);

/** @ngInject */
function StudentListController(studentService) {
    'use strict';

    var vm = this;

    (function () {
        studentService.getAllStudents().then(function (response) {
            vm.studentList = response.data;
        });
    })();
}