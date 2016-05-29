/**
 * @author yauheni.putsykovich
 */

angular
    .module('imaster')
    .controller('DocumentListController', DocumentListController);

/** @ngInject */
function DocumentListController(documentService) {
    'use strict';

    var vm = this;

    (function () {
        documentService.getAllDocuments().then(function (response) {
            vm.documentList = response.data;
        });
    })();
}