/**
 * @author slesh
 */

var conf = require('../conf');
var gulp = require('gulp');
var sass = require('gulp-sass');
var concat = require('gulp-concat');
var cleanCss = require('gulp-clean-css');
var sourcemaps = require('gulp-sourcemaps');
var browserSync = require('browser-sync');

module.exports = styles;

function styles() {
    return gulp.src(conf.styles)
        .pipe(sourcemaps.init())
        .pipe(sass().on('error', sass.logError))
        .pipe(concat('main.css'))
        .pipe(cleanCss())
        .pipe(sourcemaps.write())
        .pipe(gulp.dest(conf.target));

}

