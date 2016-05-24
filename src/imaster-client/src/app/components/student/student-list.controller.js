/**
 * @author yauheni.putsykovich
 */

angular.module('imaster').controller('StudentListController', [
    'StudentService',
    function (StudentService) {
        'use strict';

        var vm = this;

        (function () {
            StudentService.getAllStudents().then(function (response) {
                vm.studentList = response.data;
            });
        })();
    }
]);