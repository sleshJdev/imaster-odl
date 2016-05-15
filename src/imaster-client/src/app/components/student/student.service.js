/**
 * @author slesh
 */

/*jshint undef: false*/
angular.module('imaster').service('StudentService', [
    '$http',
    function ($http) {
        'use strict';

        return {
            getAllStudents: getAllStudents,
            addStudent: addStudent
        };

        function getAllStudents() {
            return $http.get('/api/students');
        }

        function addStudent(student) {
            return $http.post('/api/students', student);
        }
    }
]);
