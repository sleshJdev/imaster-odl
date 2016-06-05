/**
 * @author slesh
 */

angular
    .module('imaster')
    .controller('EditEditController', EditEditController);

/** @ngInject */
function EditEditController(essayService, $state, $log, id) {
    'use strict';
    $log.debug('EditEditController. edit essay with id ', id);

    var vm = this;
    vm.essay = {};
    vm.uploader = essayService.uploader;

    vm.save = function () {
        essayService.updateEssay(vm.essay).then(function (response) {
            $log.debug("response: ", response);
            $state.go('documents.list');
        });
    };

    (function () {
        essayService.init(vm).then(function () {
            essayService.getEssayById(id).then(function (response) {
                $log.debug('response ', response.data);
                vm.essay = response.data;
            });
        });
    })();
}