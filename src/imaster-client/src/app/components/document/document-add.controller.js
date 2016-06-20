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
    vm.isAdd = true;
    vm.documentText = 'Документ';
    vm.submitText = 'Добавить документ';
    vm.document = {};

    (function () {
        documentCommonService.initContext(vm).then(function () {
            vm.document.subject = vm.subjects[0];
        });
    })();
}