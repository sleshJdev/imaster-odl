/**
 * @author slesh
 */

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
        getTeachers: getTeachers,
        uploader: self.uploader
    };

    function addEssay(essay) {
        return self.uploader.save(essay);
    }

    function getTeachers(){
        return $http.get('/api/teachers');
    }
}