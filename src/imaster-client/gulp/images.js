/**
    * @author slesh
    */


var conf = require('../conf');
var gulp = require("gulp");
var path = require('path');
var concat = require('gulp-concat');

module.exports = images;

function images() {
    return gulp
        .src(conf.images)
        .pipe(gulp.dest(path.join(conf.target, '/images')))
}

