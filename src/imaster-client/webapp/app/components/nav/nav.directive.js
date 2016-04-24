/**
 * @author slesh
 */

/*jshint undef: false*/
angular.module('imaster').directive("nav", [
    function () {
        'use strict';
        return {
            restrict: 'E',
            templateUrl: 'components/nav/nav.html',
            replace: true,
            controller: 'NavController',
            controllerAs: 'vm'
        };
    }
]);