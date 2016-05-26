/**
 * @author slesh
 */

angular
    .module('imaster')
    .controller('EssayController', EssayController);

/** @ngInject */
function EssayController(essayService) {
    'use strict';

    var vm = this;
    vm.essay = {};
    vm.uploader = essayService.uploader;

    vm.save = function () {
        essayService.addEssay(vm.essay);
    };
}