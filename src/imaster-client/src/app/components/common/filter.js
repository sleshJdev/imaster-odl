/**
 * @author slesh
 */

(function () {
    'use strict';

    angular.module('imaster')
        .filter('fullName', fullName);

    /** @ngInject */
    function fullName() {
        return function (user) {
            if (user) {
                return user.firstName + ' ' + user.lastName + ' ' + user.patronymic;
            }
            return "";
        }
    }
})();