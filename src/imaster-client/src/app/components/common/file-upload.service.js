/**
 * @author slesh
 */

angular
    .module('imaster')
    .service('uploader', uploader);

/** @ngInject */
function uploader(FileUploader, authService, httpInterceptor, $q, $log) {
    'use strict';

    return {
        create: create
    };

    function createOnSuccessListener(defer) {
        return function (item, response, status, headers) {
            $log.debug('status: ', status, ', response: ', response);
            defer.resolve(response);
        }
    }

    function createOnErrorListener(defer) {
        return function (item, response, status, headers) {
            $log.debug('status: ', status, ', response: ', response);
            defer.reject(response);
        }
    }

    function create(url) {
        var uploadDefer = null;
        var uploader = new FileUploader({url: url});
        uploader.removeAfterUpload = true;
        uploader.onErrorItem = function (item, response, status, headers) {
            httpInterceptor.responseError({data: item, status: status, statusText: response});
            uploadDefer.reject(response);
        };
        uploader.onSuccessItem = function (item, response, status, headers) {
            uploadDefer.resolve(response);
        };
        uploader.save = function (data) {
            uploadDefer = $q.defer();
            var item = uploader.queue[0];
            item.headers = {'X-Auth': authService.getToken()};
            item.formData = [{data: angular.toJson(data)}];
            item.upload();
            return uploadDefer.promise;
        };

        return uploader;
    }
}