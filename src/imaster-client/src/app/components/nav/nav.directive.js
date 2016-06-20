/**
 * @author slesh
 */

angular
    .module('imaster')
    .directive("nav", nav);

/** @ngInject */
function nav() {
    'use strict';
    return {
        restrict: 'E',
        templateUrl: 'app/components/nav/nav.html',
        replace: true,
        controller: 'NavController',
        controllerAs: 'vm'
    };
}