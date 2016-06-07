/**
 * @author slesh
 */

angular
    .module('imaster')
    .service('essayService', essayService);

/** @ngInject */
function essayService(uploader, $http, $q) {
    'use strict';

    var self = {};

    self.uploader = uploader.create('/api/essays');

    return {
        init: init,
        addEssay: addEssay,
        getEssayById: getEssayById,
        getAllEssays: getAllEssays,
        updateEssay: updateEssay,
        getTeachers: getTeachers,
        uploader: self.uploader
    };

    function init(scope) {
        return $q.all([
            getTeachers(),
            getStatuses()
        ]).then(function (responses) {
            scope.teachers = responses[0].data;
            scope.statuses = response[1].data;
        });
    }

    function addEssay(essay) {
        return self.uploader.save(essay);
    }

    function getAllEssays() {
        return $http.get('/api/essays');
    }

    function getEssayById(id) {
        return $http.get('/api/essays/' + id);
    }

    function updateEssay() {
        return $http.put('/api/essays');
    }

    function getTeachers() {
        return $http.get('/api/teachers');
    }

    function getStatuses() {
        return $http.get('/api/statuses');
    }
}