/**
 * @author slesh
 */

angular
    .module('imaster')
    .service('documentService', documentService);

/** @ngInject */
function documentService($http) {
    'use strict';

    return {
        getAllDocuments: getAllDocuments,
        getDocumentById: getDocumentById,
        addDocument: addDocument,
        getSubjects: getSubjects
    };

    function getAllDocuments() {
        return $http.get('/api/documents');
    }

    function addDocument(document) {
        return $http.post('/api/documents', document);
    }

    function getDocumentById(id) {
        return $http.get('/api/documents/' + id);
    }

    function getSubjects(){
        return $http.get('/api/subjects');
    }
}
