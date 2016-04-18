/**
 * @author yauheni.putsykovich
 */

/* jshint undef: false */
angular.module('imaster').service('AppService', [
    '$http',
    function ($http) {
        'use strict';
        var self = this;
        self.getPermissions = function () {
            return $http.get('/api/permissions');
        };
    }]);