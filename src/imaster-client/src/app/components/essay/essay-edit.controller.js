/**
 * @author slesh
 */

angular
    .module('imaster')
    .controller('EditAddController', EditAddController);

/** @ngInject */
function EditAddController(essayService, $state, $log) {
    'use strict';

    var vm = this;
    vm.essay = {};
    vm.uploader = essayService.uploader;

    vm.save = function () {
        essayService.updateEssay(vm.essay).then(function (response) {
            $log.debug("response: ", response);
        });
    };

    (function () {

    })();
}