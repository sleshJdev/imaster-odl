/**
 * @author slesh
 */

angular.module('imaster').controller('StudentEditController', [
    'StudentService', '$state', 'id', '$log',
    function (StudentService, $state, id, $log) {
        'use strict';
        $log.debug('edit student, id: ', id);

        var vm = this;
        vm.student = null;
        
        vm.save = function () {
            StudentService.updateStudent(vm.student).then(function () {
                $state.go('students');
            });
        };

        (function () {
            StudentService.getStudentById(id).then(function (response) {
                vm.student = response.data;
            });
        })();
    }
]);