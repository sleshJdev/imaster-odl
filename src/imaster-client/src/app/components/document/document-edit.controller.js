/**
 * @author slesh
 */

angular
    .module('imaster')
    .controller('DocumentAddController', DocumentAddController);

/** @ngInject */
function DocumentAddController(documentService, $state) {
    'use strict';

    var vm = this;
    vm.student = null;

    vm.save = function () {
        documentService.addDocument(vm.student).then(function () {
            $state.go('documents');
        });
    };
}