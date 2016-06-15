/**
 * @author slesh
 */

angular
    .module('imaster')
    .controller('DocumentAddController', DocumentAddController);

/** @ngInject */
function DocumentAddController(documentCommonService) {
    'use strict';

    var vm = this;
    vm.submitText = 'Добавить документ';
    vm.student = null;

    (function () {
        documentCommonService.initContext(vm);
    })();
}