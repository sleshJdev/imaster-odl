/**
 * @author slesh
 */

angular
    .module('imaster')
    .controller('EditEditController', EditEditController);

/** @ngInject */
function EditEditController(essayService, essayCommonService, $state, $log, id) {
    'use strict';
    $log.debug('EditEditController. edit essay with id ', id);

    var vm = this;
    vm.essay = {};
    vm.submitText = 'Обновить реферат';
    vm.uploader = essayService.uploader;
    vm.submit = update;

    function update() {
        essayService.updateEssay(vm.essay).then(function (response) {
            $log.debug("response: ", response);
            $state.go('documents.list');
        });
    }

    (function () {
        essayCommonService.initContext(vm).then(function () {
            essayService.getEssayById(id).then(function (response) {
                $log.debug('response ', response.data);
                vm.essay = response.data;
            });
        });
    })();
}