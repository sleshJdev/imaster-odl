/**
    * @author slesh
    */

angular.module('imaster').controller('EssayController', [
    'EssayService', 'FileUploader', '$log',
    function (EssayService, FileUploader, $log) {
        $log.debug('EssayController');

        var vm = this;

        vm.essay = {};
        var uploader = $scope.uploader = new FileUploader({
            url: '/api/students/essay'
        });

        vm.uploader = uploader;

        // FILTERS

        uploader.filters.push({
            name: 'customFilter',
            fn: function(item /*{File|FileLikeObject}*/, options) {
                return this.queue.length < 10;
            }
        });

        // CALLBACKS

        uploader.onWhenAddingFileFailed = function(item /*{File|FileLikeObject}*/, filter, options) {
            $log.info('onWhenAddingFileFailed', item, filter, options);
        };
        uploader.onAfterAddingFile = function(fileItem) {
            $log.info('onAfterAddingFile', fileItem);
        };
        uploader.onAfterAddingAll = function(addedFileItems) {
            $log.info('onAfterAddingAll', addedFileItems);
        };
        uploader.onBeforeUploadItem = function(item) {
            $log.info('onBeforeUploadItem', item);
        };
        uploader.onProgressItem = function(fileItem, progress) {
            $log.info('onProgressItem', fileItem, progress);
        };
        uploader.onProgressAll = function(progress) {
            $log.info('onProgressAll', progress);
        };
        uploader.onSuccessItem = function(fileItem, response, status, headers) {
            $log.info('onSuccessItem', fileItem, response, status, headers);
        };
        uploader.onErrorItem = function(fileItem, response, status, headers) {
            $log.info('onErrorItem', fileItem, response, status, headers);
        };
        uploader.onCancelItem = function(fileItem, response, status, headers) {
            $log.info('onCancelItem', fileItem, response, status, headers);
        };
        uploader.onCompleteItem = function(fileItem, response, status, headers) {
            $log.info('onCompleteItem', fileItem, response, status, headers);
        };
        uploader.onCompleteAll = function() {
            $log.info('onCompleteAll');
        };

        vm.save = function () {
            EssayService.addEssay(vm.essay);
        }
    }
]);