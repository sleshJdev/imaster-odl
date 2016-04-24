/**
 * @author slesh
 */

/*jshint undef: false*/
angular.module('imaster').service('StudentService', [
    '$http',
    function ($http) {
        'use strict';

        var self = this;

        self.getAllStudents = function () {
            return $http.get('/api/students');
        };

        self.addStudent = function (student) {
            return $http.post('/api/students', student);
        };
    }
]);
