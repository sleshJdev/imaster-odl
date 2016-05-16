/**
 * @author slesh
 */


var conf = require('../conf');
var gulp = require('gulp');
var concat = require('gulp-concat');
var browserify = require('gulp-browserify')(conf.shim);
var jshint = require('gulp-jshint');
var sourcemaps = require('gulp-sourcemaps');
var connect = require('gulp-connect');

module.exports = scripts;

function scripts() {
    return gulp.src(conf.scripts)
        .pipe(sourcemaps.init())
        .pipe(jshint())
        .pipe(concat('scripts.js'))
        .pipe(browserify)
        .pipe(sourcemaps.write())
        .pipe(gulp.dest(conf.temp));
}