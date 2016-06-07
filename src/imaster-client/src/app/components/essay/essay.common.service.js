/**
 * Created by slesh on 6/8/16.
 */

(function () {
    'use strict';
    angular.module('imaster').service('essayCommon', essayCommon);

    /** @ngInject */
    function essayCommon($http, $q) {
        return {
            initContext: initContext
        };

        function initContext(context) {


            return loadStaticData(context);
        }

        function loadStaticData(context) {
            return $q.all([
                $http.get('/api/teachers/public'),
                $http.get('/api/statuses/public')
            ]).then(function (responses) {
                context.teachers = responses[0].data;
                context.statuses = responses[1].data;
            });
        }
    }
})();