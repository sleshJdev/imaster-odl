var gulp = require("gulp");

var conf = require('./conf');
var path = require('path');
var sass = require('gulp-sass');
var cleanCss = require('gulp-clean-css');
var angularTemplatecache = require('gulp-angular-templatecache');
var concat = require('gulp-concat');
var browserify = require('gulp-browserify');
var jshint = require('gulp-jshint');
var sourcemaps = require('gulp-sourcemaps');
var connect = require('gulp-connect');
var proxyMiddleware = require('http-proxy-middleware');

gulp.task('sass', function () {
    return gulp.src([path.join(conf.app, '/**/*.scss'), path.join(conf.resources, '/**/*.scss')])
        .pipe(sourcemaps.init())
        .pipe(sass().on('error', sass.logError))
        .pipe(concat('index.css'))
        .pipe(cleanCss())
        .pipe(sourcemaps.write())
        .pipe(gulp.dest(conf.target));
});

gulp.task('html', function () {
    var cache = angularTemplatecache('templates.js', {
        module: conf.module
    });
    return gulp.src(['!' + path.join(conf.app, 'index.html'), path.join(conf.app, '/**/*.html')])
        .pipe(cache)
        .pipe(gulp.dest(conf.temp));
});

gulp.task('lint', function () {
    return gulp.src([path.join(conf.app, '/**/*.js')])
        .pipe(jshint())
        .pipe(jshint.reporter('default'));
});

gulp.task('scripts', ['lint'], function () {
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

    return gulp.src([path.join(conf.app, 'app.js'), path.join(conf.app, '/**/*.js')])
        .pipe(sourcemaps.init())
        .pipe(concat('index.js'))
        .pipe(browserifyTask)
        .pipe(sourcemaps.write())
        .pipe(gulp.dest(conf.temp));
});

function copyScripts() {
    gulp.src([path.join(conf.temp, '/*.js')])
        .pipe(concat('index.js'))
        .pipe(gulp.dest(conf.target))
}

gulp.task('bundle-scripts', ['scripts'], function () {
   copyScripts();
});

gulp.task('bundle-html', ['html'], function () {
    gulp.src([path.join(conf.app, 'index.html')])
        .pipe(gulp.dest(conf.target));
    copyScripts();
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

gulp.task('watch', function () {
    gulp.watch(path.join(conf.app, '/**/*.html'), ['bundle-html']);
    gulp.watch(path.join(conf.app, '**/*.js'), ['bundle-scripts']);
    gulp.watch([path.join(conf.app, '/**/*.scss'), path.join(conf.resources, '/**/*.scss')], ['sass']);
});

gulp.task('default', ['bundle-html', 'bundle-scripts', 'sass', 'watch']);