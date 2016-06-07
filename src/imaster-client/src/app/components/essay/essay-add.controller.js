/**
 * @author slesh
 */

angular
    .module('imaster')
    .controller('EssayAddController', EssayAddController);

/** @ngInject */
function EssayAddController(essayService, essayCommon, authService, $state, $log) {
    'use strict';

    var vm = this;
    vm.essay = {};
    vm.uploader = essayService.uploader;

    vm.save = function () {
        essayService.addEssay(vm.essay).then(function (response) {
            $log.debug("response: ", response);
            $state.go('documents.list');
        });
    };

    (function () {
        essayCommon.initContext(vm).then(function () {
            vm.essay.teacher = vm.teachers[0];
            vm.essay.status = vm.statuses[0];
        });

        vm.access = {
            status: authService.isTeacher()
        }
    })();
}