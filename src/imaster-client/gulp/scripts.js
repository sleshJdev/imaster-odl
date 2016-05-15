/**
 * @author slesh
 */


var conf = require('../conf');
var gulp = require('gulp');
var path = require('path');
var concat = require('gulp-concat');
var browserify = require('gulp-browserify');
var jshint = require('gulp-jshint');
var sourcemaps = require('gulp-sourcemaps');
var connect = require('gulp-connect');

module.exports = scripts();

function scripts() {
    gulp.task('lint', function () {
        return gulp
            .src(conf.scripts)
            .pipe(jshint())
            .pipe(jshint.reporter('default'));
    });

    return function () {
        var browserifyTask = browserify({
            shim: {
                angular: {
                    path: path.join(conf.dependencies, 'angular/angular.js'),
                    exports: 'angular'
                },
                'angular-ui-router': {
                    path: path.join(conf.dependencies, 'angular-ui-router/release/angular-ui-router.js'),
                    exports: 'uiRouter',
                    depends: {
                        angular: 'angular'
                    }
                }
            }
        });

        return gulp.src(conf.scripts)
            .pipe(sourcemaps.init())
            .pipe(concat('scripts.js'))
            .pipe(browserifyTask)
            .pipe(sourcemaps.write())
            .pipe(gulp.dest(conf.temp));
    };
}