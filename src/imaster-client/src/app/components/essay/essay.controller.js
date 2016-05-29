/**
 * @author slesh
 */

angular
    .module('imaster')
    .controller('EssayController', EssayController);

/** @ngInject */
function EssayController(essayService, $state, $log) {
    'use strict';

    var vm = this;
    vm.essay = {};
    vm.uploader = essayService.uploader;

    vm.save = function () {
        essayService.addEssay(vm.essay).then(function (response) {
            $log.debug("response: ", response);
            $state.go('students.list');
        });
    };

    (function () {
        essayService.getTeachers().then(function (response) {
            vm.teachers = response.data;
            vm.essay.teacher = vm.teachers[0];
        })
    })();
}