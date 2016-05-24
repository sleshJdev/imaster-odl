/**
 * @author slesh
 */

angular.module('imaster').service('EssayService', [
    'FileUploader', '$log',
    function (FileUploader, $log) {
        'use strict';

        $log.info('uploader', uploader);
        
        return {
            addEssay: addEssay
        };

        function addEssay(essay) {
        }
    }
]);