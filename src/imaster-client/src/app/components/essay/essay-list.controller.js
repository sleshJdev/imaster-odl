/**
 * @author slesh
 */

angular
    .module('imaster')
    .controller('EssayListController', EssayListController);

/** @ngInject */
function EssayListController(essayService, collection, $q, $log) {
    'use strict';
    $log.debug('EssayListController');
    var vm = this;
    vm.remove = remove;
    vm.essayList = {};

    function remove() {
        $q.all(vm.essayList.filter(collection.getChecked).map(essayService.remove)).then(function () {
            essayService.getAllEssays().then(function (response) {
                vm.essayList = response.data;
            });
        });
    }

    (function () {
        essayService.getAllEssays().then(function (response) {
            vm.essayList = response.data;
        });
    })();
}