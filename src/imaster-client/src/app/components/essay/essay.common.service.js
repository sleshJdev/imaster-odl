/**
 * @author slesh
 */

(function () {
    'use strict';
    angular.module('imaster').service('essayCommonService', essayCommonService);

    /** @ngInject */
    function essayCommonService($state, $q, authService, essayService) {
        return {
            initContext: initContext,
            cancel: cancel
        };

        function cancel() {
            $state.go('essays.list');
        }

        function initContext(context) {
            authService.exportMethodsTo(context);
            return loadStaticData(context);
        }

        function loadStaticData(context) {
            return $q.all([
                essayService.getTeachers(),
                essayService.getStatuses()
            ]).then(function (responses) {
                context.teachers = responses[0].data;
                context.statuses = responses[1].data;
            });
        }
    }
})();