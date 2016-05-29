/**
 * @author slesh
 */

angular
    .module('imaster')
    .controller('EssayListController', EssayListController);

/** @ngInject */
function EssayListController(essayService, $log) {
    'use strict';
    $log.debug('EssayListController');
    var vm = this;
    vm.essayList = {};

    (function () {
        essayService.getAllEssays().then(function (response) {
            vm.essayList = response.data;
        });
    })();
}