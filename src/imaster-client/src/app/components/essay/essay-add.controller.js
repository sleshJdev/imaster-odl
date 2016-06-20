/**
 * @author slesh
 */

angular
    .module('imaster')
    .controller('EssayAddController', EssayAddController);

/** @ngInject */
function EssayAddController(essayService, essayCommonService, $state, $log) {
    'use strict';

    var vm = this;
    vm.submitText = 'Создать реферат';
    vm.essay = {};
    vm.uploader = essayService.uploader;
    vm.submit = save;
    vm.cancel = essayCommonService.cancel;

    function save() {
        essayService.addEssay(vm.essay).then(vm.cancel);
    }

    (function () {
        essayCommonService.initContext(vm).then(function () {
            vm.essay.teacher = vm.teachers[0];
            vm.essay.status = vm.statuses[0];
        });
    })();
}