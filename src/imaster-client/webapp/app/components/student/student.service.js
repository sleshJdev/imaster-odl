/**
 * @author slesh
 */

/*jshint undef: false*/
angular.module('imaster').service('UserService', [
    '$http',
    function ($http) {
        'use strict';

        var self = this;

        self.getAllUsers = function () {
            return $http.get("/api/students");
        };
    }
]);
