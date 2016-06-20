/**
 * @author slesh
 */

angular
    .module('imaster')
    .service('documentService', documentService);

/** @ngInject */
function documentService($http, uploader) {
    'use strict';

    var self = {};

    self.uploader = uploader.create('/api/documents');

    return {
        getAllDocuments: getAllDocuments,
        getDocumentById: getDocumentById,
        addDocument: addDocument,
        updateDocument: updateDocument,
        getSubjects: getSubjects,
        uploader: self.uploader
    };

    function getAllDocuments() {
        return $http.get('/api/documents');
    }

    function addDocument(document) {
        return self.uploader.save(document);
    }

    function updateDocument(document) {
        return self.uploader.update(document);
    }

    function getDocumentById(id) {
        return $http.get('/api/documents/' + id);
    }

    function getSubjects() {
        return $http.get('/api/subjects', {cache: true});
    }
}
