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
            getTeachers().then(function (response) {
                scope.teachers = response.data;
                scope.teachers.forEach(function (teacher) {
                    teacher.fullName = teacher.firstName + ' ' + teacher.lastName + ' ' + teacher.patronymic;
                });
            }),
            getStatuses().then(function (response) {
                scope.statuses = response.data;
            })
        ]);
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