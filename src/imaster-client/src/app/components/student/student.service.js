/**
 * @author slesh
 */

angular
    .module('imaster')
    .service('studentService', studentService);

/** @ngInject */
function studentService($http) {
    'use strict';

    return {
        getAllStudents: getAllStudents,
        getStudentById: getStudentById,
        addStudent: addStudent,
        updateStudent: updateStudent
    };

    function getAllPublicStudents() {
        return $http.get('/api/students/public')
    }

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
