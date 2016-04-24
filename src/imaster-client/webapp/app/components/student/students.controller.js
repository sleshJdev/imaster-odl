/**
 * @author yauheni.putsykovich
 */

/* jshint undef: false */
angular.module('imaster').controller('StudentController', [
    'StudentService',
    function (StudentService) {
        'use strict';

        var vm = this;

        StudentService.getAllStudents().then(function (response) {
            vm.studentList = response.data;
        });
    }
]);