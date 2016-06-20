/**
 * @author yauheni.putsykovich
 */

angular
    .module('imaster')
    .run(run);

/** @ngInject */
function run(authService, $log) {
    'use strict';
    $log.debug('session interceptor was added');
    if (authService.isAnonymous()) {
        if (authService.restore()) {
            $log.debug('restored token');
        } else {
            $log.debug('token not found');
        }
    }
}