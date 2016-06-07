/**
 * Created by slesh on 6/8/16.
 */

(function () {
    'use strict';

    angular.module('imaster')
        .filter('fullName', fullName);

    /** @ngInject */
    function fullName() {
        return function (user) {
            return user.firstName + ' ' + user.lastName + ' ' + user.patronymic;
        }
    }
})();