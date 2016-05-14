/**
 * @author yauheni.putsykovich
 */

/* jshint undef: false */
angular.module('imaster').service('AuthService', [
    '$http', '$q',
    function ($http, $q) {
        'use strict';
        var self = this;
        self.authentication = null;
        self.isLogging = false;

        function setAuthentication(authData) {
            self.authentication = authData;
            $http.defaults.headers.common['X-Auth'] =
                authData ? authData.token : undefined;
            if (sessionStorage) {
                if (authData) {
                    sessionStorage.setItem("auth.data", JSON.stringify(authData));
                } else {
                    sessionStorage.removeItem("auth.data");
                }
            }
        }

        self.login = function (username, password) {
            return $http.post('/api/login', {
                username: username,
                password: password
            }).then(function (response) {
                setAuthentication(response.data);
                return response;
            }, function (error) {
                setAuthentication(null);
                return $q.reject(error);
            });
        };

        self.logout = function () {
            setAuthentication(null);
        };

        self.restore = function () {
            if (sessionStorage) {
                var authData = sessionStorage.getItem("auth.data");
                if (authData) {
                    try {
                        setAuthentication(JSON.parse(authData));
                        return true;
                    } catch (e) {
                        console.log(e);
                    }
                }
            }
            return false;
        };

        self.isAuthenticated = function () {
            return !!self.authentication;
        };

        self.getUserName = function () {
            return !!self.authentication ? self.authentication.username : "empty";
        };

        self.getAuthentication = function () {
            return angular.copy(self.authentication);
        };

        function hasRole(role) {
            return self.isAuthenticated() &&
                !!self.authentication.roles &&
                !!self.authentication.roles.length &&
                self.authentication.roles.indexOf(role) !== -1;
        }

        self.addMethods = function (vm) {
            vm.isAdmin = function () {
                return hasRole('admin');
            };
            vm.isStudent = function () {
                return hasRole('student');
            };

            vm.isTeacher = function () {
                return hasRole('teacher');
            };

            vm.isLogging = function () {
                return self.isLogging;
            };

            vm.isLoggedUser = function () {
                return self.isAuthenticated();
            };

            vm.getUserName = function () {
                var auth = self.getAuthentication();
                return auth ? auth.username : null;
            };
        }
    }
]);
