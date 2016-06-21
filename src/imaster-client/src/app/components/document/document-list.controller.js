/**
 * @author yauheni.putsykovich
 */

angular
    .module('imaster')
    .controller('DocumentListController', DocumentListController);

/** @ngInject */
function DocumentListController(documentService, collection, $q) {
    'use strict';

    var vm = this;
    vm.remove = remove;

    function remove() {
        $q.all(vm.documentList
            .filter(collection.getChecked)
            .map(collection.getId)
            .map(documentService.removeDocument)
        ).then(getAllDocuments);
    }

    function getAllDocuments() {
        documentService.getAllDocuments().then(function (response) {
            vm.documentList = response.data;
        })
    }

    (function () {
        getAllDocuments();
    })();
}