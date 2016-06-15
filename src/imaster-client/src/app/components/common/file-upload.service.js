/**
 * @author slesh
 */

angular
    .module('imaster')
    .service('uploader', uploader);

/** @ngInject */
function uploader(FileUploader, authService, httpInterceptor, $q) {
    'use strict';

    return {
        create: create
    };

    function create(url) {
        var uploadDefer = null;
        var uploader = new FileUploader({url: url});
        //uploader.removeAfterUpload = true;
        uploader.onErrorItem = function (item, response, status) {
            httpInterceptor.responseError({data: item, status: status, statusText: response});
            uploadDefer.reject(response);
        };
        uploader.onSuccessItem = function (item, response) {
            uploadDefer.resolve(response);
        };
        uploader.save = function (data) {
            uploadDefer = $q.defer();
            uploader.method = 'POST';
            var item = uploader.queue[0];
            item.headers = {'X-Auth': authService.getToken()};
            item.formData = [{data: angular.toJson(data)}];
            item.upload();
            return uploadDefer.promise;
        };
        uploader.update = function (data) {
            uploadDefer = $q.defer(data);
            uploader.method = 'PUT';
            var item = uploader.queue[0];
            item.headers = {'X-Auth': authService.getToken()};
            item.formData = [{data: angular.toJson(data)}];
            item.upload();
            return uploadDefer.promise;
        };

        return uploader;
    }
}