var gulp = require('gulp');

var conf = require('./conf');
var path = require('path');
var sass = require('gulp-sass');
var concat = require('gulp-concat');
var browserify = require('gulp-browserify');
var jshint = require('gulp-jshint');
var sourcemaps = require('gulp-sourcemaps');
var connect = require('gulp-connect');
var proxyMiddleware = require('http-proxy-middleware');
var browserSync = require('browser-sync');

gulp.task('html', require('./gulp/html'));
gulp.task('styles', require('./gulp/styles'));
gulp.task('scripts', ['lint'], require('./gulp/scripts'));
gulp.task('images', require('./gulp/images'));

function copyScripts() {
    return gulp.src([path.join(conf.temp, '/*.js')])
        .pipe(concat('main.js'))
        .pipe(gulp.dest(conf.target))
        .pipe(browserSync.reload({stream: true}));
}

function watch() {
    gulp.watch(conf.images, ['images']);
    gulp.watch(conf.html, ['bundle-html']);
    gulp.watch(conf.scripts, ['bundle-scripts']);
    gulp.watch(conf.styles, ['styles']);
}

gulp.task('bundle-scripts', ['scripts'], copyScripts);
gulp.task('bundle-html', ['html'], copyScripts);
gulp.task('build', ['bundle-scripts', 'bundle-html', 'styles', 'images'], copyScripts);
gulp.task('watch', ['build'], watch);
gulp.task('default', ['serve']);

gulp.task('serve', ['watch'], function () {
    var proxy = new proxyMiddleware('/api', {target: 'http://localhost:8010'});
    browserSync({
        ws: false,
        open: true,
        port: 9000,
        server: {
            baseDir: conf.target,
            middleware: [proxy]
        }
    });
});

gulp.task('server', function () {
    connect.server({
        root: conf.target,
        port: 9090,
        middleware: function (connect, options) {
            var proxy = new proxyMiddleware('/api', {target: 'http://localhost:8010'});
            return [proxy];
        }
    })
});