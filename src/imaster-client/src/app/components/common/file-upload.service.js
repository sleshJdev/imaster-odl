/**
 * @author slesh
 */

angular
    .module('imaster')
    .service('uploader', uploader);

/** @ngInject */
function uploader(FileUploader, authService, $log) {
    'use strict';

    return {
        create: create
    };

    function create(url) {
        var uploader = new FileUploader({
            url: url,
            withCredentials: true
        });
        uploader.onBeforeUploadItem = function (item) {
            $log.info('onBeforeUploadItem', item);
            item.headers = {'X-Auth': authService.getToken()};
            item.removeAfterUpload = true;
        };
        uploader.onSuccessItem = function (fileItem, response, status, headers) {
            $log.info('onSuccessItem', fileItem, response, status, headers);
        };
        uploader.onErrorItem = function (fileItem, response, status, headers) {
            $log.info('onErrorItem', fileItem, response, status, headers);
        };
        uploader.save = function (data) {
            var item = uploader.queue[0];
            item.formData.push({data: angular.toJson(data)});
            item.upload();
        };

        return uploader;
    }
}