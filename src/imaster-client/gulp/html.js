/**
 * @author slesh
 */

var conf = require('../conf');
var gulp = require("gulp");
var angularTemplateCache = require('gulp-angular-templatecache');
var concat = require('gulp-concat');
var sourcemaps = require('gulp-sourcemaps');
var connect = require('gulp-connect');

module.exports = html;

function html() {
    var cache = angularTemplateCache('templates.js', {module: conf.module});
    gulp.src(conf.index).pipe(gulp.dest(conf.target));
    return gulp.src(conf.html).pipe(cache).pipe(gulp.dest(conf.temp));
}