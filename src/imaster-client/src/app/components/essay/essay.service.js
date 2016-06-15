/**
 * @author slesh
 */

(function () {
    angular
        .module('imaster')
        .service('essayService', essayService);

    /** @ngInject */
    function essayService(uploader, $http) {
        'use strict';

        var self = {};

        self.uploader = uploader.create('/api/essays');

        return {
            addEssay: addEssay,
            remove: remove,
            getEssayById: getEssayById,
            getAllEssays: getAllEssays,
            updateEssay: updateEssay,
            getTeachers: getTeachers,
            getStatuses: getStatuses,
            uploader: self.uploader
        };

        function addEssay(essay) {
            return self.uploader.save(essay);
        }

        function remove(essay) {
            return $http.delete("/api/essays/" + essay.id);
        }

        function getAllEssays() {
            return $http.get('/api/essays');
        }

        function getEssayById(id) {
            return $http.get('/api/essays/' + id);
        }

        function updateEssay(essay) {
            return self.uploader.update(essay);
        }

        function getTeachers() {
            return $http.get('/api/teachers/public');
        }

        function getStatuses() {
            return $http.get('/api/statuses');
        }
    }
})();