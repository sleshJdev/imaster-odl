/**
 * @author slesh
 */

angular
    .module('imaster')
    .service('essayService', essayService);

/** @ngInject */
function essayService(uploader) {
    'use strict';

    var self = {};

    self.uploader = uploader.create('/api/essays');

    return {
        addEssay: addEssay,
        uploader: self.uploader
    };

    function addEssay(essay) {
        self.uploader.save(essay);
    }
}