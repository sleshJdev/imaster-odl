/**
 * @author slesh
 */

(function () {
    'use strict';

    angular.module('imaster').service('roleService', roleService);

    /** @ngInject */
    function roleService($http) {
        return {
            getAllRoles: getAllRoles
        };

        function getAllRoles() {
            return $http.get("/api/roles/public");
        }
    }
})();