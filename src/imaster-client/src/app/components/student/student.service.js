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
            getStudentById: getStudentById,
            addStudent: addStudent,
            updateStudent: updateStudent
        };

        function getAllStudents() {
            return $http.get('/api/students');
        }

        function addStudent(student) {
            return $http.post('/api/students', student);
        }

        function updateStudent(student) {
            return $http.put('/api/students', student);
        }

        function getStudentById(id) {
            return $http.get('/api/students/' + id);
        }
    }
]);
